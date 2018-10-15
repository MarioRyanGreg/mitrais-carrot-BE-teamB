package com.mitrais.carrot.controllers;

import com.mitrais.carrot.config.jwt.JwtTokenProvider;
import com.mitrais.carrot.payload.ApiResponse;
import com.mitrais.carrot.payload.JwtAuthenticationResponse;
import com.mitrais.carrot.payload.LoginRequest;
import com.mitrais.carrot.payload.SignUpRequest;
import com.mitrais.carrot.repositories.UserRepository;
import com.mitrais.carrot.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * controller to handle user sigin and signup process
 *
 * @author Febri
 */
@RestController
@RequestMapping("${spring.data.rest.basePath}")
public class AuthController {

    /**
     * variable of AuthenticationManager service
     */
    private AuthenticationManager authenticationManager;

    /**
     * variable of JwtTokenProvider service
     */
    private JwtTokenProvider tokenProvider;

    /**
     * variable of UserService service
     */
    private UserService userService;

    /**
     * auth controller constructor
     *
     * @param authenticationManager the service for AuthenticationManager
     * @param userRepository the service for UserRepository
     * @param tokenProvider the service for JwtTokenProvider
     * @param userService the service for UserService
     */
    public AuthController(
            @Autowired(required = false) AuthenticationManager authenticationManager,
            @Autowired UserRepository userRepository,
            @Autowired JwtTokenProvider tokenProvider,
            @Autowired UserService userService
    ) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }

    /**
     * signin proses when user want to access this app
     *
     * @param loginRequest request model for login request body
     * @return ResponseEntity object and contain JwtAuthenticationResponse object to show the jwt result
     */
    @PostMapping("/signin")
    @ResponseBody
    @ApiOperation(value = "login request", notes = "Return token to access all endpoint.")
    public ResponseEntity<?> authenticateUser(
            @ApiParam("Example Request body of login request. Cannot be empty.") @Valid @RequestBody LoginRequest loginRequest
    ) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    /**
     * signup process to register user
     *
     * @param signUpRequest request model for signup request body
     * @return ResponseEntity object and contain ApiResponse and also description of success or not
     */
	@PostMapping("/signup")
	@ResponseBody
	@ApiOperation(value = "signup request", notes = "Register user to the app to get the access to this app.")
	public ResponseEntity<?> registerUser(
			@ApiParam("Example Request body of register request. Cannot be empty.") @Valid @RequestBody SignUpRequest signUpRequest) {
		if (userService.existsByUserName(signUpRequest.getUserName())) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Username is already taken!"),
					HttpStatus.BAD_REQUEST);
		}

		if (userService.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Email Address already in use!"),
					HttpStatus.BAD_REQUEST);
		}

		// register/create user to db
		userService.registerUser(signUpRequest);
		return new ResponseEntity<ApiResponse>(new ApiResponse(true, "User registered successfully"), HttpStatus.OK);
	}
}
