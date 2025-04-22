package com.realestate.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realestate.main.entity.Agent;
import com.realestate.main.exceptions.UserNotFoundException;
import com.realestate.main.service.AgencyService;

@RestController
public class AgencyController {
	
	@Autowired
	private AgencyService agencyService;
	
	@PreAuthorize("hasRole('Agency')")
	@PostMapping("/addAgent")
	public ResponseEntity<Agent> addAgent(@RequestParam String email,@RequestBody Agent agent) throws UserNotFoundException {
		Agent agent2 = agencyService.addAgent(email,agent);
		return new ResponseEntity<Agent>(agent2, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('Agency')")
	@PutMapping("/updateAgent")
	public ResponseEntity<Agent>  updateAgent(@RequestParam String email,@RequestBody Agent agent) throws UserNotFoundException {
		Agent updateAgent = agencyService.updateAgent(email, agent);
		return new ResponseEntity<Agent>(updateAgent, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('Agency','Agent')")
	@GetMapping("/getAgent")
	public ResponseEntity<Agent> getAgent(@RequestParam String email) throws UserNotFoundException {
		Agent agent = agencyService.getAgent(email);
		return new ResponseEntity<Agent>(agent, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('Agency')")
	@DeleteMapping("/deleteAgent")
	public ResponseEntity<String> deleteAgent(String email) throws UserNotFoundException {
		String deleteAgent = agencyService.deleteAgent(email);
		return new ResponseEntity<String>(deleteAgent, HttpStatus.OK);
	}
}
