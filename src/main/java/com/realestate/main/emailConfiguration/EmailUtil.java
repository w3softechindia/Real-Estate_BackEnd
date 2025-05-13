package com.realestate.main.emailConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtil {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendAgencyRegistration(String email, String password)
	        throws Exception {
	    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	    MimeMessageHelper mn = new MimeMessageHelper(mimeMessage, true, "UTF-8");
	    mn.setFrom("sricharan824@gmail.com", "RealEstate Team");
	    mn.setTo(email);
	    mn.setSubject("Your Agency Account Credentials - RealEstate");

	    String body = String.format(
	    	    "<html>" +
	    	    "<head>" +
	    	    "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
	    	    "  <style>" +
	    	    "    body { font-family: Arial, sans-serif; margin: 20px; padding: 0; color: #333333; }" +
	    	    "    h2 { color: #0056b3; }" +
	    	    "    ul { list-style-type: none; padding: 0; }" +
	    	    "    li { padding: 8px 0; }" +
	    	    "    .footer { margin-top: 30px; font-size: 0.9em; color: #777777; }" +
	    	    "  </style>" +
	    	    "</head>" +
	    	    "<body>" +
	    	    "  <h2>Welcome to RealEstate!</h2>" +
	    	    "  <p>Your agency account has been created successfully. Below are your login credentials:</p>" +
	    	    "  <ul>" +
	    	    "    <li><strong>Email:</strong> %s</li>" +
	    	    "    <li><strong>Password:</strong> %s</li>" +
	    	    "  </ul>" +
	    	    "  <p>Please keep this information confidential and do not share it with anyone.</p>" +
	    	    "  <p>If you have any questions or need help, please contact our support team.</p>" +
	    	    "  <div class=\"footer\">" +
	    	    "    <p>Best regards,</p>" +
	    	    "    <p>RealEstate ERP Team</p>" +
	    	    "  </div>" +
	    	    "</body>" +
	    	    "</html>",
	    	    email,
	    	    password
	    	);

	    mn.setText(body, true);
	    javaMailSender.send(mimeMessage);
	}

	public void sendAgentRegistration(String email, String password,String agencyName)
	        throws Exception {
	    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	    MimeMessageHelper mn = new MimeMessageHelper(mimeMessage, true, "UTF-8");
	    mn.setFrom("sricharan824@gmail.com", agencyName+" Team");
	    mn.setTo(email);
	    mn.setSubject("Agent Account Credentials -"+agencyName);

	    String body = String.format(
	    	    "<html>" +
	    	    "<head>" +
	    	    "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
	    	    "  <style>" +
	    	    "    body { font-family: Arial, sans-serif; margin: 20px; padding: 0; color: #333333; }" +
	    	    "    h2 { color: #0056b3; }" +
	    	    "    ul { list-style-type: none; padding: 0; }" +
	    	    "    li { padding: 8px 0; }" +
	    	    "    .footer { margin-top: 30px; font-size: 0.9em; color: #777777; }" +
	    	    "  </style>" +
	    	    "</head>" +
	    	    "<body>" +
	    	    "  <h2>Welcome to %s </h2>" +
	    	    "  <p>Your agent account has been created successfully. Below are your login credentials:</p>" +
	    	    "  <ul>" +
	    	    "    <li><strong>Email:</strong> %s</li>" +
	    	    "    <li><strong>Password:</strong> %s</li>" +
	    	    "  </ul>" +
	    	    "  <p>Please keep this information confidential and do not share it with anyone.</p>" +
	    	    "  <p>If you have any questions or need help, please contact our support team.</p>" +
	    	    "  <div class=\"footer\">" +
	    	    "    <p>Best regards,</p>" +
	    	    "    <p>RealEstate ERP Team</p>" +
	    	    "  </div>" +
	    	    "</body>" +
	    	    "</html>",
	    	    agencyName,
	    	    email,
	    	    password
	    	);

	    mn.setText(body, true);
	    javaMailSender.send(mimeMessage);
	}


}
