package com.realestate.main.service;

import com.realestate.main.entity.Admin;
import com.realestate.main.entity.Agency;

public interface AdminService {
	
	void addRole();
	
	Admin addAdmin(Admin admin);
	
	Agency addAgency(Agency agency);
	
}
