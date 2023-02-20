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

import com.hairtransplant.project.entities.LifeStyle;
import com.hairtransplant.project.repositories.LifeStyleRepository;

@RestController
@RequestMapping("/api/lifeStyleController")
public class LifeStyleController {

	@Autowired
	LifeStyleRepository lifeStyleRepository;

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
}
