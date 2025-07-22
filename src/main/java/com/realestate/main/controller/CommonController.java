package com.realestate.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.realestate.main.exceptions.UserNotFoundException;
import com.realestate.main.service.CommonService;

import jakarta.mail.MessagingException;

@RestController
public class CommonController {
	
	@Autowired
	private CommonService commonService;
	
	@PostMapping("/sendEmailOTP")
	public ResponseEntity<String> sendOtp(@RequestParam String email) throws UserNotFoundException, MessagingException {
		String sendOtp = commonService.sendOtp(email);
		return new ResponseEntity<String>(sendOtp, HttpStatus.OK);
	}
	
	@PostMapping("/verifyOTP")
	public ResponseEntity<Boolean> verifyOTP(@RequestParam String email,@RequestParam int otp) throws UserNotFoundException {
		boolean verifyOTP = commonService.verifyOTP(email, otp);
		return new ResponseEntity<Boolean>(verifyOTP, HttpStatus.OK);
	}
	
	@PutMapping("/resetPassword")
	public ResponseEntity<String> resetPassword(@RequestParam String email,@RequestParam int otp,@RequestParam String newPassword) throws UserNotFoundException{
		String resetPassword = commonService.resetPassword(otp, email, newPassword);
		return new ResponseEntity<String>(resetPassword, HttpStatus.OK);
	}
}
