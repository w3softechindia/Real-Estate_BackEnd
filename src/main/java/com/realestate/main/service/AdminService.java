package com.realestate.main.service;

import java.util.List;

import com.realestate.main.dto.AdminDto;
import com.realestate.main.dto.AgencyDto;
import com.realestate.main.dto.AgentDto;
import com.realestate.main.dto.CustomerDto;
import com.realestate.main.entity.Admin;
import com.realestate.main.entity.Agency;
import com.realestate.main.exceptions.UserNotFoundException;

public interface AdminService {
	
	void addRole();
	
	AdminDto addAdmin(Admin admin);
	
	AdminDto updateAdmin(String email, Admin admin) throws UserNotFoundException;
	
	AdminDto getAdmin(String email) throws UserNotFoundException;
	
	AgencyDto addAgency(Agency agency);
	
	AgencyDto updateAgency(String email, Agency agency) throws UserNotFoundException;
	
	AgencyDto getAgency(String email) throws UserNotFoundException;
	
	String deleteAgency(String email) throws UserNotFoundException;
	
	List<AgencyDto> getAllAgencies();
	
	List<CustomerDto> getAllCustomers();
	
	List<CustomerDto> getCustomersByAgency(String agencyName) throws UserNotFoundException;

	List<AgentDto> getAllAgents();
	
	List<AgentDto> getAgentsByAgency(String agencyName) throws UserNotFoundException;
}
