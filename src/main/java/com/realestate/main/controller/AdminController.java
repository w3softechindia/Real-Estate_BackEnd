package com.realestate.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.realestate.main.entity.Admin;
import com.realestate.main.entity.Agency;
import com.realestate.main.service.AdminService;

import jakarta.annotation.PostConstruct;

@RestController
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@PostConstruct
	public void addRoles() {
		adminService.addRole();
	}
	
	@PostMapping("/addAdmin")
	public ResponseEntity<Admin> addAdmin(@RequestBody Admin admin){
		Admin admin2 = adminService.addAdmin(admin);
		return new ResponseEntity<Admin>(admin2, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('Admin')")
	@PostMapping("/addAgency")
	public ResponseEntity<Agency> addAgency(@RequestBody Agency agency){
		Agency agency2 = adminService.addAgency(agency);
		return new ResponseEntity<Agency>(agency2, HttpStatus.OK);
	}
}
