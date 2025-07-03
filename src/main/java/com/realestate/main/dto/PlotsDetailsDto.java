package com.realestate.main.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlotsDetailsDto {
	
	private long countOfPlots;
	private long countOfAssignedPlots;
	private long countOfUnAssignedPlots;
	private long countOfSoldPlots;
	private long countOfAvailablePlots;
	private long countOfBookedPlots;
}
