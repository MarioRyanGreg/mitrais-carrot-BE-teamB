package com.mitrais.carrot.models;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Email;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.data.annotation.Transient;

/**
 * user model that representative of user table on database
 *
 * @author Febri
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "user", uniqueConstraints = {
    @UniqueConstraint(columnNames = {
        "user_name"
    })
    ,
        @UniqueConstraint(columnNames = {
        "email"
    })
})
public class User extends ModelAudit {

    /**
     * default serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Primary key that has unique value for each user
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * name of user
     */
    @Column(name = "name")
    private String name;

    /**
     * username that will be use when user signin into system
     * <P>
     * this entity is required
     */
    @Column(name = "user_name")
    @NotBlank(message = "*Please provide your username")
    private String userName;

    /**
     * email that will be use when user signin into system
     * <P>
     * should be Valid Email pattern
     * <P>
     * this is required
     *
     */
    @Column(name = "email")
    @Email
    @NotBlank(message = "*Please provide an email")
    private String email;

    /**
     * password that will be use when user signin into system
     * <P>
     * this entity is required
     */
    @Column(name = "password")
    @NotBlank(message = "*Please provide your password")
    @Transient
    private String password;

    /**
     * status of user is it active or not
     */
    @Column(name = "active", columnDefinition = "boolean default true")
    private Boolean active;

    /**
     * Role of user
     * <P>
     * One user can has one or more Role
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    /**
     * Default Constructor
     */
    public User() {
    }

    /**
     * Constructor to set value of this class variable
     *
     * @param name name of user
     * @param userName username that will be use when user signin into system
     * @param email email of user that will be use when user signin into system
     * @param password password that will be use when user signin into system
     */
    public User(String name, String userName, String email, String password) {
        this.name = name;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

}
