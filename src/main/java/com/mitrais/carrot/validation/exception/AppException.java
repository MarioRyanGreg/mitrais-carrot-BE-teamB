package com.mitrais.carrot.validation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom Exception class
 *  
 * @author Febri_MW251
 *
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AppException extends RuntimeException {

	/**
	 * adding static serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * set message of error
	 * 
	 * @param message message error
	 */
    public AppException(String message) {
        super(message);
    }

    /**
     * set message of error
     * 
     * @param message message error
     * @param cause Throwable
     */
    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}
