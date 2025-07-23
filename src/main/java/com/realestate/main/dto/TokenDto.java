package com.realestate.main.dto;

import java.time.LocalDate;

import com.realestate.main.entity.Lead;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
	private int tokenid;
	private double amount;
	private String transactionMode;
	private String agencyStatus;
	private LocalDate tokenDeadLine;
	private Lead lead;
	private String agentName;
}
