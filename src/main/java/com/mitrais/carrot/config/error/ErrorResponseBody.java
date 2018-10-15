package com.mitrais.carrot.config.error;

import java.util.Date;

import lombok.Data;

/**
 * error custom response body
 * 
 * @author Febri
 */
@Data
public class ErrorResponseBody {

	/**
	 * timestamp variable as Date type
	 */
	private Boolean success = false;

	/**
	 * timestamp variable as Date type
	 */
	private Date timestamp;

	/**
	 * String message to descripe the error
	 */
	private String message;

	/**
	 * String details of errors
	 */
	private String details;

	/**
	 * ErrorResponseBody constructor
	 * 
	 * @param timestamp the date that error happen
	 * @param message the message to describe the error
	 * @param details the details of errors
	 */
	public ErrorResponseBody(Date timestamp, String message, String details) {
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

}
