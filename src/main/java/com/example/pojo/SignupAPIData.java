package com.example.pojo;




import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data

public class SignupAPIData {
	
	
	@NotNull(message = "Name Required")
	@Size(min = 2,message = "min 2 char")
	private String name;
	
	@NotNull(message = "email Reqired")
	@Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email is invalid")
	private String email;
	
	@NotNull(message = "password Required")
	@Size(min = 8, message = "min 8 char")
	private String password;
	
	@NotNull(message = "mpobile Required")
	@Size(min = 10, message = "Min 10 Char")
	private String mobile;


	
	

}
