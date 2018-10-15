package com.mitrais.carrot.controllers;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.mitrais.carrot.config.SecurityConfig;
import com.mitrais.carrot.dummy.DateDummy;
import com.mitrais.carrot.models.User;
import com.mitrais.carrot.repositories.UserRepository;
import com.mitrais.carrot.services.UserService;
import com.mitrais.carrot.utils.CarrotAuthTestHelper;
import com.mitrais.carrot.validation.exception.ResourceNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(SecurityConfig.class)
public class UserControllerTest {

    /**
     * get base url from config application.properties
     */
    @Value("${spring.data.rest.basePath}")
    private String baseUrl;

    /**
     * adding User base url
     */
    private String userEndpointPath;

    /**
     * id pk of user
     */
    private Long idPk = 212L;

    /**
     * mock mvc
     */
    private MockMvc mvc;

    /**
     * WebApplicationContext variable mock
     */
    @Autowired
    private WebApplicationContext context;

    /**
     * UserService variable
     */
    @MockBean
    private UserService userServiceMock;

    /**
     * UserRepository variable
     */
    @MockBean
    private UserRepository userRepositoryMock;

    /**
     * to intialize default variable value
     */
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        this.userEndpointPath = this.baseUrl + "/users";
    }

    /**
     * create dummy data of User object
     *
     * @return User object dummy
     */
    private User dummyUser() {
        User user = new User("John doe", "john", "johndoe@gmail.com", "Dw21kwkw");
        user.setId(this.idPk);
        user.setDeleted(Boolean.FALSE);
        user.setActive(Boolean.TRUE);
        user.setCreatedBy(1);
        user.setCreatedTime(DateDummy.myLocalDateTime(null));
        user.setLastModifiedTime(DateDummy.myLocalDateTime(null));
        return user;
    }

    /**
     * should return 401 unauthorized becuse access with anonymous user
     *
     * @throws Exception throwing exception
     * @verifies should return status().isUnauthorized()
     */
    @Test
    @WithAnonymousUser
    public void testShouldGetUnauthorizedGetRequest() throws Exception {
        mvc.perform(get(this.userEndpointPath)).andExpect(status().isUnauthorized());
    }

    /**
     * Test fetch all data from `api/v1/users` endpoint that returning not empty
     * data
     *
     * @throws Exception throwing exception
     * @verifies should return status().isOk() and hasSize(1)
     */
    @Test
    @WithMockUser
    public void testAll() throws Exception {

        User user = this.dummyUser();

        given(userServiceMock.findAllBydeletedFalse()).willReturn(Arrays.asList(user));

        mvc.perform(get(this.userEndpointPath).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].password").exists())
                .andExpect(jsonPath("$[0].roles").exists())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].id").isNotEmpty())
                .andExpect(jsonPath("$[0].id").value(user.getId()))
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[0].name").isString())
                .andExpect(jsonPath("$[0].name").isNotEmpty())
                .andExpect(jsonPath("$[0].name").value(user.getName()))
                .andExpect(jsonPath("$[0].email").exists())
                .andExpect(jsonPath("$[0].email").isString())
                .andExpect(jsonPath("$[0].email").isNotEmpty())
                .andExpect(jsonPath("$[0].email").value(user.getEmail()))
                .andExpect(jsonPath("$[0].deleted").exists())
                .andExpect(jsonPath("$[0].deleted").isBoolean())
                .andExpect(jsonPath("$[0].deleted").isNotEmpty())
                .andExpect(jsonPath("$[0].deleted").value(Boolean.FALSE));
    }

    /**
     * Test fetch all data from `api/v1/users` endpoint that returning empty
     * data
     *
     * @throws Exception throwing exception
     * @verifies should return status().isOk() and hasSize(1)
     */
    @Test
    @WithMockUser
    public void testAllReturnEmpty() throws Exception {

        given(userServiceMock.findAllBydeletedFalse()).willReturn(new ArrayList<>());

        mvc.perform(get(this.userEndpointPath).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    /**
     * Test get data by id with anonymous user
     *
     * @throws Exception throwing exception
     * @verifies should return status().isUnauthorized()
     */
    @Test
    @WithAnonymousUser
    public void testGetShowByIdAuthorized() throws Exception {
        mvc.perform(get(this.userEndpointPath + "/" + this.idPk).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    /**
     * Test get data by id with anonymous user
     *
     * @throws Exception throwing exception
     * @verifies should return status().isNotFound()
     */
    @Test
    @WithMockUser
    public void testGetShowByIdNotFound() throws Exception {
        Long id = 11L;
        given(userServiceMock.findById(Mockito.anyLong())).willThrow(new ResourceNotFoundException("Data", "id", id));

        mvc.perform(get(this.userEndpointPath + "/" + this.idPk).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").exists()).andExpect(jsonPath("$.success").isBoolean())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.message").value("Data not found with id : '" + id + "'"));
    }

    /**
     * Test get data by id with anonymous user
     *
     * @throws Exception throwing exception
     * @verifies should return status().isOk()
     */
    @Test
    @WithMockUser
    public void testGetShowByIdOk() throws Exception {
        User user = this.dummyUser();

        given(userServiceMock.findById(Mockito.anyLong())).willReturn(Optional.of(user));

        mvc.perform(get(this.userEndpointPath + "/" + this.idPk).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.password").exists())
                .andExpect(jsonPath("$.roles").exists())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(jsonPath("$.name").value(user.getName()))
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.email").isString())
                .andExpect(jsonPath("$.email").isNotEmpty())
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    /**
     * Test DELETE success
     *
     * @throws Exception throwing exception
     * @verifies should return status().isOk()
     */
    @Test
    @WithMockUser
    public void testDeleteUserDataWithSuccessfully() throws Exception {

        given(userServiceMock.findById(Mockito.anyLong())).willReturn(Optional.of(this.dummyUser()));
        doNothing().when(userServiceMock).deleted(Mockito.any(User.class));

        mvc.perform(delete(this.userEndpointPath + "/" + this.idPk).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").exists()).andExpect(jsonPath("$.success").isBoolean())
                .andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.message").value("Data id : " + this.idPk + " deleted successfully"));
    }

    /**
     * Test DELETE Not found data by id
     *
     * @throws Exception throwing exception
     * @verifies should return status().isNotFound()
     */
    @Test
    @WithMockUser
    public void testDeleteUserWithNotFound() throws Exception {
        given(userServiceMock.findById(Mockito.anyLong())).willThrow(new ResourceNotFoundException("Data", "id", this.idPk));
        doNothing().when(userServiceMock).deleted(Mockito.any(User.class));

        mvc.perform(delete(this.userEndpointPath + "/" + this.idPk).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").exists()).andExpect(jsonPath("$.success").isBoolean())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.message").value("Data not found with id : '" + this.idPk + "'"));
    }

    /**
     * Test DELETE to delete User Data with not valid param id
     *
     * @throws Exception throwing exception
     * @verifies should return status().isMethodNotAllowed()
     */
    @Test
    @WithMockUser
    public void testDeleteWithoutIdUserPK() throws Exception {
        mvc.perform(delete(this.userEndpointPath).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

    /**
     * Test get users profile by UserPrincipal data with anonymous user should
     * unauthorized
     *
     * @throws Exception throwing exception
     * @verifies should return status().isUnauthorized()
     */
    @Test
    @WithAnonymousUser
    public void testGetUserMeUnauthorized() throws Exception {

        mvc.perform(get(this.userEndpointPath + "/me").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    /**
     * Test get users profile by UserPrincipal data with anonymous user should
     * unauthorized
     *
     * @throws Exception throwing exception
     * @verifies should return status().isOk()
     */
    @Test
    public void testGetUserMeAuthorized() throws Exception {
        User user = this.dummyUser();
        // mock UserPrincipal
        CarrotAuthTestHelper.letMeIn(user, Boolean.TRUE);
        mvc.perform(get(this.userEndpointPath + "/me").contentType(MediaType.APPLICATION_JSON).header("Authorization", "Bearer xx"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.username").exists())
                .andExpect(jsonPath("$.username").isString())
                .andExpect(jsonPath("$.username").value(user.getUserName()))
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.name").value(user.getName()));
    }

    /**
     * Test get my profile by userprincipal data
     * Case 1 : should return 404 because user exist
     *
     * @throws Exception throwing exception
     * @verifies should return status().isNotFound()
     */
    @Test
    public void testGetMyProfileNotFoundUser() throws Exception {

        // mock auth UserPrincipal
        CarrotAuthTestHelper.letMeIn(this.dummyUser(), Boolean.TRUE);
        given(userServiceMock.findByUserName(Mockito.anyString())).willReturn(null);

        mvc.perform(get(this.userEndpointPath + "/myprofile").contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").isBoolean()).andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.timestamp").exists()).andExpect(jsonPath("$.timestamp").isString());
    }

    /**
     * Test get my profile by userprincipal data Case 2 : should return correct
     * response
     *
     * @throws Exception throwing exception
     * @verifies should return status().isOk()
     */
    @Test
    public void testGetMyProfileShouldReturnCorrectResponse() throws Exception {
        // mock UserPrincipal
        User user = this.dummyUser();
        CarrotAuthTestHelper.letMeIn(user, Boolean.TRUE);

        given(userServiceMock.findByUserName(user.getUserName())).willReturn(user);

        mvc.perform(get(this.userEndpointPath + "/myprofile").contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.username").exists())
                .andExpect(jsonPath("$.username").isString())
                .andExpect(jsonPath("$.username").value(user.getUserName()))
                .andExpect(jsonPath("$.email").exists())
                .andExpect(jsonPath("$.email").isString())
                .andExpect(jsonPath("$.email").value(user.getEmail()));
    }

    /**
     * Test check availability user by key and value of variable that already
     * defined
     *
     * @throws Exception throwing exception
     * @verifies should return status().isOk()
     */
    @Test
    @WithMockUser
    public void testGetCheckUserAvailability() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("key", "xxx");
        params.add("value", "xxx");

        given(userServiceMock.isUserAvailable(Mockito.anyString(), Mockito.anyString())).willReturn(Boolean.FALSE);

        mvc.perform(get(this.userEndpointPath + "/availability").params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.available").exists())
                .andExpect(jsonPath("$.available").isBoolean())
                .andExpect(jsonPath("$.available").value(Boolean.FALSE));
    }

    /**
     * Test check availability user by key and value of variable that already
     * defined
     *
     * @throws Exception throwing exception
     * @verifies should return status().isOk()
     */
    @Test
    @WithMockUser
    public void testGetCheckUserAvailabilityByUsername() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("key", "username");
        params.add("value", "xxx");

        given(userRepositoryMock.existsByUserName(Mockito.anyString())).willReturn(Boolean.FALSE);

        mvc.perform(get(this.userEndpointPath + "/availability").params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.available").exists())
                .andExpect(jsonPath("$.available").isBoolean())
                .andExpect(jsonPath("$.available").value(Boolean.FALSE));
    }
}
