package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.User;
import com.example.pojo.ForgotPasswordAPIData;
import com.example.pojo.LoginAPIData;
import com.example.pojo.ResetPassword;
import com.example.pojo.SignupAPIData;
import com.example.service.AuthService;
import com.example.service.EmailService;

import jakarta.validation.Valid;

@RestController
public class SignupController {
	
	/*
	 * 1.api path
	 * 2.receive data name,email,password,mobile
	 * 3.validate input data and throw exception
	 * 4.check if user exist with email -> yes throw an error
	 * 5.create new user
	 */
	
	
	
	@Autowired
	public AuthService authService;
	
	@Autowired
	public EmailService emailService;

	
	@PostMapping("/create-account")
	public Object createAccount(@Valid @RequestBody SignupAPIData signupAPIData) throws Exception {
		
		Object userDataObject = authService.handleCreateAccount(signupAPIData);
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("result", "success");
		responseMap.put("data", userDataObject);
		
		return ResponseEntity.status(HttpStatus.OK).body(responseMap);
		
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginAPIData loginAPIData) throws Exception {
		
		User userData = authService.handleLogin(loginAPIData);
		
		Map<String , Object> responseMap = new HashMap<String, Object>();
		responseMap.put("result", "Success");
		responseMap.put("data", userData);
		
		return ResponseEntity.status(HttpStatus.OK).body(responseMap);		
				
	}
	
	@GetMapping("/send-email")
	public ResponseEntity<?> sendEmail() throws Exception{
		
		String fromEmail ="rsdpk@gmail.com";
		String toEmail = "rsdpk01@gmial.com";
		String subject = "Test Email";
		String mailBody = "Hello, This is Test Email";
		
	//	emailService.sendPlainEmail(fromEmail, toEmail, subject, mailBody);
		
		emailService.sendTemplateEmail(fromEmail, toEmail, subject, "test-html");
		
		Map<String , String> responseMap = new HashMap<String, String>();
		responseMap.put("result", "Success");
		responseMap.put("message", "Email Sent");
		
		return ResponseEntity.status(HttpStatus.OK).body(responseMap);
	}
	
	/*
	 * Forgot password api
	 1. create path
	 2. receive data and validate - > email
	 3. check with db -> if roe exist -> send email else throw not register error message 
	 
	 generate key -> store in db -> send in the link -> receive from UI -> check row based on the key
	 */
	
	@PostMapping("/forgot-email")
	public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordAPIData forgotPasswordAPIData) throws Exception{
		
		authService.handleForgotPassword(forgotPasswordAPIData);
		
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("result", "success");
		responseMap.put("message", "We initiated an email with a link to reset your password. Please check you spam");
		
		return ResponseEntity.status(HttpStatus.OK).body(responseMap);
	}
	
	
	/*
	 * Create a path
	 * receive and validate data -> link id , password, confirm password 
	 * check if password and confirm password matching -> yes proceed else throw an error
	 * get user based on link id - > if exist update password else invalid reset key or expire
	 * 
	 */
	
	@PostMapping("/reset-password")
	public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPassword resetPassword) throws Exception{
		
		authService.handleRestPassword(resetPassword);
		
		Map<String, String> responseMap = new HashMap<String, String>();
		responseMap.put("result", "success");
		responseMap.put("message", "You Password Successfully Updated");
		
		return ResponseEntity.status(HttpStatus.OK).body(responseMap);
	}
}
