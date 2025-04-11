package com.realestate.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestate.main.entity.RealEStateUser;
@Repository
public interface RealEstateUserRepo extends JpaRepository<RealEStateUser, Integer>{

	RealEStateUser findByEmail(String email);

}
