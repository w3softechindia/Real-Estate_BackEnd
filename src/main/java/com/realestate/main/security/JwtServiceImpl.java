package com.realestate.main.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.realestate.main.entity.Admin;
import com.realestate.main.entity.Agency;
import com.realestate.main.entity.Agent;
import com.realestate.main.entity.Customer;
import com.realestate.main.entity.RealEStateUser;
import com.realestate.main.repository.AdminRepository;
import com.realestate.main.repository.AgencyRepository;
import com.realestate.main.repository.AgentRepository;
import com.realestate.main.repository.CustomerRepository;
import com.realestate.main.repository.RealEstateUserRepo;
@Primary
@Service
public class JwtServiceImpl implements UserDetailsService {
	
	@Autowired
	private RealEstateUserRepo estateUserRepo;
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private AgencyRepository agencyRepository;
	
	@Autowired
	private AgentRepository agentRepository;

	@Autowired
	@Lazy
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;

	public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
		String email = jwtRequest.getEmail();
		String password = jwtRequest.getPassword();
		authenticate(email, password);

		loadUserByUsername(email);
		RealEStateUser eStateUser = estateUserRepo.findByEmail(email);
		String generatedToken = jwtService.generateToken(eStateUser.getEmail());
		return new JwtResponse(generatedToken, eStateUser);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	    Agency agency = agencyRepository.findByEmail(email).orElse(null);
	    if (agency != null) return agency;

	    Admin admin = adminRepository.findByEmail(email).orElse(null);
	    if (admin != null) return admin;

	    Agent agent = agentRepository.findByEmail(email).orElse(null);
	    if (agent != null) return agent;

	    Customer customer = customerRepository.findByEmail(email).orElse(null);
	    if (customer != null) return customer;

	    throw new UsernameNotFoundException("User not found with email: " + email);
	}

	private void authenticate(String email, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

}
