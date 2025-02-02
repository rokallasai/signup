package com.example.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptions {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Object handleAPIInputDateValidationException(MethodArgumentNotValidException ex) {
		
		Map<String, String> errors = new HashMap<String, String>();
		
		ex.getBindingResult().getFieldErrors().forEach(error ->{
			
			errors.put(error.getField(), error.getDefaultMessage());
		});
		
		Map<String , Object> responseMap = new HashMap<String, Object>();
		
		responseMap.put("result", "failed");
		responseMap.put("errors", errors);
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
	}

	
	@ExceptionHandler(Exception.class)
	public Object handleGenericException(Exception ex) {
		Map<String, String> responseMap = new HashMap<String, String>();

		responseMap.put("result", "failed");
		responseMap.put("message", ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMap);
		
	}
}
