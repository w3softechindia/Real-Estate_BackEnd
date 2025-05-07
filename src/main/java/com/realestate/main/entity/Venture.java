package com.realestate.main.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Venture {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ventureId;

	private String ventureName; 
	private String ventureSize;
	private int totalPlots;
	private int availablePlots;
	private int bookedPlots;
	private int soldPlots;
	private String address;
	private String city;
	private String state;
	private long phno;
	private long pincode;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "venture")
	@JsonManagedReference
	private List<Plots> plots;

}
