package com.realestate.main.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuspensionDto {
	private int suspensionId;
	private String agentName;
	private String agentEmail;
	private Date effectiveDate;
	private String reason;
	private String remarks;
	private String issuedBy;
	private Date issueDate;
	private String rejoinEligibility;
}
