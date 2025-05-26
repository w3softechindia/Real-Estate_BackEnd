package com.realestate.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestate.main.dto.VisitDto;
import com.realestate.main.entity.Visit;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Integer>{
	


}
