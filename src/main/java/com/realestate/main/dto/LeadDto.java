package com.realestate.main.dto;

import java.util.Date;
import java.util.List;

import com.realestate.main.entity.Agent;
import com.realestate.main.entity.Token;
import com.realestate.main.entity.Visit;

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
	private Agent agent;
	private List<Token> tokens;
	private List<Visit> visits;

}
