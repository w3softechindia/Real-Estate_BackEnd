package com.realestate.main.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import com.realestate.main.entity.Lead;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitDto {
	private int visitId;
	private String propertyType;
	private LocalDate visitDate;
	private LocalTime visitTime;
	private String notes;
	private String customerFeedback;
	private String nextStep;
	private String status;
	private String reason;
	private Lead lead;
}
