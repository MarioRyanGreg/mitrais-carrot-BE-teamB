package com.mitrais.carrot.config.jwt;

import com.mitrais.carrot.models.User;
import com.mitrais.carrot.repositories.UserRepository;
import com.mitrais.carrot.validation.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * service class to handling userRepository
 * 
 * @author Febri
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * user repository variable
     */
    private UserRepository userRepository;

    /**
     * CustomUserDetailsService constructor
     * 
     * @param userRepository  UserRepository class dependency
     */
    public CustomUserDetailsService(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * find user by user name or email
     *
     * @param usernameOrEmail String value of username or email value of user
     * @return UserDetails object
     * @throws UsernameNotFoundException throwing UsernameNotFoundException class if there is an error 
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        // Let people login with either username or email
        User user = userRepository.findByUserNameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail)
                );

        return UserPrincipal.create(user);
    }

    /**
     * find user by id and register user to user principal class
     * 
     * @param id pk of user
     * @return UserDetails object
     */
    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("User", "id", id)
                );

        return UserPrincipal.create(user);
    }
}
