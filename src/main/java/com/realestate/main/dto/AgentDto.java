package com.realestate.main.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.realestate.main.entity.Agency;
import com.realestate.main.entity.Customer;
import com.realestate.main.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3503946714286691004L;
	private long id;
	private String email;
	private String password;
	private String phoneNumber;
	private Set<Role> roles;
	private String agentName;
	private String address;
	private int pincode;
	private String city;
	private String state;
	private LocalDate registrationDate;
	private Agency agency;
	private List<Customer> customers;
	private String status;
}
