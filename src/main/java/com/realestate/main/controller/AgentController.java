package com.realestate.main.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PatchExchange;
import com.realestate.main.dto.AgentDto;
import com.realestate.main.dto.CustomerDto;
import com.realestate.main.dto.LeadDto;
import com.realestate.main.dto.TokenDto;
import com.realestate.main.dto.VisitDto;
import com.realestate.main.entity.Agent;
import com.realestate.main.entity.Customer;
import com.realestate.main.entity.Lead;
import com.realestate.main.entity.Post;
import com.realestate.main.entity.Token;
import com.realestate.main.entity.Visit;
import com.realestate.main.exceptions.AgentNotFoundException;
import com.realestate.main.exceptions.PropertyNotFoundException;
import com.realestate.main.exceptions.RoleNotFoundException;
import com.realestate.main.exceptions.TokenNotFoundException;
import com.realestate.main.exceptions.UserNotFoundException;
import com.realestate.main.exceptions.VisitNotFoundException;
import com.realestate.main.repository.VisitRepository;
import com.realestate.main.service.AgentService;

@RestController
public class AgentController {
	
	@Autowired
	private AgentService agentService;
	
	@PreAuthorize("hasRole('Agent')")
	@PostMapping("/addCustomer")
	public ResponseEntity<CustomerDto> addCustomer(@RequestParam String agentEmail,@RequestBody Customer customer) throws UserNotFoundException, RoleNotFoundException {
		CustomerDto customer2 = agentService.addCustomer(agentEmail, customer);
		return new ResponseEntity<CustomerDto>(customer2, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('Agent','Customer')")
	@PutMapping("/updateCustomer")
	public ResponseEntity<CustomerDto> updateCustomer(@RequestParam String email,@RequestBody Customer customer) throws UserNotFoundException {
		CustomerDto updateCustomer = agentService.updateCustomer(email, customer);
		return new ResponseEntity<CustomerDto>(updateCustomer, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('Agent','Customer','Admin','Agency')")
	@GetMapping("/getCustomer")
	public ResponseEntity<CustomerDto> getCustomer(@RequestParam String email) throws UserNotFoundException{
		CustomerDto customer = agentService.getCustomer(email);
		return new ResponseEntity<CustomerDto>(customer, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('Agent')")
	@PostMapping("/addLead")
	public ResponseEntity<LeadDto> addLead(@RequestBody Lead lead,@RequestParam String agentEmail) throws AgentNotFoundException{
		LeadDto lead2 =agentService.addLead(lead,agentEmail);
		return new ResponseEntity<LeadDto>(lead2,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('Agent')")
	@GetMapping("/getAllLeads")
	public ResponseEntity<List<LeadDto>> getAllLeads(@RequestParam String email) throws AgentNotFoundException{
		List<LeadDto> allLeads = agentService.getAllLeadsByAgent(email);
		return new ResponseEntity<List<LeadDto>>(allLeads,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('Agent')")
	@PostMapping("/addVisit")
	public ResponseEntity<VisitDto> addVisit(@RequestBody Visit visit,@RequestParam int leadId,@RequestParam long ventureId)throws UserNotFoundException, PropertyNotFoundException{
     VisitDto savedVisit = agentService.addVisit(visit,leadId,ventureId);
     return new ResponseEntity<VisitDto>(savedVisit,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('Agent')")
	@GetMapping("/getAllVisits")
	public ResponseEntity<List<VisitDto>> getAllVisits(){
		List<VisitDto> allVisits = agentService.getAllVisits();
		return new ResponseEntity<List<VisitDto>>(allVisits,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('Agent')")
	@PutMapping("/updateLead")
	public ResponseEntity<LeadDto> updateLead(@RequestBody Lead lead,@RequestParam String email) throws UserNotFoundException{
		LeadDto updateLead = agentService.updateLead(lead, email);
		return new ResponseEntity<LeadDto>(updateLead,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('Agent')")
	@DeleteMapping("/deleteLead")
	public ResponseEntity<String> deleteLead(@RequestParam String email) throws UserNotFoundException{
		String deleteLead = agentService.deleteLead(email);
		return new ResponseEntity<String>(deleteLead,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('Agent')")
	@PutMapping("/updateVisitStatus")
	public ResponseEntity<VisitDto> updateVisitStatus(@RequestParam int visitId,@RequestParam String status,@RequestParam String reason) throws VisitNotFoundException{
		VisitDto updateVisitStatus = agentService.updateVisitStatus(visitId, status,reason);
		return new ResponseEntity<VisitDto>(updateVisitStatus,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('Agent')")
	@PostMapping("/payment")
public ResponseEntity<TokenDto> addToken(@RequestParam int leadId,@RequestBody Token token) throws UserNotFoundException{
	 TokenDto payment = agentService.addToken(leadId, token);
return new ResponseEntity<TokenDto>(payment,HttpStatus.OK);
}
	
//	
//	@PreAuthorize("hasRole('Agency')")
//	@PutMapping("/acceptToken")
//	public ResponseEntity<VisitDto> acceptToken(@RequestParam String tokenId,@RequestParam String agencyStatus) throws VisitNotFoundException{
//		VisitDto acceptToken = agentService.acceptToken(tokenId,agencyStatus);
//		return new ResponseEntity<VisitDto>(acceptToken,HttpStatus.OK);
//	}
	
	@PreAuthorize("hasRole('Agent')")
	@PutMapping("/updateAgentProfile")
	public ResponseEntity<AgentDto> updateAgentProfile(@RequestBody Agent agent,@RequestParam String email) throws AgentNotFoundException{
		AgentDto updateProfile = agentService.updateProfile(agent, email);
		return new ResponseEntity<AgentDto>(updateProfile,HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('Agent','Agency')")
	@GetMapping("/getAllTokens")
	public ResponseEntity<List<TokenDto>> getAllTokens(){
		List<TokenDto> allTokens = agentService.getAllTokens();
		return new ResponseEntity<List<TokenDto>>(allTokens,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('Agent')")
	@GetMapping("/getAllPosts")
	public ResponseEntity<List<Post>> getAllPosts(){
		List<Post> allPosts = agentService.getAllPosts();
		return new ResponseEntity<List<Post>>(allPosts,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('Agent')")
	@GetMapping("/getVisitsByStatus")
	public ResponseEntity<List<VisitDto>> getAllVisitsByStatus(@RequestParam String status) throws VisitNotFoundException{
		List<VisitDto> visitsByStatus = agentService.getVisitsByStatus(status);
		return new ResponseEntity<List<VisitDto>>(visitsByStatus,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('Agent')")
	@GetMapping("/getTokensByAgency")
	public ResponseEntity<List<TokenDto>> getAllTokensByAgencyStatus(@RequestParam String agencyStatus)throws TokenNotFoundException{
		List<TokenDto> allTokensByAgencyStatus = agentService.getAllTokensByAgencyStatus(agencyStatus);
		return new ResponseEntity<List<TokenDto>>(allTokensByAgencyStatus,HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('Agent')")
	@PutMapping("/payment")
	public ResponseEntity<TokenDto> makepayment(@RequestParam int  tokenId,@RequestParam double finalAmount,@RequestParam String finalStatus) throws TokenNotFoundException{
		TokenDto payment = agentService.makePayment(tokenId, finalAmount, finalStatus);
		return new ResponseEntity<TokenDto>(payment,HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('Agent')")
	  @GetMapping("/total/{agentId}")
	    public ResponseEntity<Double> getTotalRevenue(@PathVariable int agentId) throws UserNotFoundException {
	         Double totalRevenue = agentService.getTotalRevenue(agentId);
	         return new ResponseEntity<>(totalRevenue,HttpStatus.OK);
	    }
	
	@PreAuthorize("hasRole('Agent')")
	 @GetMapping("/monthly/{agentId}")
	    public ResponseEntity<Map<String, Double>> getMonthlyRevenue(@PathVariable int agentId) {
	         Map<String,Double> monthlyRevenue = agentService.getMonthlyRevenue(agentId);
	         return new ResponseEntity<Map<String,Double>>(monthlyRevenue,HttpStatus.OK);
	    }
}
