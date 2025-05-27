package com.realestate.main.entity;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authority implements GrantedAuthority{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5204382211511392832L;
	private String authority;

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.authority;
	}
}
