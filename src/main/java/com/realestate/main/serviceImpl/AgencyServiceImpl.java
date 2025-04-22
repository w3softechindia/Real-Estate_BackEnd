package com.realestate.main.serviceImpl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.realestate.main.entity.Agency;
import com.realestate.main.entity.Agent;
import com.realestate.main.entity.Role;
import com.realestate.main.exceptions.UserNotFoundException;
import com.realestate.main.repository.AgencyRepository;
import com.realestate.main.repository.AgentRepository;
import com.realestate.main.repository.RoleRepository;
import com.realestate.main.service.AgencyService;

@Service
public class AgencyServiceImpl implements AgencyService {

	@Autowired
	private AgentRepository agentRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private AgencyRepository agencyRepository;

	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

	@Override
	public Agent addAgent(String agencyEmail,Agent agent) throws UserNotFoundException {
		// TODO Auto-generated method stub
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
		return agentRepository.save(agent);
	}

	@Override
	public Agent updateAgent(String email, Agent agent) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Agent agent2 = agentRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Agent not found with email :" + email));
		agent2.setAddress(agent.getAddress());
		agent2.setCity(agent.getCity());
		agent2.setEmail(agent.getEmail());
		agent2.setPhoneNumber(agent.getPhoneNumber());
		agent2.setPincode(agent.getPincode());
		agent2.setState(agent.getState());
		return agentRepository.save(agent2);
	}

	@Override
	public Agent getAgent(String email) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Agent agent2 = agentRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Agent not found with email :" + email));
		return agent2;
	}

	@Override
	public String deleteAgent(String email) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Agent agent2 = agentRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Agent not found with email :" + email));
		agentRepository.delete(agent2);
		return "Agent Deleted Successfully..!!!";
	}

}
