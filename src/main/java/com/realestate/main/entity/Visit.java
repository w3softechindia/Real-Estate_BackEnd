package com.realestate.main.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
	private String propertyType;
	private LocalDate visitDate;
	private LocalTime visitTime;
	private String notes;
	private String customerFeedback;
	private String nextStep;
	private String status;
	private String reason;
	
	@ManyToOne
	@JsonBackReference(value = "lead-visits")
	private Lead lead;
	
	 @ManyToOne
	 @JsonBackReference(value = "venture-visits")
	 private Venture venture;
}
