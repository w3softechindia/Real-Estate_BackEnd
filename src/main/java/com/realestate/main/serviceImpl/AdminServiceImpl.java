package com.realestate.main.serviceImpl;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.realestate.main.dto.AdminDto;
import com.realestate.main.dto.AgencyDto;
import com.realestate.main.dto.AgentDto;
import com.realestate.main.dto.CustomerDto;
import com.realestate.main.entity.Admin;
import com.realestate.main.entity.Agency;
import com.realestate.main.entity.Agent;
import com.realestate.main.entity.Customer;
import com.realestate.main.entity.Role;
import com.realestate.main.exceptions.UserNotFoundException;
import com.realestate.main.mapper.UserMapper;
import com.realestate.main.repository.AdminRepository;
import com.realestate.main.repository.AgencyRepository;
import com.realestate.main.repository.AgentRepository;
import com.realestate.main.repository.CustomerRepository;
import com.realestate.main.repository.RoleRepository;
import com.realestate.main.service.AdminService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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
	
	@Autowired
	private UserMapper userMapper;

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
	public AdminDto addAdmin(Admin admin) {
		// TODO Auto-generated method stub
		Role role = roleRepository.findById("Admin").get();
		Set<Role> roles = new HashSet<Role>();
		roles.add(role);
		admin.setRoles(roles);
		admin.setPassword(bCryptPasswordEncoder.encode(admin.getPassword()));
		Admin admin2 = adminRepository.save(admin);
		log.info("Admin Created Successfully..!!! ");

		AdminDto adminDto = userMapper.toAdminDto(admin2);
		log.info("Converted into AdminDto..!!");
		return adminDto;
	}

	@Override
	public AgencyDto addAgency(Agency agency) {
		// TODO Auto-generated method stub
		Role role = roleRepository.findById("Agency").get();
		Set<Role> roles = new HashSet<Role>();
		roles.add(role);
		agency.setRoles(roles);
		agency.setPassword(bCryptPasswordEncoder.encode(agency.getPassword()));
		agency.setRegistrationDate(LocalDate.now());
		Agency agency2 = agencyRepository.save(agency);
		log.info("Agency created successfully..!!");
		
		AgencyDto agencyDto = userMapper.toAgencyDto(agency2);
		log.info("Converted into AgencyDto..!!");
		return agencyDto;
	}

	@Override
	public AdminDto updateAdmin(String email, Admin admin) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Admin admin2 = adminRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Admin not found with Email :" + email));
		admin2.setAdminName(admin.getAdminName());
		admin2.setEmail(admin.getEmail());
		admin2.setPhoneNumber(admin.getPhoneNumber());
		Admin admin3 = adminRepository.save(admin2);
		log.info("Admin Updated successfully..!!");
		
		AdminDto adminDto = userMapper.toAdminDto(admin3);
		log.info("Converted into AdminDto..!!");
		return adminDto;
	}

	@Override
	public AdminDto getAdmin(String email) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Admin admin2 = adminRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Admin not found with Email :" + email));

		AdminDto adminDto = userMapper.toAdminDto(admin2);
		return adminDto;
	}

	@Override
	public AgencyDto updateAgency(String email, Agency agency) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Agency agency2 = agencyRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Agency not found with email :" + email));
		agency2.setAgencyAddress(agency.getAgencyAddress());
		agency2.setAgencyName(agency.getAgencyName());
		agency2.setAgencyPinCode(agency.getAgencyPinCode());
		agency2.setEmail(agency.getEmail());
		agency2.setPhoneNumber(agency.getPhoneNumber());
		Agency agency3 = agencyRepository.save(agency2);

		AgencyDto agencyDto = userMapper.toAgencyDto(agency3);
		return agencyDto;
	}

	@Override
	public AgencyDto getAgency(String email) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Agency orElseThrow = agencyRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Agency not found with email :" + email));

		AgencyDto agencyDto = userMapper.toAgencyDto(orElseThrow);
		return agencyDto;
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
	public List<CustomerDto> getAllCustomers() {
		// TODO Auto-generated method stub
		List<Customer> all = customerRepository.findAll();

		List<CustomerDto> collect = all.stream().map(userMapper::toCustomerDto)
				.collect(Collectors.toList());
		return collect;
	}

	@Override
	public List<CustomerDto> getCustomersByAgency(String agencyName) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Agency agency2 = agencyRepository.findByAgencyName(agencyName)
				.orElseThrow(() -> new UserNotFoundException("Agency not found with email :" + agencyName));
		List<Customer> byAgency = customerRepository.findByAgencyId(agency2.getId());

		List<CustomerDto> collect = byAgency.stream().map(userMapper::toCustomerDto)
				.collect(Collectors.toList());
		return collect;
	}

	@Override
	public List<AgentDto> getAllAgents() {
		// TODO Auto-generated method stub
		List<Agent> all = agentRepository.findAll();
		List<AgentDto> collect = all.stream().map(userMapper::toAgentDto)
				.collect(Collectors.toList());
		return collect;
	}

	@Override
	public List<AgentDto> getAgentsByAgency(String agencyName) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Agency agency2 = agencyRepository.findByAgencyName(agencyName)
				.orElseThrow(() -> new UserNotFoundException("Agency not found with email :" + agencyName));
		List<Agent> list = agentRepository.findByAgencyId(agency2.getId());

		List<AgentDto> collect = list.stream().map(userMapper::toAgentDto)
				.collect(Collectors.toList());
		return collect;
	}

	@Override
	public List<AgencyDto> getAllAgencies() {
		// TODO Auto-generated method stub
		 List<Agency> all = agencyRepository.findAll();
		 
		 List<AgencyDto> collect = all.stream().map(userMapper::toAgencyDto).collect(Collectors.toList());
		 return collect;
	}
}
