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

import com.hairtransplant.project.entities.HairLoss;
import com.hairtransplant.project.entities.LifeStyle;
import com.hairtransplant.project.entities.PersonalInformation;
import com.hairtransplant.project.repositories.HairLossRepository;
import com.hairtransplant.project.repositories.PersonalInformationRepository;
import com.hairtransplant.project.services.HiarLossExporterExcelService;
import com.hairtransplant.project.services.LifeStyleExporterExcelService;

@RestController
@RequestMapping("/api/hairLossController")
public class HairLossController {

	@Autowired
	HairLossRepository hairLossRepository;

	@Autowired
	PersonalInformationRepository personalInformationRepository;

	@GetMapping("search/all")
	public List<HairLoss> getAllHairLosss() {
		return hairLossRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<HairLoss> getHairLossById(@PathVariable(value = "id") Long id) {
		HairLoss hairLoss = hairLossRepository.findById(id).orElse(null);
		if (hairLoss == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(hairLoss);
	}

	@GetMapping("/count")
	public Long getCount() {
		Long numberOfRows = hairLossRepository.count();
		return numberOfRows;
	}

	@PostMapping("/save")
	public HairLoss createHairLoss(@RequestBody HairLoss hairLoss) {
		if (hairLoss.getStringParent() != null) {
			Long idParent = Long.parseLong(hairLoss.getStringParent());
			PersonalInformation pi = personalInformationRepository.findById(idParent).orElse(null);
			if (pi != null) {
				hairLoss.setPersonalInformation(pi);
			}
		}
		return hairLossRepository.save(hairLoss);
	}

	@PutMapping("update/{id}")
	public ResponseEntity<HairLoss> updateHairLoss(@PathVariable(value = "id") Long id,
			@RequestBody HairLoss hairLossDetails) {
		HairLoss hairLoss = hairLossRepository.findById(id).orElse(null);

		if (hairLoss == null) {
			return ResponseEntity.notFound().build();
		}

		hairLoss.setCause(hairLossDetails.getCause());
		hairLoss.setExtent(hairLossDetails.getExtent());
		hairLoss.setPattern(hairLossDetails.getPattern());
		hairLoss.setDateDataEntry(hairLossDetails.getDateDataEntry());

		HairLoss updatedHairLoss = hairLossRepository.save(hairLoss);
		return ResponseEntity.ok(updatedHairLoss);
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<HairLoss> deleteHairLoss(@PathVariable(value = "id") Long id) {
		HairLoss hairLoss = hairLossRepository.findById(id).orElse(null);
		if (hairLoss == null) {
			return ResponseEntity.notFound().build();
		}
		hairLossRepository.delete(hairLoss);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("delete/all")
	public ResponseEntity<?> deleteSelectedHairLoss(@RequestBody List<HairLoss> hairLossList) {

		if (hairLossList.isEmpty()) {
			return ResponseEntity.badRequest().body("List of Life Style is empty.");
		}
		hairLossRepository.deleteAll(hairLossList);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/export-to-excel")
	public ResponseEntity<byte[]> exportHairLossToExcel(@RequestBody List<HairLoss> hairLossList) {
	  try {
	    byte[] excelBytes = HiarLossExporterExcelService.exportHairLoss(hairLossList);
	    
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    headers.setContentDispositionFormData("attachment", "Hair-Loss.xlsx");
	    
	    return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
	  } catch (Exception e) {
	    e.printStackTrace();
	  }
	  return null;
	}
	@PostMapping("/import-excel")
	public List<HairLoss> importExcel(@RequestBody byte[] data) throws Exception {
	    List<HairLoss> l =  HiarLossExporterExcelService.importExcelFile(data);
	    System.out.println(l.get(0).getPersonalInformation().toString());
	    return l;
	}
}
