package com.realestate.main.entity;

import java.io.Serializable;
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
public class Venture implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7907606992877379844L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ventureId;

	private String ventureName; 
	private String ventureSize;
	private long totalPlots;
	private long availablePlots;
	private long bookedPlots;
	private long soldPlots;
	private String address;
	private String city;
	private String state;
	private long phno;
	private long pincode;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "venture")
	@JsonManagedReference
	private List<Plots> plots;

}
