package com.example.entity;


import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Component
@Table(name = "users")
@Data
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer id;
	
	private String name;
	
	private String email;
	
	private String password;
	
	private String mobile;
	
	private LocalDateTime createdOn = LocalDateTime.now();

	private Boolean isActive = true;
	
	private Boolean isEmailVerified = false;
	 
	private String passwordResetKey;
}
