package com.realestate.main.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Agent extends RealEStateUser{
	
	private String address;
	private int pincode;
	private String city;
	private String state;
}
