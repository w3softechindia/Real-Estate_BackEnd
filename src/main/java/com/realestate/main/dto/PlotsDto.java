package com.realestate.main.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import com.realestate.main.entity.PropertyStatus;
import com.realestate.main.entity.Venture;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlotsDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5633359406771884981L;
	private long plotId;
	private String plotNumber;
	private Double plotSize; // In square yards or square feet
	private BigDecimal price;
	private PropertyStatus status;
	private String location;
	private String facing; // East, West, North, South
	private boolean isCornerPlot;
	
	private Venture venture;
}
