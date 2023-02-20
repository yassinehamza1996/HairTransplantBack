package com.hairtransplant.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hairtransplant.project.entities.PersonalInformation;

public interface PersonalInformationRepository extends JpaRepository<PersonalInformation, Long> {

}
