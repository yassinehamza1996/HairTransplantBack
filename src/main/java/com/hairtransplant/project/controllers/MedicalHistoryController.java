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

import com.hairtransplant.project.entities.MedicalHistory;
import com.hairtransplant.project.entities.PersonalInformation;
import com.hairtransplant.project.repositories.MedicalHistoryRepository;
import com.hairtransplant.project.services.MedicalHistoryExcelService;
import com.hairtransplant.project.repositories.PersonalInformationRepository;

@RestController
@RequestMapping("/api/medicalHistoryController")
public class MedicalHistoryController {

	@Autowired
	private MedicalHistoryRepository medicalHistoryRepository ;
	
	@Autowired
	private PersonalInformationRepository PersonalInformationRepository;
	
	@GetMapping("search/all")
	public List<MedicalHistory> getAllMedicalHistorys() {
		return medicalHistoryRepository.findAll();
	}
	
	@GetMapping("/count")
	public Long getCountMedicalHistorys() {
		return medicalHistoryRepository.count();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<MedicalHistory> getMedicalHistoryById(@PathVariable(value = "id") Long id) {
		MedicalHistory medicalHistory = medicalHistoryRepository.findById(id).orElse(null);
		if (medicalHistory == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(medicalHistory);
	}
	@PostMapping("/save")
	public MedicalHistory createMedicalHistory(@RequestBody MedicalHistory medicalHistory) {
		if(medicalHistory.getId() == null && medicalHistory.getStringParent() != null) {
			Long parentId = Long.parseLong(medicalHistory.getStringParent());
			PersonalInformation pi =  PersonalInformationRepository.findById(parentId).orElse(null);
			medicalHistory.setPersonalInformation(pi);
		}
		return medicalHistoryRepository.save(medicalHistory);
	}

	@PutMapping("update/{id}")
	public ResponseEntity<MedicalHistory> updateMedicalHistory(@PathVariable(value = "id") Long id,
			@RequestBody MedicalHistory medicalHistoryDetails) {
		MedicalHistory medicalHistory = medicalHistoryRepository.findById(id).orElse(null);
		if (medicalHistory == null) {
			return ResponseEntity.notFound().build();
		}
		medicalHistory.setPreExistingConditions(medicalHistoryDetails.getPreExistingConditions());
		medicalHistory.setCurrentMedications(medicalHistoryDetails.getCurrentMedications());
		medicalHistory.setAllergies(medicalHistoryDetails.getAllergies());
		medicalHistory.setPreviousTransplants(medicalHistoryDetails.getPreviousTransplants());
		medicalHistory.setDateDataEntry(medicalHistoryDetails.getDateDataEntry());

		MedicalHistory updatedMedicalHistory = medicalHistoryRepository.save(medicalHistory);
		return ResponseEntity.ok(updatedMedicalHistory);
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<MedicalHistory> deleteMedicalHistory(@PathVariable(value = "id") Long id) {
		MedicalHistory medicalHistory = medicalHistoryRepository.findById(id).orElse(null);
		if (medicalHistory == null) {
			return ResponseEntity.notFound().build();
		}
		medicalHistoryRepository.delete(medicalHistory);
		return ResponseEntity.ok().build();
	}
	@DeleteMapping("delete/all")
	public ResponseEntity<?> deleteAllmedicalHistory(@RequestBody List<MedicalHistory> medicalHistory) {
		if (medicalHistory.isEmpty()) {
			return ResponseEntity.badRequest().body("List of medical History is empty.");
		}
		medicalHistoryRepository.deleteAll(medicalHistory);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/import-excel")
	public List<MedicalHistory> importExcel(@RequestBody byte[] data) throws Exception {
	    return MedicalHistoryExcelService.importExcelFile(data);
	}
	
	@PostMapping("/export-to-excel")
	public ResponseEntity<byte[]> exportPersonalInformationToExcel(@RequestBody List<MedicalHistory> medicalHistoryList) {
	  try {
	    byte[] excelBytes = MedicalHistoryExcelService.exportPersonalInformationToExcel(medicalHistoryList);
	    
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    headers.setContentDispositionFormData("attachment", "medical-history.xlsx");
	    
	    return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
	  } catch (Exception e) {
	    e.printStackTrace();
	  }
	  return null;
	}

}
