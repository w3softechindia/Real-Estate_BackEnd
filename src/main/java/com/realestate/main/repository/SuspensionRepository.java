package com.realestate.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.realestate.main.entity.Suspension;

@Repository
public interface SuspensionRepository extends JpaRepository<Suspension, Integer>{

}
