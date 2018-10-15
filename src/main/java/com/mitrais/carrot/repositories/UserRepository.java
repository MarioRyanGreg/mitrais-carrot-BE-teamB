package com.mitrais.carrot.repositories;

import com.mitrais.carrot.models.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * this is interface of JPA repository for User
 * https://docs.spring.io/spring-data/jpa/docs/1.8.x/reference/html/#jpa.query-methods.query-creation
 * 
 * @author Febri_MW251
 */
@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * find user by email
     *
     * @param email String email of User
     * @return will return User Object
     */
    public User findByEmail(String email);

    /**
     * find by username or email
     *
     * @param username string of username
     * @param email String email of user
     * @return will return User Optional Object
     */
    public Optional<User> findByUserNameOrEmail(String username, String email);

    /**
     * find user by user id list
     *
     * @param userIds  List Long of userIds
     * @return will return Array of User object
     */
    public List<User> findByIdIn(List<Long> userIds);

    /**
     * find user by username
     *
     * @param userName string of username
     * @return return Obejct of User
     */
    public User findByUserName(String userName);

    /**
     * check user exist or not by username
     *
     * @param username String username
     * @return will return false if user is exist on database
     * <P>
     * will return true if user not exist in database
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
}
