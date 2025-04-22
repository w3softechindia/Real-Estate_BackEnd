package com.realestate.main.service;

import com.realestate.main.entity.Customer;
import com.realestate.main.exceptions.RoleNotFoundException;
import com.realestate.main.exceptions.UserNotFoundException;

public interface AgentService {
	
	Customer addCustomer(String agentEmail, Customer customer) throws UserNotFoundException, RoleNotFoundException;
	
	Customer updateCustomer(String email, Customer customer) throws UserNotFoundException;
	
	Customer getCustomer(String email) throws UserNotFoundException;
}
