package com.hairtransplant.project.entities;

import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "medical_history")
public class MedicalHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "personal_information_id")
	private PersonalInformation personalInformation;

	@Column(name = "pre_existing_conditions")
	private String preExistingConditions;

	@Column(name = "current_medications")
	private String currentMedications;

	@Column(name = "allergies")
	private String allergies;

	@Column(name = "previous_transplants")
	private String previousTransplants;

	@Column(name = "date_dataEntry")
	@JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
	private Date dateDataEntry;
	@Transient
	private String parent;

	// constructor
	public MedicalHistory() {
	}

	public MedicalHistory(Long id, PersonalInformation personalInformation, String preExistingConditions,
			String currentMedications, String allergies, String previousTransplants, Date dateDataEntry,
			String parent) {
		super();
		this.id = id;
		this.personalInformation = personalInformation;
		this.preExistingConditions = preExistingConditions;
		this.currentMedications = currentMedications;
		this.allergies = allergies;
		this.previousTransplants = previousTransplants;
		this.dateDataEntry = dateDataEntry;
		this.parent = parent;
	}

	public Date getDateDataEntry() {
		return dateDataEntry;
	}

	public void setDateDataEntry(Date dateDataEntry) {
		this.dateDataEntry = dateDataEntry;
	}

	public String getParent() {
		return personalInformation.getId().toString();
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	

	// getters and setters
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

	public String getPreExistingConditions() {
		return preExistingConditions;
	}

	public void setPreExistingConditions(String preExistingConditions) {
		this.preExistingConditions = preExistingConditions;
	}

	public String getCurrentMedications() {
		return currentMedications;
	}

	public void setCurrentMedications(String currentMedications) {
		this.currentMedications = currentMedications;
	}

	public String getAllergies() {
		return allergies;
	}

	public void setAllergies(String allergies) {
		this.allergies = allergies;
	}

	public String getPreviousTransplants() {
		return previousTransplants;
	}

	public void setPreviousTransplants(String previousTransplants) {
		this.previousTransplants = previousTransplants;
	}

}
