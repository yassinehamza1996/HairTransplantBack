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

import com.hairtransplant.project.entities.Goals;
import com.hairtransplant.project.entities.Goals;
import com.hairtransplant.project.entities.PersonalInformation;
import com.hairtransplant.project.repositories.GoalsRepository;
import com.hairtransplant.project.repositories.PersonalInformationRepository;
import com.hairtransplant.project.services.GoalsExporterExcelService;
import com.hairtransplant.project.services.HiarLossExporterExcelService;

@RestController
@RequestMapping("/api/goalsController")
public class GoalsController {

	@Autowired
	GoalsRepository goalsRepository;
	
	@Autowired
	PersonalInformationRepository personalInformationRepository;
	
	@GetMapping("/count")
	public Long getCount() {
		return goalsRepository.count();
	}
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
		if(goals.getStringParent() != null) {
			Long parentId = Long.parseLong(goals.getStringParent());
			PersonalInformation pi = personalInformationRepository.findById(parentId).orElse(null);
			if(pi != null) {
				goals.setPersonalInformation(pi);
			}
		}
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
	@DeleteMapping("delete/all")
	public ResponseEntity<?> deleteSelectedGoals(@RequestBody List<Goals> GoalsList) {

		if (GoalsList.isEmpty()) {
			return ResponseEntity.badRequest().body("List of Life Style is empty.");
		}
		goalsRepository.deleteAll(GoalsList);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/export-to-excel")
	public ResponseEntity<byte[]> exportGoalsToExcel(@RequestBody List<Goals> GoalsList) {
	  try {
	    byte[] excelBytes = GoalsExporterExcelService.exportGoals(GoalsList);
	    
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
	public List<Goals> importExcel(@RequestBody byte[] data) throws Exception {
	    List<Goals> l =  GoalsExporterExcelService.importExcelFile(data);
	    System.out.println(l.get(0).getPersonalInformation().toString());
	    return l;
	}
}
