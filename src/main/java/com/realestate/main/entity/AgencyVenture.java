package com.realestate.main.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AgencyVenture {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne
	@JsonBackReference("agency-venture")
	private Agency agencies;

	@ManyToOne
	@JsonBackReference("venture-agency")
	private Venture venture;

	private int numberOfPlots;

	@ManyToMany
	@JoinTable(name = "agency_venture_plots", joinColumns = @JoinColumn(name = "agency_venture_id"), inverseJoinColumns = @JoinColumn(name = "plot_id"))
	private List<Plots> plotsAssigned;
}
