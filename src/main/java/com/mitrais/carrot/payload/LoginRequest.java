package com.mitrais.carrot.payload;

import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * login request body to check user
 * 
 * @author Febri_MW251
 *
 */
@Data
public class LoginRequest {

	/**
	 * usernameOrEmail username or email value of user
	 */
	@NotBlank
	private String usernameOrEmail;

	/**
	 * password user
	 */
	@NotBlank
	private String password;

	/**
	 * default constructor
	 */
	public LoginRequest() {}

	/**
	 * constructor setter
         * 
         * @param usernameOrEmail username or email of user
         * @param password password of user
	 */
	public LoginRequest(String usernameOrEmail, String password) {
		this.usernameOrEmail = usernameOrEmail;
		this.password = password;
	}

}
