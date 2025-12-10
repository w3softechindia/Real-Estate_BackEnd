package com.realestate.main.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuotationDto {
	private int quotationId;
	private String propertyName;
	private double baseprice;
	private double gstPercentage;
	private double gstAmount;
	private double registrationCharges;
	private double maintainanceCharges;
	private double discountPercentage;
	private double discountAmount;
	private double finalPrice;
	private Date createdDate;
	private Date validityDate;
	private String termsAndConditions;
	private String createdBy;
}
