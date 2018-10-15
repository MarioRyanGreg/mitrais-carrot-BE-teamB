package com.mitrais.carrot.dummy;

import com.mitrais.carrot.payload.SignUpRequest;

public class RegisterDummy {

	/**
	 * dummy register object
	 *
	 * @return SignUpRequest object dummy
	 */
	public static SignUpRequest dummyRegisterUser() {
		SignUpRequest singupRequest = new SignUpRequest();
		singupRequest.setName("chicken black");
		singupRequest.setUserName("chicken");
		singupRequest.setEmail("chicken@kremes.com");
		singupRequest.setPassword("everydayisholiday");
		return singupRequest;
	}
}
