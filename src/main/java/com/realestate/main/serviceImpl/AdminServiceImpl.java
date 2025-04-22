package com.realestate.main.serviceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.realestate.main.entity.Admin;
import com.realestate.main.entity.Agency;
import com.realestate.main.entity.Agent;
import com.realestate.main.entity.Customer;
import com.realestate.main.entity.Role;
import com.realestate.main.exceptions.UserNotFoundException;
import com.realestate.main.repository.AdminRepository;
import com.realestate.main.repository.AgencyRepository;
import com.realestate.main.repository.AgentRepository;
import com.realestate.main.repository.CustomerRepository;
import com.realestate.main.repository.RoleRepository;
import com.realestate.main.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private AgencyRepository agencyRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private AgentRepository agentRepository;

	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

	@Override
	public void addRole() {
		// TODO Auto-generated method stub
		Role role1 = new Role();
		role1.setRoleName("Admin");
		roleRepository.save(role1);

		Role role2 = new Role();
		role2.setRoleName("Agency");
		roleRepository.save(role2);

		Role role3 = new Role();
		role3.setRoleName("Agent");
		roleRepository.save(role3);

		Role role4 = new Role();
		role4.setRoleName("Customer");
		roleRepository.save(role4);
	}

	@Override
	public Admin addAdmin(Admin admin) {
		// TODO Auto-generated method stub
		Role role = roleRepository.findById("Admin").get();
		Set<Role> roles = new HashSet<Role>();
		roles.add(role);
		admin.setRoles(roles);
		admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
		Admin admin2 = adminRepository.save(admin);
		return admin2;
	}

	@Override
	public Agency addAgency(Agency agency) {
		// TODO Auto-generated method stub
		Role role = roleRepository.findById("Agency").get();
		Set<Role> roles = new HashSet<Role>();
		roles.add(role);
		agency.setRoles(roles);
		agency.setPassword(bCryptPasswordEncoder.encode(agency.getPassword()));
		Agency agency2 = agencyRepository.save(agency);
		return agency2;
	}

	@Override
	public Admin updateAdmin(String email, Admin admin) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Admin admin2 = adminRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Admin not found with Email :" + email));
		admin2.setAdminName(admin.getAdminName());
		admin2.setEmail(admin.getEmail());
		admin2.setPhoneNumber(admin.getPhoneNumber());
		Admin admin3 = adminRepository.save(admin2);
		return admin3;
	}

	@Override
	public Admin getAdmin(String email) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Admin admin2 = adminRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Admin not found with Email :" + email));
		return admin2;
	}

	@Override
	public Agency updateAgency(String email, Agency agency) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Agency agency2 = agencyRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Agency not found with email :" + email));
		agency2.setAgencyAddress(agency.getAgencyAddress());
		agency2.setAgencyName(agency.getAgencyName());
		agency2.setAgencyPinCode(agency.getAgencyPinCode());
		agency2.setEmail(agency.getEmail());
		agency2.setPhoneNumber(agency.getPhoneNumber());
		Agency agency3 = agencyRepository.save(agency2);
		return agency3;
	}

	@Override
	public Agency getAgency(String email) throws UserNotFoundException {
		// TODO Auto-generated method stub
		return agencyRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Agency not found with email :" + email));
	}

	@Override
	public String deleteAgency(String email) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Agency agency = agencyRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Agency not found with email :" + email));
		agencyRepository.delete(agency);
		return "Agency got deleted..!!!";
	}

	@Override
	public List<Customer> getAllCustomers() {
		// TODO Auto-generated method stub
		return customerRepository.findAll();
	}

	@Override
	public List<Customer> getCustomersByAgency(String agencyName) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Agency agency2 = agencyRepository.findByAgencyName(agencyName)
				.orElseThrow(() -> new UserNotFoundException("Agency not found with email :" + agencyName));
		List<Customer> byAgency = customerRepository.findByAgencyId(agency2.getId());
		return byAgency;
	}

	@Override
	public List<Agent> getAllAgents() {
		// TODO Auto-generated method stub
		List<Agent> all = agentRepository.findAll();
		return all;
	}

	@Override
	public List<Agent> getAgentsByAgency(String agencyName) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Agency agency2 = agencyRepository.findByAgencyName(agencyName)
				.orElseThrow(() -> new UserNotFoundException("Agency not found with email :" + agencyName));
		List<Agent> list = agentRepository.findByAgencyId(agency2.getId());
		return list;
	}

}
