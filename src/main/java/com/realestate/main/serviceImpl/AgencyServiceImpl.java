package com.realestate.main.serviceImpl;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.realestate.main.dto.AgentDto;
import com.realestate.main.dto.TokenDto;
import com.realestate.main.emailConfiguration.EmailUtil;
import com.realestate.main.entity.Agency;
import com.realestate.main.entity.Agent;
import com.realestate.main.entity.Post;
import com.realestate.main.entity.Role;
import com.realestate.main.entity.Token;
import com.realestate.main.exceptions.DuplicateEntryException;
import com.realestate.main.exceptions.UserNotFoundException;
import com.realestate.main.mapper.UserMapper;
import com.realestate.main.repository.AgencyRepository;
import com.realestate.main.repository.AgentRepository;
import com.realestate.main.repository.Postrepository;
import com.realestate.main.repository.RealEstateUserRepo;
import com.realestate.main.repository.RoleRepository;
import com.realestate.main.repository.TokenRepository;
import com.realestate.main.service.AgencyService;

import jakarta.transaction.Transactional;

@Service
public class AgencyServiceImpl implements AgencyService {

	@Autowired
	private AgentRepository agentRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private AgencyRepository agencyRepository;

	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private EmailUtil emailUtil;

	@Autowired
	private RealEstateUserRepo userRepo;
	
	@Autowired
	private TokenRepository tokenRepository;

	@Autowired
	private Postrepository postRepository;

	@Override
	public AgentDto addAgent(String agencyEmail, Agent agent) throws Exception {
		// TODO Auto-generated method stub
		if (userRepo.existsByEmail(agent.getEmail()))
			throw new DuplicateEntryException("Email Already Exists with :" + agent.getEmail());
		if (userRepo.existsByPhoneNumber(agent.getPhoneNumber()))
			throw new DuplicateEntryException("Phone Number Already exists :" + agent.getPhoneNumber());
		String password = agent.getPassword();
		Agency agency2 = agencyRepository.findByEmail(agencyEmail)
				.orElseThrow(() -> new UserNotFoundException("Agency not found with email :" + agencyEmail));
		Set<Role> roles = new HashSet<Role>();
		Optional<Role> byId = roleRepository.findById("Agent");
		if (byId.isPresent()) {
			Role role = byId.get();
			roles.add(role);
		}
		agent.setRoles(roles);
		agent.setPassword(bCryptPasswordEncoder.encode(agent.getPassword()));
		agent.setAgency(agency2);
		agent.setRegistrationDate(LocalDate.now());
		agent.setStatus("ACTIVE");
		Agent agent2 = agentRepository.save(agent);
		emailUtil.sendAgentRegistration(agent2.getEmail(), password, agency2.getAgencyName());

		AgentDto agentDto = userMapper.toAgentDto(agent2);
		return agentDto;
	}

	@Override
	public AgentDto updateAgent(String email, Agent agent) throws UserNotFoundException {
		Agent existingAgent = agentRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Agent not found with email: " + email));

		// Only updating the required fields
		existingAgent.setAgentName(agent.getAgentName());
		existingAgent.setAddress(agent.getAddress());
		existingAgent.setPincode(agent.getPincode());
		existingAgent.setCity(agent.getCity());
		existingAgent.setState(agent.getState());

		Agent updatedAgent = agentRepository.save(existingAgent);

		return userMapper.toAgentDto(updatedAgent);
	}

	@Override
	public AgentDto getAgent(String email) throws UserNotFoundException {
		// TODO Auto-generated method stub
		Agent agent2 = agentRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Agent not found with email :" + email));
		AgentDto agentDto = userMapper.toAgentDto(agent2);
		return agentDto;
	}

	@Override
	@Transactional
	public String deleteAgent(String email) throws UserNotFoundException {
		Agent agent = agentRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Agent not found with email: " + email));

		// Clear the roles to avoid foreign key constraint violation
		agent.getRoles().clear();
		agentRepository.save(agent); // Persist the removal of roles

		// Now safely delete the agent
		agentRepository.delete(agent);

		return "Agent Deleted Successfully..!!!";
	}

	@Override
	public Post addPost(String agencyEmail, Post post) throws Exception {
		Agency agency = agencyRepository.findByEmail(agencyEmail)
				.orElseThrow(() -> new UserNotFoundException("Agency not found with email: " + agencyEmail));

		post.setStartDate(LocalDate.now());
		post.setAgency(agency); // Assuming you have a `@ManyToOne` relation: Post → Agency
		Post savedPost = postRepository.save(post);

		return savedPost;
	}

	@Override
	public TokenDto acceptToken(int tokenId, String agencyStatus) throws UserNotFoundException {
		Token token = tokenRepository.findById(tokenId).orElseThrow(
				()->new UserNotFoundException("Token With Id :"+ tokenId+"is not found......"));
		
		token.setAgencyStatus(agencyStatus);
		
		Token save = tokenRepository.save(token);
		return userMapper.toTokenDto(save);
		 

	public Post updatePost(Long postId, Post updatedPost) throws Exception {
		Post existingPost = postRepository.findById(postId)
				.orElseThrow(() -> new UserNotFoundException("Post not found with ID: " + postId));

		existingPost.type = updatedPost.type;
		existingPost.title = updatedPost.title;
		existingPost.message = updatedPost.message;
		existingPost.audience = updatedPost.audience;
		existingPost.priority = updatedPost.priority;
		existingPost.startDate = updatedPost.startDate;
		existingPost.endDate = updatedPost.endDate;
		existingPost.department = updatedPost.department;
		existingPost.postedBy = updatedPost.postedBy;
		existingPost.attachmentPath = updatedPost.attachmentPath;

		return postRepository.save(existingPost);
	}

	@Override
	public void deletePost(Long postId) throws Exception {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new UserNotFoundException("Post not found with ID: " + postId));

		postRepository.delete(post);
	}

	@Override
	public List<Post> getAllPostsByAgency(String email) throws Exception {
		Agency agency = agencyRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException("Agency not found with email: " + email));

		return postRepository.findByAgencyEmail(agency.getEmail());

	}

}
