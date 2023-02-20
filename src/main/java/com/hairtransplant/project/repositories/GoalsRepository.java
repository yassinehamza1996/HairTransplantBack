package com.hairtransplant.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hairtransplant.project.entities.Goals;

public interface GoalsRepository extends JpaRepository<Goals, Long>{

}
