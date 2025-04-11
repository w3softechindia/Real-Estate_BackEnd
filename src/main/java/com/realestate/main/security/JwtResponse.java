package com.realestate.main.security;

import com.realestate.main.entity.RealEStateUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

	private String jwtToken;
	private RealEStateUser eStateUser;
}
