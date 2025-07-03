package com.realestate.main.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2359113032282549333L;
	private String agencyName;
	private String agencyAddress;
	private int agencyPinCode;
	private String city;
	private String state;
	private String fbUrl;
	private String instagramUrl;
	private String twitterUrl;
	private LocalDate registrationDate;
	private String status;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "agency")
	@JsonManagedReference("agency-agent")
	private List<Agent> agents;
	
	@OneToMany(mappedBy = "agencies", cascade = CascadeType.ALL)
	@JsonManagedReference("agency-venture")
	private List<AgencyVenture> agencyVentures;
}
