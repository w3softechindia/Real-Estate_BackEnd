package com.realestate.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.realestate.main.entity.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer>{

}
