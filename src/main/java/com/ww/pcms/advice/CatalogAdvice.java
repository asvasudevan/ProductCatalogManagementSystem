package com.ww.pcms.advice;

import com.ww.pcms.Exception.EmptyInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice(basePackages = "com.ww.pcms.controller")
public class CatalogAdvice {
	
	@ExceptionHandler(EmptyInputException.class)
	public ResponseEntity<String> handleEmptyInput(EmptyInputException emptyInputException){
		return new ResponseEntity<String> ("Input filed is empty", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException noSuchElementException){
		return new ResponseEntity<String> ("No such element pls try some other value", HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception exception){
		return new ResponseEntity<String> ("Server error occurred:", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
