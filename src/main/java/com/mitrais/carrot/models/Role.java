package com.mitrais.carrot.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * role model representative of role table on database
 *
 * @author Febri
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "role", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "role_name"
        })
})

@JsonIgnoreProperties(
        value = {"createdTime", "lastModifiedTime"},
        allowGetters = true
)
public class Role extends ModelAudit {

	/**
	 * Generated Serial Version of Bazaar Class
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Primary key that has unique value for each user
	 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
	 * the name of role this entity is required
	 * with length 60
	 */
    @NotNull
    @NotBlank
    @Column(name = "role_name", length = 60)
    private String roleName;

    /**
     * Default constructor
     */
	public Role() {}

	/**
	 * Constructor with dependency injection
	 * @param id id of role
	 * @param roleName name of role
	 */
	public Role(Integer id, String roleName) {
		this.id = id;
		this.roleName = roleName;
	}
}
