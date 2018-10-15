package com.mitrais.carrot.payload;

import lombok.Data;

/**
 * POJO of summary user
 * 
 * @author Febri_MW251
 *
 */
@Data
public class UserSummary {

	/**
	 * id user
	 */
    private Long id;

    /**
     * username of user
     */
    private String username;

    /**
     * name of user
     */
    private String name;

    /**
     * constructor of UserSummary
     * 
     * @param id id user
     * @param username username of user
     * @param name name of user
     */
    public UserSummary(Long id, String username, String name) {
        this.id = id;
        this.username = username;
        this.name = name;
    }

}
