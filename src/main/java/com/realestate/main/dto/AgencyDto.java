package com.realestate.main.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.realestate.main.entity.Agent;
import com.realestate.main.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgencyDto {
	
	private long id;
	private String email;
	private String password;
	private String phoneNumber;
	private Set<Role> roles;
	private String agencyName;
	private String agencyAddress;
	private int agencyPinCode;
	private String city;
	private String state;
	private String fbUrl;
	private String instagramUrl;
	private String twitterUrl;
	private LocalDate registrationDate;
	private List<Agent> agents;
	private String status;
}
