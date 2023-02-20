package com.hairtransplant.project.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "goals")
public class Goals {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "personal_information_id")
	private PersonalInformation personalInformation;

	@Column(name = "expectations", columnDefinition = "text")
	private String expectations;

	@Column(name = "outcome", columnDefinition = "text")
	private String outcome;

	@Column(name = "budget")
	private int budget;

	@Column(name = "date_dataEntry")
	private LocalDate dateDataEntry;

	public Goals() {
	}

	public Goals(Long id, PersonalInformation personalInformation, String expectations, String outcome, int budget,
			LocalDate dateDataEntry) {
		super();
		this.id = id;
		this.personalInformation = personalInformation;
		this.expectations = expectations;
		this.outcome = outcome;
		this.budget = budget;
		this.dateDataEntry = dateDataEntry;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PersonalInformation getPersonalInformation() {
		return personalInformation;
	}

	public void setPersonalInformation(PersonalInformation personalInformation) {
		this.personalInformation = personalInformation;
	}

	public String getExpectations() {
		return expectations;
	}

	public void setExpectations(String expectations) {
		this.expectations = expectations;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public int getBudget() {
		return budget;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	public LocalDate getDateDataEntry() {
		return dateDataEntry;
	}

	public void setDateDataEntry(LocalDate dateDataEntry) {
		this.dateDataEntry = dateDataEntry;
	}

	// Constructor, getters and setters
}