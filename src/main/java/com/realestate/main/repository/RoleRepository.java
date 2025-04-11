package com.realestate.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.realestate.main.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String>{

}
