package com.hairtransplant.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.hairtransplant.project.repositories.MedicalHistoryRepository;

@RestController
@RequestMapping("/api/medicalHistoryController")
public class MedicalHistoryController {

	@Autowired
	private MedicalHistoryRepository medicalHistoryRepository;

	@GetMapping("search/all")
	public List<MedicalHistory> getAllMedicalHistorys() {
		return medicalHistoryRepository.findAll();
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
		medicalHistory.setdateDataEntry(medicalHistoryDetails.getdateDataEntry());

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
}
