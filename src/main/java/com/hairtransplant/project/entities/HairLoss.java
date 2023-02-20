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
@Table(name = "hair_loss")
public class HairLoss {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "personal_information_id")
	private PersonalInformation personalInformation;

	@Column(name = "cause", columnDefinition = "text")
	private String cause;

	@Column(name = "extent", columnDefinition = "text")
	private String extent;

	@Column(name = "pattern", columnDefinition = "text")
	private String pattern;

	@Column(name = "date_dataEntry")
	private LocalDate dateDataEntry;

	public HairLoss() {
	}

	public HairLoss(Long id, PersonalInformation personalInformation, String cause, String extent, String pattern,
			LocalDate dateDataEntry) {
		super();
		this.id = id;
		this.personalInformation = personalInformation;
		this.cause = cause;
		this.extent = extent;
		this.pattern = pattern;
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

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getExtent() {
		return extent;
	}

	public void setExtent(String extent) {
		this.extent = extent;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public LocalDate getDateDataEntry() {
		return dateDataEntry;
	}

	public void setDateDataEntry(LocalDate dateDataEntry) {
		this.dateDataEntry = dateDataEntry;
	}

	// Constructor, getters and setters
}
