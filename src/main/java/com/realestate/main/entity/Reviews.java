package com.realestate.main.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Reviews {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// The agent receiving the review
	@ManyToOne(optional = false)
	@JoinColumn(name = "agent_id", nullable = false)
	private Agent agent;

	// The agency writing the review
	@ManyToOne(optional = false)
	@JoinColumn(name = "agency_id", nullable = false)
	private Agency agency;

	@Column(nullable = false, length = 1000)
	private String reviewText;

	private int rating; // optional: 1–5 stars

	private LocalDateTime createdAt;
}
