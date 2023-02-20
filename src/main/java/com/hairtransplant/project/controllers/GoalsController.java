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

import com.hairtransplant.project.entities.Goals;
import com.hairtransplant.project.repositories.GoalsRepository;

@RestController
@RequestMapping("/api/goalsController")
public class GoalsController {

	@Autowired
	GoalsRepository goalsRepository;

	@GetMapping("search/all")
	public List<Goals> getAllGoalss() {
		return goalsRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Goals> getGoalsById(@PathVariable(value = "id") Long id) {
		Goals goals = goalsRepository.findById(id).orElse(null);
		if (goals == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(goals);
	}

	@PostMapping("/save")
	public Goals createGoals(@RequestBody Goals goals) {
		return goalsRepository.save(goals);
	}

	@PutMapping("update/{id}")
	public ResponseEntity<Goals> updateGoals(@PathVariable(value = "id") Long id, @RequestBody Goals goalsDetails) {
		Goals goals = goalsRepository.findById(id).orElse(null);

		if (goals == null) {
			return ResponseEntity.notFound().build();
		}

		goals.setExpectations(goalsDetails.getExpectations());
		goals.setOutcome(goalsDetails.getOutcome());
		goals.setBudget(goalsDetails.getBudget());
		goals.setDateDataEntry(goalsDetails.getDateDataEntry());

		Goals updatedGoals = goalsRepository.save(goals);
		return ResponseEntity.ok(updatedGoals);
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<Goals> deleteGoals(@PathVariable(value = "id") Long id) {
		Goals goals = goalsRepository.findById(id).orElse(null);
		if (goals == null) {
			return ResponseEntity.notFound().build();
		}
		goalsRepository.delete(goals);
		return ResponseEntity.ok().build();
	}
}
