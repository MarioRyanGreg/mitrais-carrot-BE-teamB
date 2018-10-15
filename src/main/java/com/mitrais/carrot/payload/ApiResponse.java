package com.mitrais.carrot.payload;

import lombok.Data;

/**
 * Custom Api response with message
 * 
 * @author Febri_MW251
 *
 */
@Data
public class ApiResponse {

	/**
	 * success status
	 */
    private Boolean success;

    /**
     * message api response
     */
    private String message;

    /**
     * constructor of ApiResponse
     * 
     * @param success success status
     * @param message message api response
     */
    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

}
