package com.realestate.main.service;

import com.realestate.main.dto.AgentDto;
import com.realestate.main.entity.Agent;
import com.realestate.main.exceptions.UserNotFoundException;

public interface AgencyService {
	
	AgentDto addAgent(String agencyEmail,Agent agent) throws UserNotFoundException, Exception;
	
	AgentDto updateAgent(String email, Agent agent) throws UserNotFoundException;
	
	AgentDto getAgent(String email) throws UserNotFoundException;
	
	String deleteAgent(String email) throws UserNotFoundException;
}
