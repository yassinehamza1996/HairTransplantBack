package com.hairtransplant.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hairtransplant.project.entities.LifeStyle;
import com.hairtransplant.project.entities.MedicalHistory;
import com.hairtransplant.project.entities.PersonalInformation;
import com.hairtransplant.project.repositories.LifeStyleRepository;
import com.hairtransplant.project.repositories.PersonalInformationRepository;
import com.hairtransplant.project.services.GenericExporterExcelService;
import com.hairtransplant.project.services.LifeStyleExporterExcelService;
import com.hairtransplant.project.services.MedicalHistoryExcelService;

@RestController
@RequestMapping("/api/lifeStyleController")
public class LifeStyleController {

	@Autowired
	LifeStyleRepository lifeStyleRepository;
	@Autowired
	private PersonalInformationRepository PersonalInformationRepository;

	@GetMapping("search/all")
	public List<LifeStyle> getAllLifeStyles() {
		return lifeStyleRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<LifeStyle> getLifeStyleById(@PathVariable(value = "id") Long id) {
		LifeStyle lifeStyle = lifeStyleRepository.findById(id).orElse(null);
		if (lifeStyle == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(lifeStyle);
	}

	@PostMapping("/save")
	public LifeStyle createLifeStyle(@RequestBody LifeStyle lifeStyle) {
		Long parentId = Long.parseLong(lifeStyle.getStringParent());
		PersonalInformation personalInformation = PersonalInformationRepository.findById(parentId).orElse(null);
		lifeStyle.setPersonalInformation(personalInformation);
		return lifeStyleRepository.save(lifeStyle);
	}

	@PutMapping("update/{id}")
	public ResponseEntity<LifeStyle> updateLifeStyle(@PathVariable(value = "id") Long id,
			@RequestBody LifeStyle lifeStyleDetails) {
		LifeStyle lifeStyle = lifeStyleRepository.findById(id).orElse(null);
		if (lifeStyle == null) {
			return ResponseEntity.notFound().build();
		}
		lifeStyle.setDiet(lifeStyleDetails.getDiet());
		lifeStyle.setExercise(lifeStyleDetails.getExercise());
		lifeStyle.setAlcohol(lifeStyleDetails.getAlcohol());
		lifeStyle.setTobacco(lifeStyleDetails.getTobacco());
		lifeStyle.setDateDataEntry(lifeStyleDetails.getDateDataEntry());

		LifeStyle updatedLifeStyle = lifeStyleRepository.save(lifeStyle);
		return ResponseEntity.ok(updatedLifeStyle);
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<LifeStyle> deleteLifeStyle(@PathVariable(value = "id") Long id) {
		LifeStyle lifeStyle = lifeStyleRepository.findById(id).orElse(null);
		if (lifeStyle == null) {
			return ResponseEntity.notFound().build();
		}
		lifeStyleRepository.delete(lifeStyle);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("delete/all")
	public ResponseEntity<?> deleteSelectedLifeStyles(@RequestBody List<LifeStyle> lifeStyleList) {

		if (lifeStyleList.isEmpty()) {
			return ResponseEntity.badRequest().body("List of Life Style is empty.");
		}
		lifeStyleRepository.deleteAll(lifeStyleList);
		return ResponseEntity.ok().build();
	}
	@PostMapping("/import-excel")
	public List<LifeStyle> importExcel(@RequestBody byte[] data) throws Exception {
	    List<LifeStyle> l =  LifeStyleExporterExcelService.importExcelFile(data);
	    System.out.println(l.get(0).getPersonalInformation().toString());
	    return l;
	}
	
	@PostMapping("/export-to-excel")
	public ResponseEntity<byte[]> exportLifeStyleToExcel(@RequestBody List<LifeStyle> lifeStyleList) {
	  try {
	    byte[] excelBytes = LifeStyleExporterExcelService.exportLifeStyle(lifeStyleList);
	    
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    headers.setContentDispositionFormData("attachment", "life-Style.xlsx");
	    
	    return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
	  } catch (Exception e) {
	    e.printStackTrace();
	  }
	  return null;
	}
}
