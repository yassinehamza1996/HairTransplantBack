package com.hairtransplant.project.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "PersonalInformation" ,uniqueConstraints = {
		@UniqueConstraint(columnNames = {"email"})
})

public class PersonalInformation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "firstname")
	private String firstname;
	
	@Column(name = "lastname")
	private String lastname;
	
	@Column(name = "address")
	private String address;
	
	
	@Column(name = "email" , nullable = false)
	
	private String email;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "age")
	private Integer age;
	

	@OneToMany(mappedBy = "personalInformation", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private List<MedicalHistory> medicalHistoryList;
	

	public PersonalInformation() {
	}



	public PersonalInformation(Long id, String firstname, String lastname, String address, String email,
			String phoneNumber, Integer age) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.address = address;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.age = age;
	}
	

	public PersonalInformation(Long id, String firstname, String lastname, String address, String email,
			String phoneNumber, Integer age ,List<MedicalHistory> medicalHistoryList ) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.address = address;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.age = age;
		this.medicalHistoryList = medicalHistoryList;
	}
	
	public List<MedicalHistory> getMedicalHistoryList() {
		return medicalHistoryList;
	}



	public void setMedicalHistoryList(List<MedicalHistory> medicalHistoryList) {
		this.medicalHistoryList = medicalHistoryList;
	}






	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public String getFirstname() {
		return firstname;
	}



	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}



	public String getLastname() {
		return lastname;
	}



	public void setLastname(String lastname) {
		this.lastname = lastname;
	}



	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	// Getters and Setters
}