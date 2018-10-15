package com.mitrais.carrot.config.error;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * handling global error response
 */
@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * logger
     */
    private static final Logger logMe = LoggerFactory.getLogger(CustomizedResponseEntityExceptionHandler.class);

    /**
     * variable today to set date of today
     */
    private Date today = new Date();

    /**
     * catching and handling all exceptions with Exception
     * also logging the error into logger factory
     * 
     * @param ex Exception dependency injection
     * @param request WebRequest dependency injection
     * @return object ResponseEntity to set custom response
     */
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		ErrorResponseBody response = new ErrorResponseBody(this.today, ex.getMessage(), request.getDescription(true));
		logMe.error("Error in handleAllExceptions . Message - {} - Details - {} ", ex.getMessage(), request.getDescription(true));
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

    /**
     * catching and handling all exceptions with MethodArgumentNotValidException
     * also logging the error into logger factory
     * 
     * @param ex MethodArgumentNotValidException dependency injection
     * @param headers HttpHeaders dependency injection
     * @param status HttpStatus dependency injection
     * @param request WebRequest dependency injection
     * @return object ResponseEntity to set custom response
     */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorResponseBody response = new ErrorResponseBody(this.today, "Form validation failed " + request.getDescription(true), ex.getBindingResult().toString());
		logMe.error("Error in handleMethodArgumentNotValid method : Message - {}", ex.getBindingResult().toString());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
}
