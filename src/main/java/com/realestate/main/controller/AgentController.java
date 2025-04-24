package com.realestate.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realestate.main.dto.CustomerDto;
import com.realestate.main.entity.Customer;
import com.realestate.main.exceptions.RoleNotFoundException;
import com.realestate.main.exceptions.UserNotFoundException;
import com.realestate.main.service.AgentService;

@RestController
public class AgentController {
	
	@Autowired
	private AgentService agentService;
	
	@PreAuthorize("hasRole('Agent')")
	@PostMapping("/addCustomer")
	public ResponseEntity<CustomerDto> addCustomer(@RequestParam String agentEmail,@RequestBody Customer customer) throws UserNotFoundException, RoleNotFoundException {
		CustomerDto customer2 = agentService.addCustomer(agentEmail, customer);
		return new ResponseEntity<CustomerDto>(customer2, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('Agent','Customer')")
	@PutMapping("/updateCustomer")
	public ResponseEntity<CustomerDto> updateCustomer(@RequestParam String email,@RequestBody Customer customer) throws UserNotFoundException {
		CustomerDto updateCustomer = agentService.updateCustomer(email, customer);
		return new ResponseEntity<CustomerDto>(updateCustomer, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('Agent','Customer','Admin','Agency')")
	@GetMapping("/getCustomer")
	public ResponseEntity<CustomerDto> getCustomer(@RequestParam String email) throws UserNotFoundException{
		CustomerDto customer = agentService.getCustomer(email);
		return new ResponseEntity<CustomerDto>(customer, HttpStatus.OK);
	}
}
