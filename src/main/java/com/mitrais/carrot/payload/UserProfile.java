package com.mitrais.carrot.payload;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * Pojo if UserProfile
 * 
 * @author Febri_MW251
 *
 */
@Data
public class UserProfile {

	/**
	 * id of user
	 */
    private Long id;

    /**
     * username of user
     */
    private String username;

    /**
     * email of user
     */
    private String email;

    /**
     * name of user
     */
    private String name;

    /**
     * joined date of user
     */
    private LocalDateTime joinedAt;

    /**
     * constructor of UserProfile class
     * 
     * @param id id of user
     * @param username username of user
     * @param email email of user
     * @param name name of user
     * @param joinedAt joined date of user
     */
    public UserProfile(Long id, String username, String email, String name, LocalDateTime joinedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = name;
        this.joinedAt = joinedAt;
    }

}
