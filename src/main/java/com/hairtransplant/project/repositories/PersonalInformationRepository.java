package com.hairtransplant.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hairtransplant.project.entities.PersonalInformation;

public interface PersonalInformationRepository extends JpaRepository<PersonalInformation, Long> {
	@Query("SELECT pi.id, pi.email FROM PersonalInformation pi")
    List<Object[]> findAllIdAndEmail();
}
