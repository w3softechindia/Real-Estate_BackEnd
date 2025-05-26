package com.realestate.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestate.main.entity.Plots;

@Repository
public interface PlotsRepository extends JpaRepository<Plots, Long>{

	List<Plots> findByVentureVentureId(long ventureId);
}
