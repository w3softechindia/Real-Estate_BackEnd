package com.realestate.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestate.main.entity.Reviews;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, Integer> {
	List<Reviews> findByAgentEmail(String email);
}