package com.realestate.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestate.main.entity.Agency;

@Repository
public interface AgencyRepository extends JpaRepository<Agency, Long>{

	Optional<Agency> findByEmail(String email);
	Optional<Agency> findByAgencyName(String agencyName);
	List<Agency> findAllByStatus(String string);
}
