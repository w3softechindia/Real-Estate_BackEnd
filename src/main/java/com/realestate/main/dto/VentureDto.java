package com.realestate.main.dto;

import java.util.List;

import com.realestate.main.entity.Plots;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentureDto {
	
	private long ventureId;

	private String ventureName; 
	private String ventureSize;
	private String address;
	private int totalPlots;
	private int availablePlots;
	private int bookedPlots;
	private int soldPlots;
	private String city;
	private String state;
	private long phno;
	private long pincode;
	private List<Plots> plots;
}
