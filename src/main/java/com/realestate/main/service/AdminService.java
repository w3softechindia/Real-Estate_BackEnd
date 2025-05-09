package com.realestate.main.service;

import java.util.List;

import com.realestate.main.dto.AdminDto;
import com.realestate.main.dto.AgencyDto;
import com.realestate.main.dto.AgentDto;
import com.realestate.main.dto.CustomerDto;
import com.realestate.main.dto.PlotsDto;
import com.realestate.main.dto.VentureDto;
import com.realestate.main.entity.Admin;
import com.realestate.main.entity.Agency;
import com.realestate.main.entity.Plots;
import com.realestate.main.entity.Venture;
import com.realestate.main.exceptions.PropertyNotFoundException;
import com.realestate.main.exceptions.UserNotFoundException;

public interface AdminService {
	
	void addRole();
	
	AdminDto addAdmin(Admin admin);
	
	AdminDto updateAdmin(String email, Admin admin) throws UserNotFoundException;
	
	AdminDto getAdmin(String email) throws UserNotFoundException;
	
	AgencyDto addAgency(Agency agency);
	
	AgencyDto updateAgency(String email, Agency agency) throws UserNotFoundException;
	
	AgencyDto getAgency(String email) throws UserNotFoundException;
	
	String deleteAgency(String email) throws UserNotFoundException;
	
	List<AgencyDto> getAllAgencies();
	
	List<CustomerDto> getAllCustomers();
	
	List<CustomerDto> getCustomersByAgency(String agencyName) throws UserNotFoundException;

	List<AgentDto> getAllAgents();
	
	List<AgentDto> getAgentsByAgency(String agencyName) throws UserNotFoundException;
	
	VentureDto addVenture(Venture venture);
	
	VentureDto updateVenture(long id, Venture venture) throws PropertyNotFoundException;
	
	VentureDto getVenture(long id) throws PropertyNotFoundException;
	
	String deleteVenture(long id) throws PropertyNotFoundException;

	List<VentureDto> getAllVentures();
	
	PlotsDto addPlot(long ventureId, Plots plots) throws PropertyNotFoundException;
	
	List<PlotsDto> addAllPlots(long ventureId, List<Plots> plots) throws PropertyNotFoundException;
	
	PlotsDto updatePlot(long plotId, Plots plots) throws PropertyNotFoundException;
	
	PlotsDto getPlot(long plotId) throws PropertyNotFoundException;
	
	String deletePlot(long plotId) throws PropertyNotFoundException;
	
	Long countOfVentures();
	
	Long countOfAgencies();
	
	Long countOfAgents();
	
	Long countOfCustomers();
}
