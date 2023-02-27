package com.hairtransplant.project.dto;

import java.util.Date;

public class MedicalHistoryDTO {

	private Long id;
	private Long personalInformationId;
	private String preExistingConditions;
	private String currentMedications;
	private String allergies;
	private String previousTransplants;
	private Date dateDataEntry;
	private PersonalInformationDTO personalInformation;
	// constructor
	public MedicalHistoryDTO() {
	}

	public MedicalHistoryDTO(Long id, Long personalInformationId, String preExistingConditions,
			String currentMedications, String allergies, String previousTransplants, Date dateDataEntry , PersonalInformationDTO personInformationDTO) {
		super();
		this.id = id;
		this.personalInformationId = personalInformationId;
		this.preExistingConditions = preExistingConditions;
		this.currentMedications = currentMedications;
		this.allergies = allergies;
		this.previousTransplants = previousTransplants;
		this.dateDataEntry = dateDataEntry;
		this.personalInformation = personInformationDTO;
	}

	
	public PersonalInformationDTO getPersonInformationDTO() {
		return personalInformation;
	}

	public void setPersonInformationDTO(PersonalInformationDTO personInformationDTO) {
		this.personalInformation = personInformationDTO;
	}

	// getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPersonalInformationId() {
		return personalInformationId;
	}

	public void setPersonalInformationId(Long personalInformationId) {
		this.personalInformationId = personalInformationId;
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

	public Date getDateDataEntry() {
		return dateDataEntry;
	}

	public void setDateDataEntry(Date dateDataEntry) {
		this.dateDataEntry = dateDataEntry;
	}
}

