package com.realestate.main.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realestate.main.entity.Admin;
import com.realestate.main.entity.Agency;
import com.realestate.main.entity.Agent;
import com.realestate.main.entity.Customer;
import com.realestate.main.exceptions.UserNotFoundException;
import com.realestate.main.service.AdminService;

import jakarta.annotation.PostConstruct;

@RestController
@CrossOrigin(origins = "", allowedHeaders = "")
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
	
	@PreAuthorize("hasRole('Admin')")
	@PutMapping("/updateAdmin")
	public ResponseEntity<Admin> updateAdmin(@RequestParam String email, @RequestBody Admin admin) throws UserNotFoundException {
		Admin updateAdmin = adminService.updateAdmin(email, admin);
		return new ResponseEntity<Admin>(updateAdmin, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('Admin')")
	@GetMapping("/getAdmin")
	public ResponseEntity<Admin> getAdmin(@RequestParam String email) throws UserNotFoundException{
		Admin admin = adminService.getAdmin(email);
		return new ResponseEntity<Admin>(admin, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('Admin')")
	@PutMapping("/updateAgency")
	public ResponseEntity<Agency> updateAgency(@RequestParam String email,@RequestBody Agency agency) throws UserNotFoundException {
		Agency updateAgency = adminService.updateAgency(email, agency);
		return new ResponseEntity<Agency>(updateAgency, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('Admin','Agency')")
	@GetMapping("/getAgency")
	public ResponseEntity<Agency> getAgency(@RequestParam String email) throws UserNotFoundException {
		Agency agency = adminService.getAgency(email);
		return new ResponseEntity<Agency>(agency, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('Admin')")
	@DeleteMapping("/deleteAgency")
	public ResponseEntity<String> deleteAgency(@RequestParam String email) throws UserNotFoundException {
		String deleteAgency = adminService.deleteAgency(email);
		return new ResponseEntity<String>(deleteAgency, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('Admin')")
	@GetMapping("/getAllCustomers")
	public ResponseEntity<List<Customer>> getAllCustomers() {
		List<Customer> allCustomers = adminService.getAllCustomers();
		return new ResponseEntity<List<Customer>>(allCustomers, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('Admin','Agency')")
	@GetMapping("/getCustomersByAgency")
	public ResponseEntity<List<Customer>> getCustomersByAgency(@RequestParam String agencyName) throws UserNotFoundException {
		List<Customer> customersByAgency = adminService.getCustomersByAgency(agencyName);
		return new ResponseEntity<List<Customer>>(customersByAgency, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('Admin')")
	@GetMapping("/getAllAgents")
	public ResponseEntity<List<Agent>> getAllAgents() {
		List<Agent> allAgents = adminService.getAllAgents();
		return new ResponseEntity<List<Agent>>(allAgents, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('Admin','Agency')")
	@GetMapping("/getAgentsByAgency")
	public ResponseEntity<List<Agent>> getAgentsByAgency(String agencyName) throws UserNotFoundException{
		List<Agent> agentsByAgency = adminService.getAgentsByAgency(agencyName);
		return new ResponseEntity<List<Agent>>(agentsByAgency, HttpStatus.OK);
	}
}
