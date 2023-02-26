package com.hairtransplant.project.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.hairtransplant.project.repositories.PersonalInformationRepository;
import com.hairtransplant.project.services.PersonalInformationExcelService;
import com.hairtransplant.project.entities.MedicalHistory;
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

	@GetMapping("fetchwithidandmail/all")
	public List<PersonalInformation> getAllPersonalInformationsIdMail() {
		List<Object[]> results = PersonalInformationRepository.findAllIdAndEmail();
		List<PersonalInformation> resultList = new ArrayList<>();
		for (Object[] result : results) {
			Long id = (Long) result[0];
			String email = (String) result[1];
			PersonalInformation pi = new PersonalInformation();
			pi.setId(id);
			pi.setEmail(email);
			resultList.add(pi);
		}
		return resultList;
	}

	@GetMapping("/count")
	public Long getCountPersonalInformations() {
		return PersonalInformationRepository.count();
	}

	@PostMapping("/export-to-excel")
	public ResponseEntity<byte[]> exportPersonalInformationToExcel(
			@RequestBody List<PersonalInformation> personalInformationList) {
		try {
			byte[] excelBytes = PersonalInformationExcelService
					.exportPersonalInformationToExcel(personalInformationList);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.setContentDispositionFormData("attachment", "personal-information.xlsx");

			return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
		PersonalInformation personalInformationResponse = null;
		if (personalInformation.getId() == null) {

			PersonalInformation personaInformationEntity = new PersonalInformation();
			personaInformationEntity.setFirstname(personalInformation.getFirstname());
			personaInformationEntity.setLastname(personalInformation.getLastname());
			personaInformationEntity.setAge(personalInformation.getAge());
			personaInformationEntity.setAddress(personalInformation.getAddress());
			personaInformationEntity.setEmail(personalInformation.getEmail());
			personaInformationEntity.setPhoneNumber(personalInformation.getPhoneNumber());
			personalInformationResponse = PersonalInformationRepository.save(personaInformationEntity);

			if (personalInformation.getMedicalHistoryList().size() != 0) {
				List<MedicalHistory> listOfMedicalHistories = new ArrayList<>();
				for (MedicalHistory medicalHistory : personalInformation.getMedicalHistoryList()) {
					if (medicalHistory.getPersonalInformation() == null) {
						medicalHistory.setPersonalInformation(personalInformationResponse);
					}
					listOfMedicalHistories.add(medicalHistory);
				}
				personalInformationResponse.setMedicalHistoryList(listOfMedicalHistories);
				personalInformationResponse = PersonalInformationRepository.save(personalInformationResponse);
			}
		} else {
			if (personalInformation.getMedicalHistoryList().size() != 0) {
				List<MedicalHistory> listOfMedicalHistories = new ArrayList<>();
				for (MedicalHistory medicalHistory : personalInformation.getMedicalHistoryList()) {
					if (medicalHistory.getPersonalInformation() == null) {
						medicalHistory.setPersonalInformation(personalInformation);
					}
					listOfMedicalHistories.add(medicalHistory);
				}
				personalInformation.setMedicalHistoryList(listOfMedicalHistories);
			}
			personalInformationResponse = PersonalInformationRepository.save(personalInformation);
		}
		return personalInformationResponse;
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
	public ResponseEntity<?> deleteAllPersonalInformations(
			@RequestBody List<PersonalInformation> personalInformations) {
		if (personalInformations.isEmpty()) {
			return ResponseEntity.badRequest().body("List of personal informations is empty.");
		}
		PersonalInformationRepository.deleteAll(personalInformations);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/import-excel")
	public List<PersonalInformation> importExcel(@RequestBody byte[] data) throws Exception {
		return PersonalInformationExcelService.importExcelFile(data);
	}

}
