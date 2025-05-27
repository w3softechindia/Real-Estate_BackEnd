package com.realestate.main.entity;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends RealEStateUser{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3618457476902143482L;
	private String address;
	private String customerName;
	private int pincode;
	private String city;
	private String state;
	private LocalDate registrationDate;
	
	@ManyToOne
	@JsonBackReference
	private Agent agent;
	
	private String agencyName;
}
