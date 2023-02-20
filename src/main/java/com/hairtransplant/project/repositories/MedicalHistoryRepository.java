package com.hairtransplant.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hairtransplant.project.entities.MedicalHistory;

public interface MedicalHistoryRepository  extends JpaRepository<MedicalHistory, Long>{

}
