package com.realestate.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestate.main.entity.Lead;
import java.util.List;
import java.util.Optional;


@Repository
public interface LeadRepository extends JpaRepository<Lead, Integer>{
	
	Optional<Lead> findByEmail(String email);
	
	List<Lead> findAllByAgentEmail(String email);

}
