package com.realestate.main.serviceImpl;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.realestate.main.dto.CustomerDto;
import com.realestate.main.entity.Agent;
import com.realestate.main.entity.Customer;
import com.realestate.main.entity.Role;
import com.realestate.main.exceptions.RoleNotFoundException;
import com.realestate.main.exceptions.UserNotFoundException;
import com.realestate.main.mapper.UserMapper;
import com.realestate.main.repository.AgentRepository;
import com.realestate.main.repository.CustomerRepository;
import com.realestate.main.repository.RoleRepository;
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
		customer.setAgencyId(agent2.getId());
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

}
