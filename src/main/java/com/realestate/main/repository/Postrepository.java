package com.realestate.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.realestate.main.entity.Agency;
import com.realestate.main.entity.Post;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface Postrepository extends JpaRepository<Post, Long> {

	List<Post> findByAgencyEmail(String email);

	List<Post> findByAdminEmail(String email);
	

	

}
