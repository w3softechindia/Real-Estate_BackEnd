package com.realestate.main.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RealEStateUserDto {

	private String email;
	private String password;
	private long phoneNumber;
	private Set<String> roles;
}
