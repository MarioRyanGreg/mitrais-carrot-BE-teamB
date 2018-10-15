package com.mitrais.carrot.services;

import com.mitrais.carrot.models.Role;
import com.mitrais.carrot.models.User;
import com.mitrais.carrot.payload.SignUpRequest;
import com.mitrais.carrot.repositories.RoleRepository;
import com.mitrais.carrot.repositories.UserRepository;
import com.mitrais.carrot.services.interfaces.IUser;
import com.mitrais.carrot.validation.exception.AppException;
import com.mitrais.carrot.validation.exception.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * interface for Implement user service
 *
 * @author Febri_MW251@mitrais.com
 */
@Service
public class UserService implements IUser {

    /**
     * user repository
     */
    private UserRepository userRepository;

    /**
     * passwordEncoder variable
     */
    private PasswordEncoder passwordEncoder;

    /**
     * RoleRepository variabel
     */
    private RoleRepository roleRepository;

    /**
     * User Service Constructor
     *
     * @param userRepository set dependency injection of UserRepository repository
     * @param passwordEncoder set dependency injection of PasswordEncoder repository
     * @param roleRepository set dependency injection of RoleRepository repository
     */
    public UserService(
            @Autowired UserRepository userRepository,
            @Autowired(required=false) PasswordEncoder passwordEncoder,
            @Autowired RoleRepository roleRepository
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    /**
     * find user by email
     *
     * @param email String email of user
     * @return will return User object if user email exist in database
     */
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * find user by username or email
     *
     * @param username String username of user
     * @param email String email of user
     * @return will return Optional User object if user email exist in database
     */
    @Override
    public Optional<User> findByUserNameOrEmail(String username, String email) {
        return userRepository.findByUserNameOrEmail(username, email);
    }

    /**
     * find by id in list array id (1,2,3)
     *
     * @param userIds Array list of user id
     * @return will return Array object of User if user email exist in database
     */
    @Override
    public List<User> findByIdIn(List<Long> userIds) {
        return userRepository.findByIdIn(userIds);
    }

    /**
     * find user by username
     *
     * @param userName String username of user
     * @return will return User object if user email exist in database
     */
    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    /**
     * find by username is exist or not
     *
     * @param username string username of user
     * @return will return Boolean type if user email exist in database
     */
    @Override
    public Boolean existsByUserName(String username) {
        return userRepository.existsByUserName(username);
    }

    /**
     * check user exist or not by email
     *
     * @param email value of email of user
     * @return will return false if user is exist by email on database
     * <P>
     * will return true if user not exist by email in database
     */
    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * find all data by is deleted value condition
     *
     * @param isdeleted Integer of deleted
     * @return will return Array object of User that User is deleted by param value 
     */
    @Override
    public Iterable<User> findBydeletedIn(Integer isdeleted) {
        return userRepository.findBydeletedIn(isdeleted);
    }

    /**
     * find all data by is deleted condition
     *
     * @return will return Array object of User that User is deleted null
     */
    @Override
    public Iterable<User> findAllBydeletedIsNull() {
        return userRepository.findAllBydeletedIsNull();
    }

    /**
     * find all data by is deleted false
     *
     * @return will return Array object of User that User is deleted false
     */
    @Override
    public Iterable<User> findAllBydeletedFalse() {
        return userRepository.findAllBydeletedFalse();
    }

    /**
     * save user data into database
     *
     * @param user Object User
     * @return will return User object that successfully saved in database
     */
    @Override
    public User save(User user) {
    	user.setDeleted(false);
    	user.setCreatedTime(LocalDateTime.now());
        user.setLastModifiedTime(LocalDateTime.now());
        return userRepository.save(user);
    }

    /**
     * find user by id
     *
     * @param id id of user
     * @return will return Optional User object
     */
    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * deleted process by set deleted as true
     *
     * @param user Object User
     */
    @Override
    public void deleted(User user) {
        user.setDeleted(true);
        user.setLastModifiedTime(LocalDateTime.now());
        userRepository.save(user);
    }

    /**
     * deleted process by set deleted as true
     *
     * @param id id of User
     */
    @Override
    public void deletedUserById(Long id) {
        User user = this.findById(id).orElseThrow(() -> new ResourceNotFoundException("Data", "id", id));
        user.setDeleted(true);
        user.setLastModifiedTime(LocalDateTime.now());
        userRepository.save(user);
    }

    /**
     * to implement user register/create
     *
     * @param signUpRequest model of signup request
     * @return User object that successfully saved in database
     */
    @Override
    public User registerUser(SignUpRequest signUpRequest) {

        // Creating user's account
        User user = new User(
                signUpRequest.getName(),
                signUpRequest.getUserName(),
                signUpRequest.getEmail(),
                signUpRequest.getPassword()
        );
        user.setCreatedTime(LocalDateTime.now());
        user.setLastModifiedTime(LocalDateTime.now());
        user.setDeleted(false);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByRoleName("ROLE_STAFF");
        if (userRole == null) {
            throw new AppException("User Role not set.");
        }

        user.setRoles(Collections.singleton(userRole));

        return userRepository.save(user);
    }

    /**
     * check user availability by key and value
     *
     * @param key key of data to be check
     * @param value value of key to be check
     * @return UserIdentityAvailability object
     */
    @Override
    public Boolean isUserAvailable(String key, String value) {
        Boolean isAvailable = false;
        if ("username".equals(key)) {
            isAvailable = !userRepository.existsByUserName(value);
        } else if ("email".equals(key)) {
            isAvailable = !userRepository.existsByEmail(value);
        }
        return isAvailable;
    }
}
