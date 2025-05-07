package com.realestate.main.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Plots {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long plotId;
	private String plotNumber;
	private Double plotSize; // In square yards or square feet
	private BigDecimal price;

	@Enumerated(EnumType.STRING)
	private PropertyStatus status;

	private String location;

	private String facing; // East, West, North, South

	private boolean isCornerPlot;
	
	@ManyToOne
	@JsonBackReference
	private Venture venture;
}
