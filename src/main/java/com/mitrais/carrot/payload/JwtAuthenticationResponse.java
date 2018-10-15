package com.mitrais.carrot.payload;

import lombok.Data;

/**
 * custom response of JwtAuthenticationResponse
 * 
 * @author Febri_MW251
 *
 */
@Data
public class JwtAuthenticationResponse {

	/**
	 * access token value
	 */
    private String accessToken;

    /**
     * token type
     */
    private String tokenType = "Bearer";

    /**
     * JwtAuthenticationResponse constructor
     * 
     * @param accessToken string token that generated from JWT helper class
     */
    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }

}
