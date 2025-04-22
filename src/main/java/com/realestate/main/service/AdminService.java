package com.realestate.main.service;

import java.util.List;

import com.realestate.main.entity.Admin;
import com.realestate.main.entity.Agency;
import com.realestate.main.entity.Agent;
import com.realestate.main.entity.Customer;
import com.realestate.main.exceptions.UserNotFoundException;

public interface AdminService {
	
	void addRole();
	
	Admin addAdmin(Admin admin);
	
	Admin updateAdmin(String email, Admin admin) throws UserNotFoundException;
	
	Admin getAdmin(String email) throws UserNotFoundException;
	
	Agency addAgency(Agency agency);
	
	Agency updateAgency(String email, Agency agency) throws UserNotFoundException;
	
	Agency getAgency(String email) throws UserNotFoundException;
	
	String deleteAgency(String email) throws UserNotFoundException;
	
	List<Customer> getAllCustomers();
	
	List<Customer> getCustomersByAgency(String agencyName) throws UserNotFoundException;
	
	List<Agent> getAllAgents();
	
	List<Agent> getAgentsByAgency(String agencyName) throws UserNotFoundException;
}
