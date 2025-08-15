package com.realestate.main.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realestate.main.dto.AdminDto;
import com.realestate.main.dto.AgencyDto;
import com.realestate.main.dto.AgencyVentureDto;
import com.realestate.main.dto.AgentDto;
import com.realestate.main.dto.CustomerDto;
import com.realestate.main.dto.PlotsDetailsDto;
import com.realestate.main.dto.PlotsDto;
import com.realestate.main.dto.VentureDto;
import com.realestate.main.entity.Admin;
import com.realestate.main.entity.Agency;
import com.realestate.main.entity.Plots;
import com.realestate.main.entity.RealEStateUser;
import com.realestate.main.entity.Reviews;
import com.realestate.main.entity.Venture;
import com.realestate.main.exceptions.AgencyNotFoundException;
import com.realestate.main.exceptions.DuplicateEntryException;
import com.realestate.main.exceptions.PropertyNotFoundException;
import com.realestate.main.exceptions.UserNotFoundException;
import com.realestate.main.service.AdminService;

import jakarta.annotation.PostConstruct;

@RestController
public class AdminController {

	@Autowired
	private AdminService adminService;

	@PostConstruct
	public void addRoles() {
		adminService.addRole();
	}

	@PostMapping("/addAdmin")
	public ResponseEntity<AdminDto> addAdmin(@RequestBody Admin admin) throws DuplicateEntryException {
		AdminDto admin2 = adminService.addAdmin(admin);
		return new ResponseEntity<AdminDto>(admin2, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('Admin')")
	@PostMapping("/addAgency")
	public ResponseEntity<AgencyDto> addAgency(@RequestBody Agency agency) throws Exception {
		AgencyDto agency2 = adminService.addAgency(agency);
		return new ResponseEntity<AgencyDto>(agency2, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('Admin')")
	@PutMapping("/updateAdmin")
	public ResponseEntity<AdminDto> updateAdmin(@RequestParam String email, @RequestBody Admin admin)
			throws UserNotFoundException {
		AdminDto updateAdmin = adminService.updateAdmin(email, admin);
		return new ResponseEntity<AdminDto>(updateAdmin, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('Admin')")
	@GetMapping("/getAdmin")
	public ResponseEntity<AdminDto> getAdmin(@RequestParam String email) throws UserNotFoundException {
		AdminDto admin = adminService.getAdmin(email);
		return new ResponseEntity<AdminDto>(admin, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('Admin') or hasRole('Agency')")
	@PutMapping("/updateAgency")
	public ResponseEntity<AgencyDto> updateAgency(@RequestParam String email, @RequestBody Agency agency)
			throws UserNotFoundException {
		AgencyDto updateAgency = adminService.updateAgency(email, agency);
		return new ResponseEntity<AgencyDto>(updateAgency, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('Admin','Agency')")
	@GetMapping("/getAgency")
	public ResponseEntity<AgencyDto> getAgency(@RequestParam String email) throws UserNotFoundException {
		AgencyDto agency = adminService.getAgency(email);
		return new ResponseEntity<AgencyDto>(agency, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('Admin')")
	@DeleteMapping("/deleteAgency")
	public ResponseEntity<String> deleteAgency(@RequestParam String email) throws UserNotFoundException {
		String deleteAgency = adminService.deleteAgency(email);
		return new ResponseEntity<String>(deleteAgency, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('Admin')")
	@GetMapping("/getAllCustomers")
	public ResponseEntity<List<CustomerDto>> getAllCustomers() {
		List<CustomerDto> allCustomers = adminService.getAllCustomers();
		return new ResponseEntity<List<CustomerDto>>(allCustomers, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('Admin','Agency')")
	@GetMapping("/getCustomersByAgency")
	public ResponseEntity<List<CustomerDto>> getCustomersByAgency(@RequestParam String agencyName)
			throws UserNotFoundException {
		List<CustomerDto> customersByAgency = adminService.getCustomersByAgency(agencyName);
		return new ResponseEntity<List<CustomerDto>>(customersByAgency, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('Admin') or hasRole('Agency')")
	@GetMapping("/getAllAgents")
	public ResponseEntity<List<AgentDto>> getAllAgents() {
		List<AgentDto> allAgents = adminService.getAllAgents();
		return new ResponseEntity<List<AgentDto>>(allAgents, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('Admin') or hasRole('Agency')")
	@GetMapping("/getAgentsByAgency")
	public ResponseEntity<List<AgentDto>> getAgentsByAgency(String agencyName) throws UserNotFoundException {
		List<AgentDto> agentsByAgency = adminService.getAgentsByAgency(agencyName);
		return new ResponseEntity<List<AgentDto>>(agentsByAgency, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('Admin')")
	@GetMapping("/getAllAgencies")
	public ResponseEntity<List<AgencyDto>> getAllAgencies() {
		List<AgencyDto> allAgencies = adminService.getAllAgencies();
		return new ResponseEntity<List<AgencyDto>>(allAgencies, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('Admin')")
	@PostMapping("/addVenture")
	public ResponseEntity<VentureDto> addVenture(@RequestParam String jsonVenture, @RequestParam MultipartFile file)
			throws IOException {
		// Convert String to Object
		ObjectMapper objectMapper = new ObjectMapper();
		Venture venture = objectMapper.readValue(jsonVenture, Venture.class);

		VentureDto landProperty2 = adminService.addVenture(venture, file);
		return new ResponseEntity<VentureDto>(landProperty2, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('Admin')")
	@PutMapping("/updateVenture")
	public ResponseEntity<VentureDto> updateVenture(@RequestParam long id, @RequestBody Venture venture)
			throws PropertyNotFoundException {
		VentureDto updateLandProperty = adminService.updateVenture(id, venture);
		return new ResponseEntity<VentureDto>(updateLandProperty, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('Admin','Agency','Agent')")
	@GetMapping("/getVenture")
	public ResponseEntity<VentureDto> getVenture(@RequestParam long id) throws PropertyNotFoundException {
		VentureDto landProperty = adminService.getVenture(id);
		return new ResponseEntity<VentureDto>(landProperty, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('Admin')")
	@DeleteMapping("/deleteVenture")
	public ResponseEntity<String> deleteVenture(@RequestParam long id) throws PropertyNotFoundException {
		String deleteVenture = adminService.deleteVenture(id);
		return new ResponseEntity<String>(deleteVenture, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('Admin','Agency','Agent')")
	@GetMapping("/getAllVentures")
	public ResponseEntity<List<VentureDto>> getAllVentures() {
		List<VentureDto> allLandProperties = adminService.getAllVentures();
		return new ResponseEntity<List<VentureDto>>(allLandProperties, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('Admin')")
	@PostMapping("/addPlot")
	public ResponseEntity<PlotsDto> addPlot(@RequestParam long ventureId, @RequestBody Plots plots)
			throws PropertyNotFoundException {
		PlotsDto plot = adminService.addPlot(ventureId, plots);
		return new ResponseEntity<PlotsDto>(plot, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('Admin')")
	@PostMapping("/addAllPlots")
	public ResponseEntity<List<PlotsDto>> addAllPlots(@RequestParam long ventureId, @RequestParam MultipartFile file)
			throws PropertyNotFoundException, IOException {
		List<PlotsDto> allPlots = adminService.addAllPlots(ventureId, file);
		return new ResponseEntity<List<PlotsDto>>(allPlots, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('Admin')")
	@PutMapping("/updatePlot")
	public ResponseEntity<PlotsDto> updatePlot(@RequestParam long plotId, @RequestBody Plots plots)
			throws PropertyNotFoundException {
		PlotsDto updatePlot = adminService.updatePlot(plotId, plots);
		return new ResponseEntity<PlotsDto>(updatePlot, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('Admin','Agency','Agent')")
	@GetMapping("/getPlot")
	public ResponseEntity<PlotsDto> getPlot(@RequestParam long plotId) throws PropertyNotFoundException {
		PlotsDto plot = adminService.getPlot(plotId);
		return new ResponseEntity<PlotsDto>(plot, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('Admin')")
	@DeleteMapping("/deletePlot")
	public ResponseEntity<String> deletePlot(@RequestParam long plotId) throws PropertyNotFoundException {
		String deletePlot = adminService.deletePlot(plotId);
		return new ResponseEntity<String>(deletePlot, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('Admin')")
	@GetMapping("/countOfVentures")
	public ResponseEntity<Long> countOfVentures() {
		Long countOfVentures = adminService.countOfVentures();
		return new ResponseEntity<Long>(countOfVentures, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('Admin')")
	@GetMapping("/countOfAgencies")
	public ResponseEntity<Long> countOfAgencies() {
		Long countOfAgencies = adminService.countOfAgencies();
		return new ResponseEntity<Long>(countOfAgencies, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('Admin','Agency')")
	@GetMapping("/countOfAgents")
	public ResponseEntity<Long> countOfAgents() {
		Long countOfAgents = adminService.countOfAgents();
		return new ResponseEntity<Long>(countOfAgents, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('Admin','Agency')")
	@GetMapping("/countOfCustomers")
	public ResponseEntity<Long> countOfCustomers() {
		Long countOfCustomers = adminService.countOfCustomers();
		return new ResponseEntity<Long>(countOfCustomers, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('Admin')")
	@PostMapping("/assignVenturesWithPlots")
	public ResponseEntity<AgencyVentureDto> assignVentures(@RequestParam long agencyId, @RequestParam long ventureId,
			@RequestParam long stPlotNum, @RequestParam long edPlotNum)
			throws AgencyNotFoundException, PropertyNotFoundException {
		AgencyVentureDto assignVentureWithPlots = adminService.assignVentureWithPlots(agencyId, ventureId, stPlotNum,
				edPlotNum);
		return new ResponseEntity<AgencyVentureDto>(assignVentureWithPlots, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('Admin','Agency','Agent')")
	@GetMapping("/countPlotsByVentureId")
	public ResponseEntity<PlotsDetailsDto> countPlotsByVentureId(@RequestParam long ventureId)
			throws PropertyNotFoundException {
		PlotsDetailsDto countPlotsByVentureId = adminService.countPlotsByVentureId(ventureId);
		return new ResponseEntity<PlotsDetailsDto>(countPlotsByVentureId, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('Admin','Agency','Agent')")
	@GetMapping("/getPlotsByVentureId")
	public ResponseEntity<List<Plots>> getPlotsByVentureId(@RequestParam long ventureId)
			throws PropertyNotFoundException {
		List<Plots> plotsByVentureId = adminService.getPlotsByVentureId(ventureId);
		return new ResponseEntity<List<Plots>>(plotsByVentureId, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('Admin','Agency')")
	@GetMapping("/getUnAssignedPlots")
	public ResponseEntity<List<Plots>> getUnAssignedPlots(@RequestParam long ventureId)
			throws PropertyNotFoundException {
		List<Plots> unAssignedPlots = adminService.getUnAssignedPlots(ventureId);
		return new ResponseEntity<List<Plots>>(unAssignedPlots, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('Admin','Agency','Agent')")
	@GetMapping("/getUserByEmail")
	public ResponseEntity<?> getUserByEmail(@RequestParam String email) throws UserNotFoundException {
		RealEStateUser userByEmail = adminService.getUserByEmail(email);
		return new ResponseEntity<>(userByEmail, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('Admin')")
	@GetMapping("/getActiveAgencies")
	public ResponseEntity<List<Agency>> getActiveAgencies() {
		List<Agency> activeAgencies = adminService.getActiveAgencies();
		return new ResponseEntity<List<Agency>>(activeAgencies, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('Admin','Agency')")
	@GetMapping("/getActiveVentures")
	public ResponseEntity<List<Venture>> getActiveVentures() {
		List<Venture> activeVentures = adminService.getActiveVentures();
		return new ResponseEntity<List<Venture>>(activeVentures, HttpStatus.OK);
	}

//	@PreAuthorize("hasAnyRole('Admin','Agency','Agent')")
//	@GetMapping("/countUnassignedPlots")
//	public ResponseEntity<Long> countUnassignedPlotsByVentureId(@RequestParam long ventureId) throws PropertyNotFoundException {
//		long countPlotsByVentureId = adminService.countUnassignedPlotsByVentureId(ventureId);
//		return new ResponseEntity<Long>(countPlotsByVentureId, HttpStatus.OK);
//	}
//	
//	@PreAuthorize("hasAnyRole('Admin','Agency','Agent')")
//	@GetMapping("/countAssignedPlots")
//	public ResponseEntity<Long> countAssignedPlotsByVentureId(@RequestParam long ventureId) throws PropertyNotFoundException {
//		long countPlotsByVentureId = adminService.countAssignedPlotsByVentureId(ventureId);
//		return new ResponseEntity<Long>(countPlotsByVentureId, HttpStatus.OK);
//	}

//	----------------------------------------------------------------------------------------

	@PreAuthorize("hasRole('Agency')")
	@PostMapping("/send")
	public ResponseEntity<Reviews> sendReviewByAgentEmail(@RequestParam String agentEmail, @RequestBody Reviews review,
			Principal principal) {

		String agencyEmail = principal.getName(); // logged-in agency's email

		Reviews savedReview = adminService.sendReview(agentEmail, agencyEmail, review.getReviewText());

		return ResponseEntity.ok(savedReview);
	}

	@PreAuthorize("hasRole('Agency')")
	@GetMapping("/agent/{agentEmail}")
	public ResponseEntity<List<Reviews>> getReviewsForAgent(@PathVariable String agentEmail) {
		return ResponseEntity.ok(adminService.getReviewsByAgentEmail(agentEmail));
	}
}
