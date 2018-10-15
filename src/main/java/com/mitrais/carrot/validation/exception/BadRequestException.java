package com.mitrais.carrot.validation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom RuntimeException class
 * 
 * @author Febri_MW251
 *
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

	/**
	 * adding static serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * set error message
	 * @param message error message
	 */
    public BadRequestException(String message) {
        super(message);
    }

    /**
     * set error message
     * @param message error message
     * @param cause Throwable
     */
    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
