package com.hairtransplant.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hairtransplant.project.entities.HairLoss;

public interface HairLossRepository extends JpaRepository<HairLoss, Long>{

}
