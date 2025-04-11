package com.realestate.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestate.main.entity.Admin;
import com.realestate.main.entity.Agency;

@Repository
public interface AgencyRepository extends JpaRepository<Agency, Integer>{

	Optional<Agency> findByEmail(String email);

}
