package com.mitrais.carrot.payload;

import lombok.Data;

/**
 * Response builder of user identity availability
 * 
 * @author Febri_MW251
 *
 */
@Data
public class UserIdentityAvailability {

	/**
	 * available status of user
	 */
    private Boolean available;

    /**
     * available status of user
     * @param available available status of user
     */
    public UserIdentityAvailability(Boolean available) {
        this.available = available;
    }

}
