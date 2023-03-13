package com.hairtransplant.project.entities;


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
@Table(name = "client_visit")
public class ClientVisit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "personal_information_id")
	private PersonalInformation personalInformation;

	@Column(name = "salesperson", columnDefinition = "varchar(100)")
	private String salesperson;

	@Column(name = "sales_manager", columnDefinition = "varchar(100)")
	private String salesManager;

	@Column(name = "visit_date")
	private String visitDate;

	public ClientVisit() {
	}

	public ClientVisit(Long id, PersonalInformation personalInformation, String salesperson, String salesManager,
			String visitDate) {
		super();
		this.id = id;
		this.personalInformation = personalInformation;
		this.salesperson = salesperson;
		this.salesManager = salesManager;
		this.visitDate = visitDate;
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

	public String getSalesperson() {
		return salesperson;
	}

	public void setSalesperson(String salesperson) {
		this.salesperson = salesperson;
	}

	public String getSalesManager() {
		return salesManager;
	}

	public void setSalesManager(String salesManager) {
		this.salesManager = salesManager;
	}

	public String getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(String visitDate) {
		this.visitDate = visitDate;
	}

	// Constructor, getters and setters
}
