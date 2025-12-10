package com.realestate.main.service;

import com.realestate.main.exceptions.UserNotFoundException;

import jakarta.mail.MessagingException;

public interface CommonService {
	
	String sendOtp(String email) throws UserNotFoundException, MessagingException;
	
	boolean verifyOTP(String email, int otp) throws UserNotFoundException;
	
	String resetPassword(int otp,String email,String newPassword) throws UserNotFoundException;
	
	
}
