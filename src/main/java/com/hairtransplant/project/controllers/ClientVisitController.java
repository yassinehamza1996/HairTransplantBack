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

import com.hairtransplant.project.entities.ClientVisit;
import com.hairtransplant.project.repositories.ClientVisitRepository;

@RestController
@RequestMapping("/api/clientVisitController")
public class ClientVisitController {

	@Autowired
	ClientVisitRepository clientVisitRepository;
	
	@GetMapping("search/all")
	public List<ClientVisit> getAllClientVisits() {
		return clientVisitRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<ClientVisit> getClientVisitById(@PathVariable(value = "id") Long id) {
		ClientVisit clientVisit = clientVisitRepository.findById(id).orElse(null);
		if (clientVisit == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(clientVisit);
	}

	@PostMapping("/save")
	public ClientVisit createClientVisit(@RequestBody ClientVisit clientVisit) {
		return clientVisitRepository.save(clientVisit);
	}

	@PutMapping("update/{id}")
	public ResponseEntity<ClientVisit> updateClientVisit(@PathVariable(value = "id") Long id, @RequestBody ClientVisit clientVisitDetails) {
		ClientVisit clientVisit = clientVisitRepository.findById(id).orElse(null);

		if (clientVisit == null) {
			return ResponseEntity.notFound().build();
		}

		clientVisit.setSalesManager(clientVisitDetails.getSalesManager());
		clientVisit.setSalesperson(clientVisitDetails.getSalesperson());
		clientVisit.setVisitDate(clientVisitDetails.getVisitDate());

		ClientVisit updatedClientVisit = clientVisitRepository.save(clientVisit);
		return ResponseEntity.ok(updatedClientVisit);
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<ClientVisit> deleteClientVisit(@PathVariable(value = "id") Long id) {
		ClientVisit clientVisit = clientVisitRepository.findById(id).orElse(null);
		if (clientVisit == null) {
			return ResponseEntity.notFound().build();
		}
		clientVisitRepository.delete(clientVisit);
		return ResponseEntity.ok().build();
	}
}
