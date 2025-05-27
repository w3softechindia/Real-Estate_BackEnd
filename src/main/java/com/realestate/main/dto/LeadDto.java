package com.realestate.main.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeadDto {
	
	private int leadId;
	private String leadName;
	private String phoneNumber;
	private String email;
	private String leadSource;
	private String interestedIn;
	private String preferredLocation;
	private double estimatedBudget;
	private String leadStatus;
	private Date followUp;
	private String leadNotes;

}
