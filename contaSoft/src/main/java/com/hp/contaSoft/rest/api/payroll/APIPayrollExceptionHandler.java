package com.hp.contaSoft.rest.api.payroll;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.hp.contaSoft.rest.api.payroll.error.ClientErrorMessage;
import com.hp.contaSoft.rest.api.payroll.exception.ClientNotFoundException;

@ControllerAdvice
public class APIPayrollExceptionHandler {

	//add exception handler
	
	@ExceptionHandler
	public ResponseEntity<ClientErrorMessage> handleException( Exception exc ){
		
		//create Client response
		ClientErrorMessage error = new ClientErrorMessage();
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		exc.printStackTrace();
		//return response entity
		return new ResponseEntity<>( error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<ClientErrorMessage> handleException( ClientNotFoundException exc ){
		
		//create Client response
		ClientErrorMessage error = new ClientErrorMessage();
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		
		//return response entity
		return new ResponseEntity<>( error, HttpStatus.NOT_FOUND);
	}

	
}
