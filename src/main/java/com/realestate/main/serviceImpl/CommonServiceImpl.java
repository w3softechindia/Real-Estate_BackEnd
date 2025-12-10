package com.realestate.main.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.realestate.main.emailConfiguration.EmailUtil;
import com.realestate.main.emailConfiguration.OtpUtil;
import com.realestate.main.entity.RealEStateUser;
import com.realestate.main.exceptions.UserNotFoundException;
import com.realestate.main.repository.RealEstateUserRepo;
import com.realestate.main.service.CommonService;

import jakarta.mail.MessagingException;

@Service
public class CommonServiceImpl implements CommonService{
	
	@Autowired
	private OtpUtil otpUtil;
	
	@Autowired
	private EmailUtil emailUtil;
	
	@Autowired
	private RealEstateUserRepo estateUserRepo;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

	@Override
	public String sendOtp(String email) throws UserNotFoundException, MessagingException {
		// TODO Auto-generated method stub
		RealEStateUser byEmail = estateUserRepo.findByEmail(email);
		if(byEmail==null) {
			throw new UserNotFoundException("Email doesn't exists..!!");
		}
		String otp = otpUtil.generateOtp();
		byEmail.setOtp(Integer.valueOf(otp));
		estateUserRepo.save(byEmail);
		emailUtil.sendOtpToEmail(email, otp);
		return "Otp Sent to registered email..!!";
	}

	@Override
	public String resetPassword(int otp, String email, String newPassword) throws UserNotFoundException {
		// TODO Auto-generated method stub
		boolean result = verifyOTP(email, otp);
		RealEStateUser byEmail = estateUserRepo.findByEmail(email);
		if(result) {
			String password = bCryptPasswordEncoder.encode(newPassword);
			byEmail.setPassword(password);
			estateUserRepo.save(byEmail);
			return "Password reset successfull..!!";
		}else {
			return "Invalid OTP";
		}
	}

	@Override
	public boolean verifyOTP(String email, int otp) throws UserNotFoundException {
		RealEStateUser byEmail = estateUserRepo.findByEmail(email);
		if(byEmail==null) {
			throw new UserNotFoundException("Email doesn't exists..!!");
		}
		if(otp==byEmail.getOtp()) {
			return true;
		}else {
			return false;
		}
	}

	

}
