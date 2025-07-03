package com.realestate.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestate.main.entity.AgencyVenture;

@Repository
public interface AgencyVentureRepository extends JpaRepository<AgencyVenture, Long>{

}
