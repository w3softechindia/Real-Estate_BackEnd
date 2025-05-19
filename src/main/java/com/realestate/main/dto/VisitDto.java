package com.realestate.main.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitDto {
	private int visitId;
	private String leadName;
	private String propertyType;
	private LocalDate visitDate;
	private LocalTime visitTime;
	private String notes;
	private String customerFeedback;
	private String nextStep;
}
