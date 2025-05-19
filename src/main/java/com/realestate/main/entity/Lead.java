package com.realestate.main.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "leads")
public class Lead {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
