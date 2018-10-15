package com.mitrais.carrot.controllers;

import com.mitrais.carrot.config.jwt.CurrentUser;
import com.mitrais.carrot.config.jwt.UserPrincipal;
import com.mitrais.carrot.models.User;
import com.mitrais.carrot.payload.ApiResponse;
import com.mitrais.carrot.payload.UserIdentityAvailability;
import com.mitrais.carrot.payload.UserProfile;
import com.mitrais.carrot.payload.UserSummary;
import com.mitrais.carrot.services.UserService;
import com.mitrais.carrot.validation.exception.ResourceNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * user rest api controller to handle user crud process
 *
 * @author Febri
 */
@CrossOrigin
@RestController
@RequestMapping("${spring.data.rest.basePath}")
@Api(value = "users", description = "Crud service for user")
public class UserController {

	/**
	 * variable of UserService service 
	 */
    private UserService userService;

    /**
     * constructor to set dependency injection
     * 
     * @param userService the service for User
     */
    public UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    /**
     * get all data
     *
     * @return will return all user with deleted status is 0
     */
    @GetMapping("/users")
    @ResponseBody
    @ApiOperation(value = "Get all user", notes = "Returns list of all Users in the database.", response = User.class)
    public ResponseEntity<Iterable<User>> all() {
        return new ResponseEntity<Iterable<User>>(userService.findAllBydeletedFalse(), HttpStatus.OK);
    }

    /**
     * get detail by id
     *
     * @param id Long variable type this param is id of primary key user 
     * @return if success will return ResponseEntity object and it contain User object else will throwing the ResourceNotFoundException
     * 
     */
    @GetMapping("/users/{id}")
    @ResponseBody
    @ApiOperation(value = "Get user by id", notes = "Returns singgle user by user id.")
    public ResponseEntity<User> show(
            @ApiParam("Id of the user to be obtained. Cannot be empty.") @PathVariable Long id
    ) {
        User user = userService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Data", "id", id));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * action delete to set is deleted = 1
     *
     * @param id Long variable type this param is id of primary key user 
     * @return if success will return ResponseEntity object and it contain ApiResponse object else will throwing the ResourceNotFoundException
     */
    @DeleteMapping("/users/{id}")
    @ResponseBody
    @ApiOperation(value = "Delete user by id", notes = "Delete user by id and system will mark the user as deleted.")
    public ResponseEntity<ApiResponse> delete(
            @ApiParam("Id of the person to be deleted. Cannot be empty.") @PathVariable Long id
    ) {
    	User user = userService.findById(id).orElseThrow(() -> new ResourceNotFoundException("Data", "id", id));
        userService.deleted(user);
        return new ResponseEntity<>(new ApiResponse(true, "Data id : " + id + " deleted successfully"), HttpStatus.OK);
    }

    /**
     * get current user
     *
     * @param currentUser is using by system in backend process to find the current user, it identify by Header Bearer token that already signin
     * @return UserSummary will return ResponseEntity contain UserSummary object
     */
    @GetMapping("/users/me")
    @ApiOperation(value = "Get current user", notes = "Get current user that already login by the bearer token.")
    public ResponseEntity<UserSummary> getCurrentUser(
            @ApiParam("No need to set to the request because the system will find the user that already login with the token. Cannot be empty.")
            @CurrentUser UserPrincipal currentUser
    ) {
        UserSummary us = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
        return new ResponseEntity<UserSummary>(us, HttpStatus.OK);
    }

    /**
     * get user profile with more detailed data
     *
     * @param currentUser is using by system in backend process to find the current user, it identify by Header Bearer token that already signin
     * @return UserProfile will return ResponseEntity contain UserProfile object
     */
    @GetMapping("/users/myprofile")
    @ApiOperation(value = "Get current user profile", notes = "Get current user profile that already login by the bearer token.")
    public ResponseEntity<UserProfile> getUserProfile(@CurrentUser UserPrincipal currentUser) {
        User user = userService.findByUserName(currentUser.getUsername());
        if (user == null) {
            throw new ResourceNotFoundException("User", "username", currentUser.getUsername());
        }

        UserProfile userProfile = new UserProfile(
                user.getId(),
                user.getUserName(),
                user.getEmail(),
                user.getName(),
                user.getCreatedTime()
        );
        return new ResponseEntity<UserProfile>(userProfile, HttpStatus.OK);
    }

    /**
     * check user availabilities by key and value available : username and email
     *
     * @param key Key of the user to be check. Cannot be empty
     * @param value Value of key to be check. Cannot be empty
     * @return UserIdentityAvailability will return UserIdentityAvailability that contain user availability result 
     */
    @GetMapping("/users/availability")
    @ApiOperation(value = "Get availability user", notes = "Get user availability by key and value currently only username and email that already available.")
    public UserIdentityAvailability checkUserAvailability(
            @ApiParam("Key of the user to be check. Cannot be empty.") @RequestParam(value = "key") String key,
            @ApiParam("Value of key to be check. Cannot be empty.") @RequestParam(value = "value") String value
    ) {
    	Boolean isAvailable = userService.isUserAvailable(key, value);
        return new UserIdentityAvailability(isAvailable);
    }
}
