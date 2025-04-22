package com.realestate.main.service;

import com.realestate.main.entity.Agent;
import com.realestate.main.exceptions.UserNotFoundException;

public interface AgencyService {
	
	Agent addAgent(String agencyEmail,Agent agent) throws UserNotFoundException;
	
	Agent updateAgent(String email, Agent agent) throws UserNotFoundException;
	
	Agent getAgent(String email) throws UserNotFoundException;
	
	String deleteAgent(String email) throws UserNotFoundException;
}
