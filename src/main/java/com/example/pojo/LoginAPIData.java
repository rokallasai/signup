package com.example.pojo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginAPIData {

	@NotNull(message = "Email Reqired")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email is invalid")
	private String email;
	
	@NotNull(message = "Password is Required")
	@Size(min = 8, message = "Password Should be 8 Char")
	private String Password;
}
