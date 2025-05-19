package com.realestate.main.service;

import java.util.List;

import com.realestate.main.dto.CustomerDto;
import com.realestate.main.dto.LeadDto;
import com.realestate.main.dto.VisitDto;
import com.realestate.main.entity.Customer;
import com.realestate.main.entity.Lead;
import com.realestate.main.entity.Visit;
import com.realestate.main.exceptions.RoleNotFoundException;
import com.realestate.main.exceptions.UserNotFoundException;

public interface AgentService {
	
	CustomerDto addCustomer(String agentEmail, Customer customer) throws UserNotFoundException, RoleNotFoundException;
	
	CustomerDto updateCustomer(String email, Customer customer) throws UserNotFoundException;
	
	CustomerDto getCustomer(String email) throws UserNotFoundException;
	
	public LeadDto addLead(Lead lead);
	
	LeadDto updateLead(Lead lead,String email) throws UserNotFoundException;
	 
	List<LeadDto> getAllLeads();
	
	String deleteLead(String email) throws UserNotFoundException;
	
	VisitDto addVisit(Visit visit);
	
	List<VisitDto> getAllVisits();
}
