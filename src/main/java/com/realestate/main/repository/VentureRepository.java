package com.realestate.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestate.main.entity.Venture;

@Repository
public interface VentureRepository extends JpaRepository<Venture, Long>{

	List<Venture> findAllByVentureStatus(String string);

}
