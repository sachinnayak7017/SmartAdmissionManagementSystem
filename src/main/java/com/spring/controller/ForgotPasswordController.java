package com.spring.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.dao.UserRepository;
import com.spring.entity.User;
import com.spring.service.ForgotPasswordService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ForgotPasswordController {

	Random random = new Random(1000);
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ForgotPasswordService forgotPasswordService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@RequestMapping("/forgot_password")
	public String openForgotPassword()
	{
		return "forgot_password_form";
	}
	
	@PostMapping("/sendOTP")
	public String sendOTP(@RequestParam("email") String email, HttpSession session)
	{
		System.out.println("EMAIL "+email);
		
		
		
		int otp = random.nextInt(9999);
		
		System.out.println("OTP "+otp);
		
		String subject = "OTP sent from Smart Admission Management System";
		String message = ""
				+ "<div style='border:1px solid #e2e2e2; padding:20px'>"
				+ "<h2>"
				+ "Your OTP is "
				+ "<b>"+otp
				+ "</n>"
				+ "</h2>"
				+ "</div>";
		String to = email;
		
		boolean flag = this.forgotPasswordService.sendEmail(subject, message, to);
		
		if(flag)
		{
			session.setAttribute("myotp", otp);
			session.setAttribute("email", email);
			
			return "verify_otp";
		}else {
			return "forgot_password_form";
		}
	
	}
	
	@PostMapping("/verifyOTP")
	public String verifyOTP(@RequestParam("otp") int otp, HttpSession session)
	{
		
		int myotp = (int)session.getAttribute("myotp");
		String email = (String)session.getAttribute("email");
		
		if(myotp == otp)
		{
			
			User user = this.userRepo.findByEmail(email);
			
			if(user == null)
			{
				// error msg	
				
				return "forgot_password_form";
			}
			else
			{
			
			    return "change_password";
			}
			
		}
		else
		{
			
			// error msg
			
			return "verify_otp";
		}
		
	}
	
	
	// change password after otp verification

	
		@PostMapping("/changePassword")
		public String changePassword(@RequestParam("newPassword") String newPassword, HttpSession session) {
			
			String email = (String) session.getAttribute("email");
			
			User user = this.userRepo.findByEmail(email);
			
			user.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
			
			this.userRepo.save(user);

			session.setAttribute("msg", "Your password have been changed successfully ! You can login now !");

			return "change_password";

			/*
			 * return
			 * "redirect:/signin?change=password changed successfully ! You can login now !"
			 * ;
			 */

		}
}
