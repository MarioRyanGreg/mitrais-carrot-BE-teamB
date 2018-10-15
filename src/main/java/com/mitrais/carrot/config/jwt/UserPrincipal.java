package com.mitrais.carrot.config.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mitrais.carrot.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * implement UserDetails to set custom UserDetails data
 *
 * @author Febri
 */
public class UserPrincipal implements UserDetails {

	/**
	 * genereate default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * id PK user
	 */
    private Long id;

    /**
     * user name
     */
    private String name;

    /**
     * username user
     */
    private String username;

    /**
     * email
     */
    @JsonIgnore
    private String email;

    /**
     * password user
     */
    @JsonIgnore
    private String password;

    /**
     * collection of authorities of user
     */
    private Collection<? extends GrantedAuthority> authorities;

    /**
     * UserPrincipal constructor
     * 
     * @param id PK user
     * @param name name of user
     * @param username username of user
     * @param email email of user
     * @param password password of user
     * @param authorities auhtorities collection of user
     */
    public UserPrincipal(Long id, String name, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    /**
     * create method to set User object into UserPrincipal object
     * 
     * @param user User object
     * @return new UserPrincipal object that contain user data
     */
    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role
                -> new SimpleGrantedAuthority(role.getRoleName())
        ).collect(Collectors.toList());

        return new UserPrincipal(
                user.getId(),
                user.getName(),
                user.getUserName(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
