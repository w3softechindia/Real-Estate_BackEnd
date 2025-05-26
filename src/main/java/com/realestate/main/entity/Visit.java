package com.realestate.main.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Visit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int visitId;
	private String leadName;
	private String propertyType;
	private LocalDate visitDate;
	private LocalTime visitTime;
	private String notes;
	private String customerFeedback;
	private String nextStep;
	private String status;
	private double amount;
	private String transactionMode;
	private String agencyStatus;
	private String tokenId;
}
