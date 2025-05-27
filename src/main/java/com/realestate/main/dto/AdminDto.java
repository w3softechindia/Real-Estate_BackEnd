package com.realestate.main.dto;

import java.io.Serializable;
import java.util.Set;

import com.realestate.main.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5978487871881636865L;
	private String adminName;
	private long id;
	private String email;
	private String password;
	private String phoneNumber;
	private Set<Role> roles;
}
