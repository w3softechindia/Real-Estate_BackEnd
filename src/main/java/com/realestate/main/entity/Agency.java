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
public class Agency extends RealEStateUser{
	
	private String agencyName;
	private String agencyAddress;
	private int agencyPinCode;
	
}
