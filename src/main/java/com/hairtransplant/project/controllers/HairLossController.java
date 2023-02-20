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

import com.hairtransplant.project.entities.HairLoss;
import com.hairtransplant.project.repositories.HairLossRepository;

@RestController
@RequestMapping("/api/hairLossController")
public class HairLossController {

	@Autowired
	HairLossRepository hairLossRepository;

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

	@PostMapping("/save")
	public HairLoss createHairLoss(@RequestBody HairLoss hairLoss) {
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
}
