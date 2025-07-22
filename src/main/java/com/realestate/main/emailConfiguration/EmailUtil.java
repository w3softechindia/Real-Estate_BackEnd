package com.realestate.main.emailConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtil {

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendAgencyRegistration(String email, String password) throws Exception {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mn = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		mn.setFrom("sricharan824@gmail.com", "RealEstate Team");
		mn.setTo(email);
		mn.setSubject("Your Agency Account Credentials - RealEstate");

		String body = String.format("<html>" + "<head>"
				+ "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" + "  <style>"
				+ "    body { font-family: Arial, sans-serif; margin: 20px; padding: 0; color: #333333; }"
				+ "    h2 { color: #0056b3; }" + "    ul { list-style-type: none; padding: 0; }"
				+ "    li { padding: 8px 0; }" + "    .footer { margin-top: 30px; font-size: 0.9em; color: #777777; }"
				+ "  </style>" + "</head>" + "<body>" + "  <h2>Welcome to RealEstate!</h2>"
				+ "  <p>Your agency account has been created successfully. Below are your login credentials:</p>"
				+ "  <ul>" + "    <li><strong>Email:</strong> %s</li>" + "    <li><strong>Password:</strong> %s</li>"
				+ "  </ul>" + "  <p>Please keep this information confidential and do not share it with anyone.</p>"
				+ "  <p>If you have any questions or need help, please contact our support team.</p>"
				+ "  <div class=\"footer\">" + "    <p>Best regards,</p>" + "    <p>RealEstate ERP Team</p>"
				+ "  </div>" + "</body>" + "</html>", email, password);

		mn.setText(body, true);
		javaMailSender.send(mimeMessage);
	}

	public void sendAgentRegistration(String email, String password, String agencyName) throws Exception {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mn = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		mn.setFrom("sricharan824@gmail.com", agencyName + " Team");
		mn.setTo(email);
		mn.setSubject("Agent Account Credentials -" + agencyName);

		String body = String.format("<html>" + "<head>"
				+ "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" + "  <style>"
				+ "    body { font-family: Arial, sans-serif; margin: 20px; padding: 0; color: #333333; }"
				+ "    h2 { color: #0056b3; }" + "    ul { list-style-type: none; padding: 0; }"
				+ "    li { padding: 8px 0; }" + "    .footer { margin-top: 30px; font-size: 0.9em; color: #777777; }"
				+ "  </style>" + "</head>" + "<body>" + "  <h2>Welcome to %s </h2>"
				+ "  <p>Your agent account has been created successfully. Below are your login credentials:</p>"
				+ "  <ul>" + "    <li><strong>Email:</strong> %s</li>" + "    <li><strong>Password:</strong> %s</li>"
				+ "  </ul>" + "  <p>Please keep this information confidential and do not share it with anyone.</p>"
				+ "  <p>If you have any questions or need help, please contact our support team.</p>"
				+ "  <div class=\"footer\">" + "    <p>Best regards,</p>" + "    <p>RealEstate ERP Team</p>"
				+ "  </div>" + "</body>" + "</html>", agencyName, email, password);

		mn.setText(body, true);
		javaMailSender.send(mimeMessage);
	}

	public void sendOtpToEmail(String email, String otp) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mn = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		mn.setFrom("sricharan824@gmail.com");
		mn.setTo(email);
		mn.setSubject("Email Verification OTP");
		String emailBody = String.format(
				"<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; color: #333; text-align: center; padding: 20px;\">\r\n"
						+ "\r\n"
						+ "    <div style=\"max-width: 600px; margin: auto; background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\">\r\n"
						+ "        <h2 style=\"color: #ff5722;\">RealEState OTP Verification</h2>\r\n"
						+ "        <h2>Dear User"+",</h2>\r\n" + "        <p>Your OTP for Changing Password is:</p>\r\n"
						+ "        \r\n"
						+ "        <div style=\"font-size: 36px; color: #ff5722; margin: 20px 0; padding: 10px; border: 2px solid #ff5722; border-radius: 5px;\">\r\n"
						+ "            <strong>" + otp + "</strong>\r\n" + "        </div>\r\n" + "\r\n"
						+ "        <p>This OTP is valid for a single use and will expire in 2 minutes.</p>\r\n"
						+ "        <p>For security reasons, do not share this OTP with anyone. If you didn't request this OTP, please ignore this message.</p>\r\n"
						+ "\r\n" + "       \r\n"
						+ "    </div>\r\n" + "\r\n" + "</body>",
				otp);
		mn.setText(emailBody, true);
		javaMailSender.send(mimeMessage);
	}
}
