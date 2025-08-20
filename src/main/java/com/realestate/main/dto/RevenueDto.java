package com.realestate.main.dto;

import java.util.Date;

import com.realestate.main.entity.Agent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RevenueDto {

	private long revenueId;
	private double revenue;
	private Date transactionDate;
	private Agent agent;
}
