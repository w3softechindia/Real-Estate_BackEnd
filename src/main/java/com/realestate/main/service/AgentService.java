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
import com.realestate.main.exceptions.VisitNotFoundException;

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
	
    VisitDto updateVisitStatus(int visitId,String status) throws VisitNotFoundException;
	
	VisitDto makePayment(int visitId,double amount,String transactionMode) throws VisitNotFoundException;
	
	VisitDto acceptToken(String tokenId,String agencyStatus) throws VisitNotFoundException;

}
