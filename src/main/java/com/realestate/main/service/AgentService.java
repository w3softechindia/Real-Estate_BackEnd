package com.realestate.main.service;

import java.util.Date;
import java.util.List;
import com.realestate.main.dto.AgentDto;
import com.realestate.main.dto.CustomerDto;
import com.realestate.main.dto.LeadDto;
import com.realestate.main.dto.TokenDto;
import com.realestate.main.dto.VisitDto;
import com.realestate.main.entity.Agent;
import com.realestate.main.entity.Customer;
import com.realestate.main.entity.Lead;
import com.realestate.main.entity.Post;
import com.realestate.main.entity.Token;
import com.realestate.main.entity.Visit;
import com.realestate.main.exceptions.AgentNotFoundException;
import com.realestate.main.exceptions.RoleNotFoundException;
import com.realestate.main.exceptions.UserNotFoundException;
import com.realestate.main.exceptions.VisitNotFoundException;

public interface AgentService {
	
	CustomerDto addCustomer(String agentEmail, Customer customer) throws UserNotFoundException, RoleNotFoundException;
	
	CustomerDto updateCustomer(String email, Customer customer) throws UserNotFoundException;
	
	CustomerDto getCustomer(String email) throws UserNotFoundException;
	
	public LeadDto addLead(Lead lead,String agentEmail) throws AgentNotFoundException;
	
	LeadDto updateLead(Lead lead,String email) throws UserNotFoundException;
	 
	List<LeadDto> getAllLeads();
	
	String deleteLead(String email) throws UserNotFoundException;
	
	VisitDto addVisit(Visit visit,int leadId) throws UserNotFoundException;
	
	List<VisitDto> getAllVisits();
	
   VisitDto updateVisitStatus(int visitId,String status,String reason) throws VisitNotFoundException;
   
   TokenDto makePayment(int leadId,Token token) throws UserNotFoundException;
   
   List<TokenDto> getAllTokens();
   
   AgentDto updateProfile(Agent agent,String email) throws AgentNotFoundException;
   
   List<Post> getAllPosts();


}
