package com.realestate.main.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends RealEStateUser{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -40010880755397877L;
	private String adminName;
}
