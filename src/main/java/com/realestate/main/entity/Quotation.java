package com.realestate.main.entity;

import java.util.Date;

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
public class Quotation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int quotationId;
	private String propertyName;
	private double basePrice;
	private double gstPercentage;
	private double gstAmount;
	private double registrationCharges;
	private double maintenanceCharges; 
	private double discountPercentage;
	private double discountAmount;
	private double finalPrice;
	private Date createdDate;
	private Date validityDate;
	private String termsAndConditions;
	private String createdBy;
}
