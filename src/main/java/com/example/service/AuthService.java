package com.example.service;

import java.security.PublicKey;
import java.util.Optional;
import java.util.UUID;

import org.hibernate.dialect.identity.DB2390IdentityColumnSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.entity.User;
import com.example.pojo.ForgotPasswordAPIData;
import com.example.pojo.LoginAPIData;
import com.example.pojo.ResetPassword;
import com.example.pojo.SignupAPIData;
import com.example.repository.userRepository;

import jakarta.validation.Valid;

@Service
public class AuthService {
	
	
	@Autowired
	public userRepository userRepository;
	
	@Autowired
	public EmailService emailService;
	
	public PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public User handleCreateAccount(SignupAPIData signupAPIData) throws Exception {
		
		Optional<User> bdData = userRepository.findByEmail(signupAPIData.getEmail());	
		
		if(bdData.isEmpty()) {
			User userObj = new User();
			
			userObj.setName(signupAPIData.getName());
			userObj.setEmail(signupAPIData.getEmail());
			userObj.setPassword(passwordEncoder.encode(signupAPIData.getPassword()));
			userObj.setMobile(signupAPIData.getMobile());
			
			User dbUserData = userRepository.save(userObj);
			
			return dbUserData;

		}else {
			throw new Exception("User already Exist. Please Login");
		}
		
	}
	
	public User handleLogin(LoginAPIData loginAPIData) throws Exception {
		
		
	     Optional<User> dbData = userRepository.findByEmail(loginAPIData.getEmail());
	     
	     if(dbData.isEmpty()) {
	    	 throw new Exception("Email is nor Registed with us");
	     }else {
	    	 User userData = dbData.get();
	    	 
	    	 Boolean isMatching = passwordEncoder.matches(loginAPIData.getPassword(), userData.getPassword());
	    	 
	    	 if (isMatching == true) {
	    		 return userData;
				
			}else {
				throw new Exception("password is not matching");
			}
	     }
		
	    
	}
	
	
	/*
	 * Forgot password api
	 1. create path
	 2. receive data and validate - > email
	 3. check with db -> if roe exist -> send email else throw not register error message 
	 
	 generate key -> store in db -> send in the link -> receive from UI -> check row based on the key
	 */
	
	public void handleForgotPassword(ForgotPasswordAPIData forgotPasswordAPIData) throws Exception {
		
		Optional<User> dbData = userRepository.findByEmail(forgotPasswordAPIData.getEmail());
		
		if (dbData.isEmpty()) {
			
			throw new Exception("Email Id is Not register with us. please check you email");
			
		}else {
			String passwordResetKey = UUID.randomUUID().toString();
			User userData = dbData.get();
			userData.setPasswordResetKey(passwordResetKey);
			userRepository.save(userData);
			
			String mailBody ="Hi" + userData.getName() + "," +
					"please find the below link to reset your password.<br/>" +
					"Password reset link : <a href= 'http://localhost:8080/password-reset-ui?linkid =" + passwordResetKey + " '> click here</a><br/>" +
					"<b> Regards <br/> Spring Boot</b> ";
					
			emailService.sendHtmlEmail("rsdpk98@gmail.com", userData.getEmail(), "Password rest Key...", mailBody);
		}
		
	}
	
	/*
	 * Create a path
	 * receive and validate data -> link id , password, confirm password 
	 * check if password and confirm password matching -> yes proceed else throw an error
	 * get user based on link id - > if exist update password else invalid reset key or expire
	 * 
	 */
	
	
	public void handleRestPassword(ResetPassword resetPassword) throws Exception {
		
		if(resetPassword.getPassword().equals(resetPassword.getConfirmPassword()) == false) {
			throw new Exception("Both Passwords Should be same");
		}
		
		Optional<User> dbData = userRepository.findByPasswordResetKey(resetPassword.getLinkId());
		
		if(dbData.isEmpty()) {
			throw new Exception("Invalid Password reset Key or Expired");
			
		}
		User userData = dbData.get();
		userData.setPassword(passwordEncoder.encode(resetPassword.getPassword()));
		userData.setPasswordResetKey("");	
		
		
		userRepository.save(userData);
	
	}
}
