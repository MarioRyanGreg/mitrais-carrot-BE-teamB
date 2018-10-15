package com.mitrais.carrot.controllers;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.mitrais.carrot.config.jwt.JwtTokenProvider;
import com.mitrais.carrot.dummy.RegisterDummy;
import com.mitrais.carrot.models.Role;
import com.mitrais.carrot.models.User;
import com.mitrais.carrot.payload.LoginRequest;
import com.mitrais.carrot.payload.SignUpRequest;
import com.mitrais.carrot.repositories.RoleRepository;
import com.mitrais.carrot.repositories.UserRepository;
import com.mitrais.carrot.services.UserService;
import org.springframework.beans.factory.annotation.Value;

/**
 * endpoint test case for Auth controller class
 *
 * @author Febri_MW251
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthControllerTest {

    /**
     * get base url from config application.properties
     */
    @Value("${spring.data.rest.basePath}")
    private String baseUrl;

    /**
     * mock mvc
     */
    private MockMvc mvc;

    /**
     * WebApplicationContext variable
     */
    @Autowired
    private WebApplicationContext context;

    /**
     * set AuthenticationManager variable
     */
    @MockBean
    private AuthenticationManager authenticationManager;

    /**
     * UserRepository variable
     */
    @MockBean
    private UserRepository userRepositoryMock;

    /**
     * UserRepository variable
     */
    @MockBean
    private RoleRepository roleRepositoryMock;

    /**
     * JwtTokenProvider variable
     */
    @MockBean
    private JwtTokenProvider tokenProvider;

    /**
     * UserService variable
     */
    @MockBean
    private UserService userServiceMock;

    /**
     * passwordEncoder variable
     */
    @MockBean
    private PasswordEncoder passwordEncoderMock;

    /**
     * setup to intialize default variable value
     */
    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    /**
     * singin path test with successfully login into system
     *
     * @throws Exception throwing exception if there is an error
     */
    @Test
    @WithAnonymousUser
    public void singinSuccessfullyTest() throws Exception {

        LoginRequest loginRequest = new LoginRequest("giveMeRandomString", "giveMeRandomPassword");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        given(tokenProvider.generateToken(authentication)).willReturn("xxxx");

        RequestBuilder requestBuilder = post(this.baseUrl + "/signin").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginRequest));

        // expected 200 return http code
        mvc.perform(requestBuilder).andExpect(status().is2xxSuccessful()).andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.accessToken").isString()).andExpect(jsonPath("$.accessToken").value("xxxx"))
                .andExpect(jsonPath("$.tokenType").exists()).andExpect(jsonPath("$.tokenType").isString())
                .andExpect(jsonPath("$.tokenType").value("Bearer"));
    }

    /**
     * singin path test with not successfully login into system
     *
     * @throws Exception throwing exception if there is an error
     */
    @Test
    @WithAnonymousUser
    public void singinNotSuccessfullyTest() throws Exception {

        LoginRequest loginRequest = new LoginRequest("cakpep", "cakpep");

        given(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword())))
                .willThrow(new RuntimeException("Bad credentials"));

        RequestBuilder requestBuilder = post(this.baseUrl + "/signin").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginRequest));

        // expected 500 return http code
        mvc.perform(requestBuilder).andExpect(status().is(404)).andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").isBoolean()).andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").exists()).andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.message").value("Bad credentials"));
    }

    /**
     * singup path test test with successfully register
     *
     * @throws Exception throwing exception if there is an error
     */
    @Test
    @WithAnonymousUser
    public void singUpUserRegisterSuccessfullyTest() throws Exception {

        SignUpRequest singupRequest = RegisterDummy.dummyRegisterUser();

        User user = new User(singupRequest.getName(), singupRequest.getUserName(), singupRequest.getEmail(),
                singupRequest.getPassword());
        user.setId(1L);

        given(userServiceMock.existsByUserName(singupRequest.getUserName())).willReturn(false);
        given(userServiceMock.existsByEmail(singupRequest.getEmail())).willReturn(false);

        given(roleRepositoryMock.findByRoleName("ROLE_STAFF")).willReturn(new Role(1, "ROLE_STAFF"));

        given(passwordEncoderMock.encode(user.getPassword())).willReturn("xxx");
        given(userRepositoryMock.save(user)).willReturn(user);

        // request to endpoint
        RequestBuilder requestBuilder = post(this.baseUrl + "/signup").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(singupRequest));

        // expected 200 return http code
        mvc.perform(requestBuilder).andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.message").value("User registered successfully"))
                .andExpect(jsonPath("$.success").isBoolean()).andExpect(jsonPath("$.success").value(true))
                .andExpect(status().is2xxSuccessful());
    }

    /**
     * singup path test test with Email Address already in use!
     *
     * @throws Exception throwing exception if there is an error
     */
    @Test
    @WithAnonymousUser
    public void singUpUserRegisterExistByEmailTest() throws Exception {

        SignUpRequest singupRequest = RegisterDummy.dummyRegisterUser();

        given(userServiceMock.existsByEmail(singupRequest.getEmail())).willReturn(true);

        // request to endpoint
        RequestBuilder requestBuilder = post(this.baseUrl + "/signup").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(singupRequest));

        // expected 400 return http code
        mvc.perform(requestBuilder).andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.message").value("Email Address already in use!"))
                .andExpect(jsonPath("$.success").isBoolean()).andExpect(jsonPath("$.success").value(false))
                .andExpect(status().is(400));
    }

    /**
     * singup path test test with Username is already taken!
     *
     * @throws Exception throwing exception if there is an error
     */
    @Test
    @WithAnonymousUser
    public void singUpUserRegisterExistByUsernameTest() throws Exception {

        SignUpRequest singupRequest = RegisterDummy.dummyRegisterUser();

        given(userServiceMock.existsByUserName(singupRequest.getUserName())).willReturn(true);
        given(userServiceMock.existsByEmail(singupRequest.getEmail())).willReturn(false);

        // request to endpoint
        RequestBuilder requestBuilder = post(this.baseUrl + "/signup").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(singupRequest));

        // expected 400 return http code
        mvc.perform(requestBuilder).andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.message").value("Username is already taken!"))
                .andExpect(jsonPath("$.success").isBoolean()).andExpect(jsonPath("$.success").value(false))
                .andExpect(status().is(400));
    }
}
