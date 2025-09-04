package com.realestate.main.serviceImpl;

import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.realestate.main.dto.AgentDto;
import com.realestate.main.dto.CustomerDto;
import com.realestate.main.dto.LeadDto;
import com.realestate.main.dto.RevenueDto;
import com.realestate.main.dto.ReviewDto;
import com.realestate.main.dto.TokenDto;
import com.realestate.main.dto.VisitDto;
import com.realestate.main.entity.Agent;
import com.realestate.main.entity.Customer;
import com.realestate.main.entity.Lead;
import com.realestate.main.entity.Post;
import com.realestate.main.entity.Revenue;
import com.realestate.main.entity.Reviews;
import com.realestate.main.entity.Role;
import com.realestate.main.entity.Token;
import com.realestate.main.entity.Venture;
import com.realestate.main.entity.Visit;
import com.realestate.main.exceptions.AgentNotFoundException;
import com.realestate.main.exceptions.PropertyNotFoundException;
import com.realestate.main.exceptions.ReviewNotFoundException;
import com.realestate.main.exceptions.RoleNotFoundException;
import com.realestate.main.exceptions.TokenNotFoundException;
import com.realestate.main.exceptions.UserNotFoundException;
import com.realestate.main.exceptions.VisitNotFoundException;
import com.realestate.main.mapper.UserMapper;
import com.realestate.main.repository.AgentRepository;
import com.realestate.main.repository.CustomerRepository;
import com.realestate.main.repository.LeadRepository;
import com.realestate.main.repository.Postrepository;
import com.realestate.main.repository.RevenueRepository;
import com.realestate.main.repository.ReviewsRepository;
import com.realestate.main.repository.RoleRepository;
import com.realestate.main.repository.TokenRepository;
import com.realestate.main.repository.VentureRepository;
import com.realestate.main.repository.VisitRepository;
import com.realestate.main.service.AgentService;

@Service
public class AgentServiceImpl implements AgentService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AgentRepository agentRepository;

	@Autowired
	private RoleRepository roleRepository;

	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private LeadRepository leadRepository;
	
	@Autowired
	private VisitRepository visitRepository;
	
	@Autowired
	private TokenRepository tokenRepository;
	
	@Autowired
	private Postrepository postrepository;
	
	@Autowired
	private VentureRepository ventureRepository;
	
	@Autowired
	private RevenueRepository revenueRepository;

	@Autowired
	private ReviewsRepository reviewsRepository;


	@Override
	public CustomerDto addCustomer(String agentEmail, Customer customer)
			throws UserNotFoundException, RoleNotFoundException {
		// Set Role
		Set<Role> roles = new HashSet<Role>();
		Role role = roleRepository.findById("Customer")
				.orElseThrow(() -> new RoleNotFoundException("Role not found with Customer"));
		roles.add(role);
		customer.setRoles(roles);

		// Find Agent and Set Agent
		Agent agent2 = agentRepository.findByEmail(agentEmail)
				.orElseThrow(() -> new UserNotFoundException("Agent not found with email :" + agentEmail));
		customer.setAgent(agent2);
		customer.setAgencyName(agent2.getAgency().getAgencyName());
		customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
		customer.setRegistrationDate(LocalDate.now());
		Customer save = customerRepository.save(customer);
		
		CustomerDto customerDto = userMapper.toCustomerDto(save);
		return customerDto;
	}

	@Override
	public CustomerDto updateCustomer(String email, Customer customer) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Customer customer2 = customerRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Customer Not found with :" + email));
		customer2.setAddress(customer.getAddress());
		customer2.setCity(customer.getCity());
		customer2.setEmail(customer.getEmail());
		customer2.setPhoneNumber(customer.getPhoneNumber());
		customer2.setPincode(customer.getPincode());
		customer2.setState(customer.getState());
		Customer customer3 = customerRepository.save(customer2);
		
		CustomerDto customerDto = userMapper.toCustomerDto(customer3);
		return customerDto;
	}

	@Override
	public CustomerDto getCustomer(String email) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Customer customer2 = customerRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Customer Not found with :" + email));
		
		CustomerDto customerDto = userMapper.toCustomerDto(customer2);
		return customerDto;
	}
	
	
	@Override
	public LeadDto addLead(Lead lead,String agentemail) throws AgentNotFoundException {
		getAllLeadsByAgent(agentemail);
		Agent agent = agentRepository.findByEmail(agentemail).orElseThrow(
				()->new AgentNotFoundException("Agent not found with email :"+agentemail));
		
		lead.setAgent(agent);
		long leadCounts = agent.getLeadsCount();
		agent.setLeadsCount(leadCounts+1);		//agent.setLeadCounts(leadCounts+1);
		agentRepository.save(agent);
		
	      Lead saveLead = leadRepository.save(lead);
	      
	      LeadDto leadDto = userMapper.toLeadDto(saveLead);
	      return leadDto;
	}

	@Override
	public List<LeadDto> getAllLeadsByAgent(String email)throws AgentNotFoundException {
	Agent agent = agentRepository.findByEmail(email).orElseThrow(
			()-> new AgentNotFoundException("Agent with email :"+email+" is not found"));
	
		List<Lead> allLeads = leadRepository.findAllByAgentEmail(agent.getEmail());
		agent.setLeadsCount(allLeads.size());
		agentRepository.save(agent);
		List<LeadDto> collect = allLeads.stream().map(userMapper::toLeadDto).collect(Collectors.toList());
				return collect;
	}

	@Override
	public VisitDto addVisit(Visit visit,int leadId,long ventureId) throws UserNotFoundException, PropertyNotFoundException{
		Lead lead = leadRepository.findById(leadId).
				orElseThrow(()->new UserNotFoundException("Lead With ID : "+leadId+" is Not Found....."));
		
		Venture venture = ventureRepository.findById(ventureId)
		.orElseThrow(()->new PropertyNotFoundException("Venture with Id :"+ventureId+"is not found......"));
		
		visit.setLead(lead);		
		visit.setVenture(venture);
		Visit saveVisit = visitRepository.save(visit);
	
		VisitDto visitDto = userMapper.toVisitDto(saveVisit);
		return visitDto;
	}


	
	public List<VisitDto> getAllVisits()  {
	    List<Visit> visits = visitRepository.findAll();

      List<VisitDto> allVisits = visits.stream().map(userMapper::toVisitDto).collect(Collectors.toList());
      return allVisits;
	}


	@Override
	public LeadDto updateLead(Lead lead, String email) throws UserNotFoundException {
		Lead findLead = leadRepository.findByEmail(email).orElseThrow(
				()-> new UserNotFoundException("Lead With Email "+email+" is not found......"));
		
		findLead.setLeadName(lead.getLeadName());
		findLead.setEstimatedBudget(lead.getEstimatedBudget());
		findLead.setInterestedIn(lead.getInterestedIn());
		findLead.setFollowUp(lead.getFollowUp());
		findLead.setLeadNotes(lead.getLeadNotes());
		findLead.setLeadSource(lead.getLeadSource());
		findLead.setLeadStatus(lead.getLeadStatus());
		findLead.setEmail(lead.getEmail());
		findLead.setPhoneNumber(lead.getPhoneNumber());
		findLead.setPreferredLocation(lead.getPreferredLocation());
		
		Lead updatedLead = leadRepository.save(findLead);
		
		LeadDto leadDto = userMapper.toLeadDto(updatedLead);
		
		return leadDto;
	}

	@Override
	public String deleteLead(String email) throws UserNotFoundException {
		Lead deleteLead = leadRepository.findByEmail(email).orElseThrow(
				()->new UserNotFoundException("Lead With Email :"+email+" is not found...."));
		leadRepository.delete(deleteLead);
		return "Lead Deleted SucessFully.....";
	}
	
	@Override
	public VisitDto updateVisitStatus(int visitId, String status,String reason) throws VisitNotFoundException {
		Visit updateVisit = visitRepository.findById(visitId).orElseThrow(
				()-> new VisitNotFoundException("Visit with Id :"+visitId+" is not found..."));
		
		updateVisit.setStatus(status);
		updateVisit.setReason(reason);
		
		 Visit save = visitRepository.save(updateVisit);
		 
		 VisitDto visitDto = userMapper.toVisitDto(save);
		 
		 return visitDto;
		 
	}


	@Override
	public AgentDto updateProfile(Agent agent, String email) throws AgentNotFoundException {
		Agent agentDetails = agentRepository.findByEmail(email).orElseThrow(
				()-> new AgentNotFoundException("Agent Not Found with email :"+ email));
		
		agentDetails.setPhoneNumber(agent.getPhoneNumber());
		agentDetails.setAddress(agent.getAddress());
		agentDetails.setCity(agent.getCity());
		agentDetails.setState(agent.getState());
		agentDetails.setPincode(agent.getPincode());
		
		Agent updatedAgent = agentRepository.save(agentDetails);
		
		AgentDto agentDto = userMapper.toAgentDto(updatedAgent);
		return agentDto;
	}

	@Override
	public TokenDto addToken(int leadId, Token token) throws UserNotFoundException {
		 Lead lead = leadRepository.findById(leadId).orElseThrow(
				()->new UserNotFoundException("Lead With Id  :"+leadId+" is not Found..."));
		
		 token.setLead(lead);
		Token save = tokenRepository.save(token);
		
		TokenDto tokenDto=userMapper.toTokenDto(save);
		
		return tokenDto;
	}

	@Override
	public List<TokenDto> getAllTokens() {
		List<Token> all = tokenRepository.findAll();
		List<TokenDto> allTokens = all.stream().map(userMapper::toTokenDto).collect(Collectors.toList());
		return allTokens;
	}

	@Override
	public List<Post> getAllPosts() {
		return postrepository.findAll();
		
	}

	@Override
	public List<VisitDto> getVisitsByStatus(String status) throws VisitNotFoundException {
		List<Visit> byStatus = visitRepository.findByStatus(status);
		if(byStatus.isEmpty()) {
			throw new VisitNotFoundException(" Visits NotFound with Status :"+status);
		}
		
		List<VisitDto> collect = byStatus.stream().map(userMapper::toVisitDto).collect(Collectors.toList());
		return collect;
	}

	@Override
	public List<TokenDto> getAllTokensByAgencyStatus(String agencyStatus) throws TokenNotFoundException {
		// TODO Auto-generated method stub
		List<Token> byAgencyStatus = tokenRepository.findByAgencyStatus(agencyStatus);
		if(byAgencyStatus.isEmpty()) {
			throw new TokenNotFoundException("Token Not Found With AgencyStatus :"+agencyStatus);
		}
		
		return byAgencyStatus.stream().map(userMapper::toTokenDto).collect(Collectors.toList());
		
	}

	@Override
	public TokenDto makePayment(int tokenId, long finalAmount, String finalStatus) throws TokenNotFoundException {
		// TODO Auto-generated method stub
		Token tokenById = tokenRepository.findById(tokenId).
				orElseThrow(()-> new TokenNotFoundException("Token With ID is :"+tokenId+" is not present..."));
		tokenById.setFinalStatus(finalStatus);
		tokenById.setFinalAmount(finalAmount);
		
		Token save = tokenRepository.save(tokenById);
		 Lead lead = tokenById.getLead();
	        Agent agent = lead.getAgent();
	        Venture venture = tokenById.getVenture(); 
	        
	        Revenue revenue = new Revenue();
	        revenue.setAgent(agent);
	        revenue.setRevenue(venture.getPrice());  // or use finalAmount if that’s the actual paid amount
	        revenue.setTransactionDate(new Date());

	        revenueRepository.save(revenue);
		TokenDto tokenDto = userMapper.toTokenDto(save);
		return tokenDto;
	}

	@Override
	public Double getTotalRevenue(int agentId) throws UserNotFoundException {
		Double totalRevenueByAgent = revenueRepository.getTotalRevenueByAgent(agentId);
		if(totalRevenueByAgent==null) {
			throw new UserNotFoundException("Agent with ID is :"+agentId+" not having any revenue");
		}
		return totalRevenueByAgent;
	}

	 public Map<String, Double> getMonthlyRevenue(int agentId) {
	        List<Object[]> results = revenueRepository.getMonthlyRevenueByAgent(agentId);

	        Map<String, Double> monthlyRevenue = new LinkedHashMap<>();
	        for (Object[] row : results) {
	            int month = (int) row[0];
	            double revenue = (double) row[1];
	            monthlyRevenue.put(Month.of(month).name(), revenue);
	        }
	        return monthlyRevenue;
	    }

	@Override
	public ReviewDto reviewResponse(int reviewId,String response) throws ReviewNotFoundException {
		// TODO Auto-generated method stub
		Reviews reviews = reviewsRepository.findById(reviewId).
		orElseThrow(()-> new ReviewNotFoundException("Review With ID : "+reviewId+" Is Not Found......."));
		
		reviews.setResponse(response);		
		
		Reviews save = reviewsRepository.save(reviews);	
		ReviewDto reviewDto = userMapper.toReviewDto(save);
		return reviewDto;
		
		}


}
