package com.mitrais.carrot.payload;

import javax.validation.constraints.*;

/**
 * request body to register user into system
 * 
 * @author Febri_MW251
 *
 */
public class SignUpRequest {

	/**
	 * name of user
	 */
	@NotBlank
	@Size(min = 4, max = 75)
	private String name;

	/**
	 * username of user
	 */
	@NotBlank
	@Size(min = 3, max = 15)
	private String userName;

	/**
	 * email user
	 */
	@NotBlank
	@Size(min = 3)
	@Email
	private String email;

	/**
	 * password user
	 */
	@NotBlank
	@Size(min = 3)
	private String password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
