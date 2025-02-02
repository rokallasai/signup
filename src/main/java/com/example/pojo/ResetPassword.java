package com.example.pojo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.PackagePrivate;

@Data
public class ResetPassword {

	@NotNull(message = "Password rest key is required")
	private String linkId;
	
	@NotNull(message = "Password Required")
	@Size(min = 8, message = "Password min 8 characters")
	private String password;
	
	@NotNull(message = "Confirm password required ")
	@Size(min = 8, message = "Password min 8 characters")
	private String confirmPassword;
}
