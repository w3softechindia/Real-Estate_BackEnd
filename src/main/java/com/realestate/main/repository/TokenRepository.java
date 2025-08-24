package com.realestate.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realestate.main.dto.TokenDto;
import com.realestate.main.entity.Token;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer>{

	List<Token> findByAgencyStatus(String agencyStatus);

	

	
	 

}
