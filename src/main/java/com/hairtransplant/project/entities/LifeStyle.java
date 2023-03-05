package com.hairtransplant.project.entities;

import java.time.LocalDate;

import com.hairtransplant.project.enums.YesNoEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "lifestyle")
public class LifeStyle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "personal_information_id")
	private PersonalInformation personalInformation;

	@Column(name = "diet", columnDefinition = "text")
	private String diet;

	@Column(name = "exercise", columnDefinition = "text")
	private String exercise;

	@Enumerated(EnumType.STRING)
	@Column(name = "alcohol")
	private YesNoEnum alcohol;

	@Enumerated(EnumType.STRING)
	@Column(name = "tobacco")
	private YesNoEnum tobacco;

	@Column(name = "date_dataEntry")
	private String dateDataEntry;

	@Transient
	private String parent;
	
	public LifeStyle() {
	}

	public LifeStyle(Long id, String diet, String exercise, YesNoEnum alcohol, YesNoEnum tobacco,
			String dateDataEntry) {
		super();
		this.id = id;
		this.diet = diet;
		this.exercise = exercise;
		this.alcohol = alcohol;
		this.tobacco = tobacco;
		this.dateDataEntry = dateDataEntry;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDateDataEntry() {
		return dateDataEntry;
	}

	public void setDateDataEntry(String dateDataEntry) {
		this.dateDataEntry = dateDataEntry;
	}

	public PersonalInformation getPersonalInformation() {
		return personalInformation;
	}

	public void setPersonalInformation(PersonalInformation personalInformation) {
		this.personalInformation = personalInformation;
	}

	public String getDiet() {
		return diet;
	}

	public void setDiet(String diet) {
		this.diet = diet;
	}

	public String getExercise() {
		return exercise;
	}

	public void setExercise(String exercise) {
		this.exercise = exercise;
	}

	public YesNoEnum getAlcohol() {
		return alcohol;
	}

	public void setAlcohol(YesNoEnum alcohol) {
		this.alcohol = alcohol;
	}

	public YesNoEnum getTobacco() {
		return tobacco;
	}

	public void setTobacco(YesNoEnum tobacco) {
		this.tobacco = tobacco;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getIdParent() {
		return personalInformation.getId().toString();
	}
}
