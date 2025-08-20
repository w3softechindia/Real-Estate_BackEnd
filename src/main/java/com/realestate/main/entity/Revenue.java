package com.realestate.main.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Revenue {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long revenueId;
	private double revenue;
	private Date transactionDate;
	
	@ManyToOne
	@JsonBackReference
	private Agent agent;
}
