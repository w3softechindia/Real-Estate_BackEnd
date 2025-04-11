package com.realestate.main.serviceImpl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.realestate.main.entity.Admin;
import com.realestate.main.entity.Agency;
import com.realestate.main.entity.Role;
import com.realestate.main.repository.AdminRepository;
import com.realestate.main.repository.AgencyRepository;
import com.realestate.main.repository.RoleRepository;
import com.realestate.main.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private AgencyRepository agencyRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder(12);
	
	@Override
	public void addRole() {
		// TODO Auto-generated method stub
		Role role1=new Role();
		role1.setRoleName("Admin");
		roleRepository.save(role1);
		
		Role role2=new Role();
		role2.setRoleName("Agency");
		roleRepository.save(role2);
		
		Role role3=new Role();
		role3.setRoleName("Agent");
		roleRepository.save(role3);
		
		Role role4=new Role();
		role4.setRoleName("Customer");
		roleRepository.save(role4);
	}

	@Override
	public Admin addAdmin(Admin admin) {
		// TODO Auto-generated method stub
		Role role = roleRepository.findById("Admin").get();
		Set<Role> roles=new HashSet<Role>();
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
		Set<Role> roles=new HashSet<Role>();
		roles.add(role);
		agency.setRoles(roles);
		agency.setPassword(bCryptPasswordEncoder.encode(agency.getPassword()));
		Agency agency2 = agencyRepository.save(agency);
		return agency2;
	}

}
