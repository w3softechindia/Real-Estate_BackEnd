package com.realestate.main.dto;

import java.util.List;

import com.realestate.main.entity.Agency;
import com.realestate.main.entity.Plots;
import com.realestate.main.entity.Venture;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgencyVentureDto {
	
	private long id;
	private Agency agency;
	private Venture venture;
	private int numberOfPlots;
	private List<Plots> plotsAssigned;
}
