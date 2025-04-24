package com.realestate.main.service;

import com.realestate.main.dto.CustomerDto;
import com.realestate.main.entity.Customer;
import com.realestate.main.exceptions.RoleNotFoundException;
import com.realestate.main.exceptions.UserNotFoundException;

public interface AgentService {
	
	CustomerDto addCustomer(String agentEmail, Customer customer) throws UserNotFoundException, RoleNotFoundException;
	
	CustomerDto updateCustomer(String email, Customer customer) throws UserNotFoundException;
	
	CustomerDto getCustomer(String email) throws UserNotFoundException;
}
