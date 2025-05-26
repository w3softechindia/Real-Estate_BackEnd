package com.realestate.main.serviceImpl;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.realestate.main.dto.CustomerDto;
import com.realestate.main.dto.LeadDto;
import com.realestate.main.dto.VisitDto;
import com.realestate.main.entity.Agent;
import com.realestate.main.entity.Customer;
import com.realestate.main.entity.Lead;
import com.realestate.main.entity.Role;
import com.realestate.main.entity.Visit;
import com.realestate.main.exceptions.RoleNotFoundException;
import com.realestate.main.exceptions.UserNotFoundException;
import com.realestate.main.mapper.UserMapper;
import com.realestate.main.repository.AgentRepository;
import com.realestate.main.repository.CustomerRepository;
import com.realestate.main.repository.LeadRepository;
import com.realestate.main.repository.RoleRepository;
import com.realestate.main.repository.VisitRepository;
import com.realestate.main.service.AgentService;

@Service
public class AgentServiceImpl implements AgentService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AgentRepository agentRepository;

	@Autowired
	private RoleRepository roleRepository;

	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private LeadRepository leadRepository;
	
	@Autowired
	private VisitRepository visitRepository;

	@Override
	public CustomerDto addCustomer(String agentEmail, Customer customer)
			throws UserNotFoundException, RoleNotFoundException {
		// Set Role
		Set<Role> roles = new HashSet<Role>();
		Role role = roleRepository.findById("Customer")
				.orElseThrow(() -> new RoleNotFoundException("Role not found with Customer"));
		roles.add(role);
		customer.setRoles(roles);

		// Find Agent and Set Agent
		Agent agent2 = agentRepository.findByEmail(agentEmail)
				.orElseThrow(() -> new UserNotFoundException("Agent not found with email :" + agentEmail));
		customer.setAgent(agent2);
		customer.setAgencyName(agent2.getAgency().getAgencyName());
		customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
		customer.setRegistrationDate(LocalDate.now());
		Customer save = customerRepository.save(customer);
		
		CustomerDto customerDto = userMapper.toCustomerDto(save);
		return customerDto;
	}

	@Override
	public CustomerDto updateCustomer(String email, Customer customer) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Customer customer2 = customerRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Customer Not found with :" + email));
		customer2.setAddress(customer.getAddress());
		customer2.setCity(customer.getCity());
		customer2.setEmail(customer.getEmail());
		customer2.setPhoneNumber(customer.getPhoneNumber());
		customer2.setPincode(customer.getPincode());
		customer2.setState(customer.getState());
		Customer customer3 = customerRepository.save(customer2);
		
		CustomerDto customerDto = userMapper.toCustomerDto(customer3);
		return customerDto;
	}

	@Override
	public CustomerDto getCustomer(String email) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Customer customer2 = customerRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Customer Not found with :" + email));
		
		CustomerDto customerDto = userMapper.toCustomerDto(customer2);
		return customerDto;
	}
	
	
	@Override
	public LeadDto addLead(Lead lead) {
	      Lead saveLead = leadRepository.save(lead);
	      
	      LeadDto leadDto = userMapper.toLeadDto(saveLead);
	      return leadDto;
	}

	@Override
	public List<LeadDto> getAllLeads() {
		List<Lead> allLeads = leadRepository.findAll();
		List<LeadDto> collect = allLeads.stream().map(userMapper::toLeadDto).collect(Collectors.toList());
				return collect;
	}

	@Override
	public VisitDto addVisit(Visit visit) {
		Visit saveVisit = visitRepository.save(visit);
		
		VisitDto visitDto = userMapper.toVisitDto(saveVisit);
		return visitDto;
	}

	@Override
	public List<VisitDto> getAllVisits() {
		List<Visit> allVisits = visitRepository.findAll();
		List<VisitDto> Visits = allVisits.stream().map(userMapper::toVisitDto).collect(Collectors.toList());
		return Visits;
	}

	@Override
	public LeadDto updateLead(Lead lead, String email) throws UserNotFoundException {
		Lead findLead = leadRepository.findByEmail(email).orElseThrow(
				()-> new UserNotFoundException("Lead With Email "+email+" is not found......"));
		
		findLead.setLeadName(lead.getLeadName());
		findLead.setEstimatedBudget(lead.getEstimatedBudget());
		findLead.setInterestedIn(lead.getInterestedIn());
		findLead.setFollowUp(lead.getFollowUp());
		findLead.setLeadNotes(lead.getLeadNotes());
		findLead.setLeadSource(lead.getLeadSource());
		findLead.setLeadStatus(lead.getLeadStatus());
		findLead.setEmail(lead.getEmail());
		findLead.setPhoneNumber(lead.getPhoneNumber());
		findLead.setPreferredLocation(lead.getPreferredLocation());
		
		Lead updatedLead = leadRepository.save(findLead);
		
		LeadDto leadDto = userMapper.toLeadDto(updatedLead);
		
		return leadDto;
	}

	@Override
	public String deleteLead(String email) throws UserNotFoundException {
		Lead deleteLead = leadRepository.findByEmail(email).orElseThrow(
				()->new UserNotFoundException("Lead With Email :"+email+" is not found...."));
		leadRepository.delete(deleteLead);
		return "Lead Deleted SucessFully.....";
	}



}
