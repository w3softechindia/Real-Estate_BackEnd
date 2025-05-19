package com.realestate.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.realestate.main.entity.Visit;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Integer>{

}
