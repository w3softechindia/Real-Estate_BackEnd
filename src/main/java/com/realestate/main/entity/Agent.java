package com.realestate.main.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8673791853055528320L;
	private String agentName;
	private String address;
	private int pincode;
	private String city;
	private String state;
	private LocalDate registrationDate;
	private String status;
	
	@ManyToOne
	@JsonBackReference
	private Agency agency;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "agent")
	@JsonManagedReference
	private List<Customer> customers;
}
