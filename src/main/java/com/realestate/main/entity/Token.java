package com.realestate.main.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Token {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int tokenid;
	private double amount;
	private String transactionMode;
	private String agencyStatus;
	private LocalDate tokenDeadLine;
	private String agentName;
	private String finalStatus;
	private long finalAmount;

	
	@ManyToOne
	@JsonBackReference(value = "lead-tokens")
	private Lead lead;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "venture_id", referencedColumnName = "ventureId")
	@JsonBackReference(value = "venture-tokens")
	@JsonIgnoreProperties({"token", "plots", "visits", "agencyVentures"}) 
	private Venture venture;
	



}

