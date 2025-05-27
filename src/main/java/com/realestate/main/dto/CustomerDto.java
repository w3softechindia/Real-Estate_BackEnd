package com.realestate.main.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import com.realestate.main.entity.Agency;
import com.realestate.main.entity.Agent;
import com.realestate.main.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1508922430136216424L;
	private long id;
	private String email;
	private String password;
	private String phoneNumber;
	private Set<Role> roles;
	private String address;
	private String customerName;
	private int pincode;
	private String city;
	private String state;
	private LocalDate registrationDate;
	private Agent agent;
	private String agencyName;
}
