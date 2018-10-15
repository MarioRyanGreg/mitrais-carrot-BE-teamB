package com.mitrais.carrot.services.interfaces;

import com.mitrais.carrot.models.User;
import com.mitrais.carrot.payload.SignUpRequest;
import com.mitrais.carrot.payload.UserIdentityAvailability;
import java.util.List;
import java.util.Optional;

/**
 * User interface class
 *
 * @author Febri
 */
public interface IUser {

	/**
     * save user data into database
     *
     * @param user Object User
     * @return will return User object that successfully saved in database
     */
    public User save(User user);

    /**
     * find user by id
     *
     * @param id id of user
     * @return will return Optional User object
     */
    public Optional<User> findById(Long id);

    /**
     * deleted process by set deleted as true
     *
     * @param user Object User
     */
    public void deleted(User user);

    /**
     * find user by email
     *
     * @param email String email of user
     * @return will return User object if user email exist in database
     */
    public User findByEmail(String email);

    /**
     * find user by username or email
     *
     * @param username String username of user
     * @param email String email of user
     * @return will return Optional User object if user email exist in database
     */
    public Optional<User> findByUserNameOrEmail(String username, String email);

    /**
     * find by id in list array id (1,2,3)
     *
     * @param userIds Array list of user id
     * @return will return Array object of User if user email exist in database
     */
    public List<User> findByIdIn(List<Long> userIds);

    /**
     * find user by username
     *
     * @param userName String username of user
     * @return will return User object if user email exist in database
     */
    public User findByUserName(String userName);

    /**
     * find by username is exist or not
     *
     * @param username username of user
     * @return will return Boolean type if user email exist in database
     */
    public Boolean existsByUserName(String username);

    /**
     * check user exist or not by email
     *
     * @param email value of email of user
     * @return will return false if user is exist by email on database
     * <P>
     * will return true if user not exist by email in database
     */
    public Boolean existsByEmail(String email);

    /**
     * find all data by is deleted value condition
     *
     * @param isdeleted Integer of deleted
     * @return will return Array object of User that User is deleted by param value 
     */
    public Iterable<User> findBydeletedIn(Integer isdeleted);

    /**
     * find all data by is deleted condition
     *
     * @return will return Array object of User that User is deleted null
     */
    public Iterable<User> findAllBydeletedIsNull();

    /**
     * find all data by is deleted false
     *
     * @return will return Array object of User that User is deleted false
     */
    public Iterable<User> findAllBydeletedFalse();

    /**
     * to implement user register/create
     *
     * @param signUpRequest model of signup request
     * @return User object that successfully saved in database
     */
    public User registerUser(SignUpRequest signUpRequest);

    /**
     * check user availability
     *
     * @param key key of data to be check
     * @param value value of key to be check
     * @return UserIdentityAvailability object
     */
    public Boolean isUserAvailable(String key, String value);

    /**
     * custom delete by id
     *
     * @param id id of user
     */
    public void deletedUserById(Long id);
}
