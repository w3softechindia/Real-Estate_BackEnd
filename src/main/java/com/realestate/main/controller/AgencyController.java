package com.realestate.main.controller;

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

import com.realestate.main.dto.AgentDto;
import com.realestate.main.entity.Agent;
import com.realestate.main.entity.Post;
import com.realestate.main.exceptions.UserNotFoundException;
import com.realestate.main.service.AgencyService;

@RestController
public class AgencyController {

	@Autowired
	private AgencyService agencyService;

	@PreAuthorize("hasRole('Agency')")
	@PostMapping("/addAgent")
	public ResponseEntity<AgentDto> addAgent(@RequestParam String email, @RequestBody Agent agent) throws Exception {
		AgentDto agent2 = agencyService.addAgent(email, agent);
		return new ResponseEntity<AgentDto>(agent2, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('Agency')")
	@PutMapping("/updateAgent")
	public ResponseEntity<AgentDto> updateAgent(@RequestParam String email, @RequestBody Agent agent)
			throws UserNotFoundException {
		AgentDto updatedAgent = agencyService.updateAgent(email, agent);
		return new ResponseEntity<>(updatedAgent, HttpStatus.OK);
	}

	@PreAuthorize("hasAnyRole('Agency','Agent')")
	@GetMapping("/getAgent")
	public ResponseEntity<AgentDto> getAgent(@RequestParam String email) throws UserNotFoundException {
		AgentDto agent = agencyService.getAgent(email);
		return new ResponseEntity<AgentDto>(agent, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('Agency')")
	@DeleteMapping("/deleteAgent")
	public ResponseEntity<String> deleteAgent(@RequestParam String email) throws UserNotFoundException {
		String message = agencyService.deleteAgent(email);
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('Agency')")
	@PostMapping("/addPost")
	public ResponseEntity<Post> addPost(@RequestParam String email, @RequestBody Post post) throws Exception {
		Post savedPost = agencyService.addPost(email, post);
		return new ResponseEntity<>(savedPost, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('Agency')")
	@PutMapping("/updatePost/{postId}")
	public ResponseEntity<Post> updatePost(@PathVariable Long postId, @RequestBody Post updatedPost) throws Exception {
		Post post = agencyService.updatePost(postId, updatedPost);
		return new ResponseEntity<>(post, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('Agency')")
	@DeleteMapping("/deletePost/{postId}")
	public ResponseEntity<String> deletePost(@PathVariable Long postId) throws Exception {
		agencyService.deletePost(postId);
		return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
	}

	
	@PreAuthorize("hasRole('Agency')")
	@GetMapping("/getAllPosts")
	public ResponseEntity<List<Post>> getAllPosts(@RequestParam String email) throws Exception {
	    List<Post> posts = agencyService.getAllPostsByAgency(email);
	    return new ResponseEntity<>(posts, HttpStatus.OK);
	}
	
	
}
