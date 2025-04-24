package com.realestate.main.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
	
	private String agencyName;
	private String agencyAddress;
	private int agencyPinCode;
	private String city;
	private String state;
	private String fbUrl;
	private String instagramUrl;
	private String twitterUrl;
	private LocalDate registrationDate;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "agency")
	@JsonManagedReference
	private List<Agent> agents;
}
