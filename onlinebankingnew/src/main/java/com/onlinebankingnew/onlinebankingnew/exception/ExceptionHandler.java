package com.onlinebankingnew.onlinebankingnew.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.onlinebankingnew.onlinebankingnew.entity.ErrorMessage;

@ControllerAdvice
@ResponseStatus
public class ExceptionHandler  extends ResponseEntityExceptionHandler{

	@org.springframework.web.bind.annotation.ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorMessage> customerNotFoundException(ResourceNotFoundException exception, WebRequest request){
		ErrorMessage msg = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(msg);
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(InsufficientFundsException.class)
	public ResponseEntity<ErrorMessage> insufficientFundsException(InsufficientFundsException exception, WebRequest request){
		ErrorMessage msg = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(msg);
	}
	
	@org.springframework.web.bind.annotation.ExceptionHandler(NegativeValueException.class)
	public ResponseEntity<ErrorMessage> negativeValue(NegativeValueException exception, WebRequest request){
		ErrorMessage msg = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(msg);
	}
	
	
//	@org.springframework.web.bind.annotation.ExceptionHandler(CustomerNotFoundException.class)
//	public ResponseEntity<ErrorMessage> customerException(CustomerNotFoundException exception, WebRequest request){
//		ErrorMessage msg = new ErrorMessage(HttpStatus.BAD_REQUEST, exception.getMessage());
//		
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
//	}
	
	
	
	

}
