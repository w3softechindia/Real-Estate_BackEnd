package com.realestate.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.realestate.main.entity.Visit;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Integer>{
	Optional<Visit> findByTokenId(String tokenId);
}
