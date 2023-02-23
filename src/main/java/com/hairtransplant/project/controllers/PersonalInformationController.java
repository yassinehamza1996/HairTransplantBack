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
import com.hairtransplant.project.repositories.PersonalInformationRepository;
import com.hairtransplant.project.entities.PersonalInformation;

@RestController
@RequestMapping("/api/PersonalInformations")
public class PersonalInformationController {

	@Autowired
	private PersonalInformationRepository PersonalInformationRepository;

	@GetMapping("search/all")
	public List<PersonalInformation> getAllPersonalInformations() {
		return PersonalInformationRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<PersonalInformation> getPersonalInformationById(@PathVariable(value = "id") Long id) {
		PersonalInformation personalInformation = PersonalInformationRepository.findById(id).orElse(null);
		if (personalInformation == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(personalInformation);
	}

	@PostMapping("/save")
	public PersonalInformation createPersonalInformation(@RequestBody PersonalInformation personalInformation) {
		return PersonalInformationRepository.save(personalInformation);
	}

	@PutMapping("update/{id}")
	public ResponseEntity<PersonalInformation> updatePersonalInformation(@PathVariable(value = "id") Long id,
			@RequestBody PersonalInformation personalInformationDetails) {
		PersonalInformation personalInformation = PersonalInformationRepository.findById(id).orElse(null);
		if (personalInformation == null) {
			return ResponseEntity.notFound().build();
		}
		personalInformation.setFirstname(personalInformationDetails.getFirstname());
		personalInformation.setLastname(personalInformationDetails.getLastname());
		personalInformation.setAddress(personalInformationDetails.getAddress());
		personalInformation.setEmail(personalInformationDetails.getEmail());
		personalInformation.setPhoneNumber(personalInformationDetails.getPhoneNumber());
		personalInformation.setAge(personalInformationDetails.getAge());

		PersonalInformation updatedPersonalInformation = PersonalInformationRepository.save(personalInformation);
		return ResponseEntity.ok(updatedPersonalInformation);
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<PersonalInformation> deletePersonalInformation(@PathVariable(value = "id") Long id) {
		PersonalInformation personalInformation = PersonalInformationRepository.findById(id).orElse(null);
		if (personalInformation == null) {
			return ResponseEntity.notFound().build();
		}
		PersonalInformationRepository.delete(personalInformation);
		return ResponseEntity.ok().build();
	}
	@DeleteMapping("delete/all")
	public ResponseEntity<?> deleteAllPersonalInformations(@RequestBody List<PersonalInformation> personalInformations) {
		if (personalInformations.isEmpty()) {
			return ResponseEntity.badRequest().body("List of personal informations is empty.");
		}
		PersonalInformationRepository.deleteAll(personalInformations);
		return ResponseEntity.ok().build();
	}
}
