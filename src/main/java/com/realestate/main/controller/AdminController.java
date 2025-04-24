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

import com.realestate.main.dto.AdminDto;
import com.realestate.main.dto.AgencyDto;
import com.realestate.main.dto.AgentDto;
import com.realestate.main.dto.CustomerDto;
import com.realestate.main.entity.Admin;
import com.realestate.main.entity.Agency;
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
	public ResponseEntity<AdminDto> addAdmin(@RequestBody Admin admin){
		AdminDto admin2 = adminService.addAdmin(admin);
		return new ResponseEntity<AdminDto>(admin2, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('Admin')")
	@PostMapping("/addAgency")
	public ResponseEntity<AgencyDto> addAgency(@RequestBody Agency agency){
		AgencyDto agency2 = adminService.addAgency(agency);
		return new ResponseEntity<AgencyDto>(agency2, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('Admin')")
	@PutMapping("/updateAdmin")
	public ResponseEntity<AdminDto> updateAdmin(@RequestParam String email, @RequestBody Admin admin) throws UserNotFoundException {
		AdminDto updateAdmin = adminService.updateAdmin(email, admin);
		return new ResponseEntity<AdminDto>(updateAdmin, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('Admin')")
	@GetMapping("/getAdmin")
	public ResponseEntity<AdminDto> getAdmin(@RequestParam String email) throws UserNotFoundException{
		AdminDto admin = adminService.getAdmin(email);
		return new ResponseEntity<AdminDto>(admin, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('Admin')")
	@PutMapping("/updateAgency")
	public ResponseEntity<AgencyDto> updateAgency(@RequestParam String email,@RequestBody Agency agency) throws UserNotFoundException {
		AgencyDto updateAgency = adminService.updateAgency(email, agency);
		return new ResponseEntity<AgencyDto>(updateAgency, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('Admin','Agency')")
	@GetMapping("/getAgency")
	public ResponseEntity<AgencyDto> getAgency(@RequestParam String email) throws UserNotFoundException {
		AgencyDto agency = adminService.getAgency(email);
		return new ResponseEntity<AgencyDto>(agency, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('Admin')")
	@DeleteMapping("/deleteAgency")
	public ResponseEntity<String> deleteAgency(@RequestParam String email) throws UserNotFoundException {
		String deleteAgency = adminService.deleteAgency(email);
		return new ResponseEntity<String>(deleteAgency, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('Admin')")
	@GetMapping("/getAllCustomers")
	public ResponseEntity<List<CustomerDto>> getAllCustomers() {
		List<CustomerDto> allCustomers = adminService.getAllCustomers();
		return new ResponseEntity<List<CustomerDto>>(allCustomers, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('Admin','Agency')")
	@GetMapping("/getCustomersByAgency")
	public ResponseEntity<List<CustomerDto>> getCustomersByAgency(@RequestParam String agencyName) throws UserNotFoundException {
		List<CustomerDto> customersByAgency = adminService.getCustomersByAgency(agencyName);
		return new ResponseEntity<List<CustomerDto>>(customersByAgency, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('Admin')")
	@GetMapping("/getAllAgents")
	public ResponseEntity<List<AgentDto>> getAllAgents() {
		List<AgentDto> allAgents = adminService.getAllAgents();
		return new ResponseEntity<List<AgentDto>>(allAgents, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('Admin','Agency')")
	@GetMapping("/getAgentsByAgency")
	public ResponseEntity<List<AgentDto>> getAgentsByAgency(String agencyName) throws UserNotFoundException{
		List<AgentDto> agentsByAgency = adminService.getAgentsByAgency(agencyName);
		return new ResponseEntity<List<AgentDto>>(agentsByAgency, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('Admin')")
	@GetMapping("/getAllAgencies")
	public ResponseEntity<List<AgencyDto>> getAllAgencies(){
		List<AgencyDto> allAgencies = adminService.getAllAgencies();
		return new ResponseEntity<List<AgencyDto>>(allAgencies, HttpStatus.OK);
	}
}
