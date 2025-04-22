package com.realestate.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestate.main.entity.Agent;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Integer>{

	Optional<Agent> findByEmail(String email);

	List<Agent> findByAgencyId(long id);

}
