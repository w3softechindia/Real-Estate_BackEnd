package com.realestate.main.dto;

import java.time.LocalDateTime;

import com.realestate.main.entity.Agency;
import com.realestate.main.entity.Agent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewDto {

	private int id;
	private Agent agent;
	private Agency agency;
	private String reviewText;
    private int rating;
    private LocalDateTime createdAt;
    private String response;
}
