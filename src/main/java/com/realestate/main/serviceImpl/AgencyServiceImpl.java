package com.realestate.main.serviceImpl;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.realestate.main.dto.AgentDto;
import com.realestate.main.emailConfiguration.EmailUtil;
import com.realestate.main.entity.Agency;
import com.realestate.main.entity.Agent;
import com.realestate.main.entity.Role;
import com.realestate.main.exceptions.DuplicateEntryException;
import com.realestate.main.exceptions.UserNotFoundException;
import com.realestate.main.mapper.UserMapper;
import com.realestate.main.repository.AgencyRepository;
import com.realestate.main.repository.AgentRepository;
import com.realestate.main.repository.RealEstateUserRepo;
import com.realestate.main.repository.RoleRepository;
import com.realestate.main.service.AgencyService;

import jakarta.transaction.Transactional;

@Service
public class AgencyServiceImpl implements AgencyService {

	@Autowired
	private AgentRepository agentRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private AgencyRepository agencyRepository;

	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private EmailUtil emailUtil;
	
	@Autowired
	private RealEstateUserRepo userRepo;

	@Override
	public AgentDto addAgent(String agencyEmail,Agent agent) throws Exception {
		// TODO Auto-generated method stub
		if(userRepo.existsByEmail(agent.getEmail())) throw new DuplicateEntryException("Email Already Exists with :"+agent.getEmail());
		if(userRepo.existsByPhoneNumber(agent.getPhoneNumber())) throw new DuplicateEntryException("Phone Number Already exists :"+agent.getPhoneNumber());
		String password=agent.getPassword();
		Agency agency2 = agencyRepository.findByEmail(agencyEmail)
				.orElseThrow(() -> new UserNotFoundException("Agency not found with email :" + agencyEmail));
		Set<Role> roles = new HashSet<Role>();
		Optional<Role> byId = roleRepository.findById("Agent");
		if (byId.isPresent()) {
			Role role = byId.get();
			roles.add(role);
		}
		agent.setRoles(roles);
		agent.setPassword(bCryptPasswordEncoder.encode(agent.getPassword()));
		agent.setAgency(agency2);
		agent.setRegistrationDate(LocalDate.now());
		agent.setStatus("ACTIVE");
		Agent agent2 = agentRepository.save(agent);
		emailUtil.sendAgentRegistration(agent2.getEmail(), password, agency2.getAgencyName());
		
		AgentDto agentDto = userMapper.toAgentDto(agent2);
		return agentDto;
	}

	@Override
	public AgentDto updateAgent(String email, Agent agent) throws UserNotFoundException {
	    Agent existingAgent = agentRepository.findByEmail(email)
	            .orElseThrow(() -> new UserNotFoundException("Agent not found with email: " + email));

	    // Only updating the required fields
	    existingAgent.setAgentName(agent.getAgentName());
	    existingAgent.setAddress(agent.getAddress());
	    existingAgent.setPincode(agent.getPincode());
	    existingAgent.setCity(agent.getCity());
	    existingAgent.setState(agent.getState());

	    Agent updatedAgent = agentRepository.save(existingAgent);

	    return userMapper.toAgentDto(updatedAgent);
	}

	
	

	@Override
	public AgentDto getAgent(String email) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Agent agent2 = agentRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Agent not found with email :" + email));
		 AgentDto agentDto = userMapper.toAgentDto(agent2);
		 return agentDto;
	}

	@Override
	@Transactional
	public String deleteAgent(String email) throws UserNotFoundException {
	    Agent agent = agentRepository.findByEmail(email)
	            .orElseThrow(() -> new UserNotFoundException("Agent not found with email: " + email));
	    
	    // Clear the roles to avoid foreign key constraint violation
	    agent.getRoles().clear();
	    agentRepository.save(agent);  // Persist the removal of roles

	    // Now safely delete the agent
	    agentRepository.delete(agent);

	    return "Agent Deleted Successfully..!!!";
	}


}
