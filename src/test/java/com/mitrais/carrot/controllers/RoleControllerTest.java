package com.mitrais.carrot.controllers;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitrais.carrot.config.SecurityConfig;
import com.mitrais.carrot.models.Role;
import com.mitrais.carrot.repositories.RoleRepository;
import com.mitrais.carrot.services.RoleService;
import com.mitrais.carrot.validation.exception.ResourceNotFoundException;

/**
 * Role controller test
 *
 * @author Febri_MW251
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(SecurityConfig.class)
public class RoleControllerTest {

    /**
     * get base url from config application.properties
     */
    @Value("${spring.data.rest.basePath}")
    private String baseUrl;

    /**
     * adding role base url
     */
    private String roleUrl;

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
     * variable of RoleService service mock
     */
    @MockBean
    public RoleService roleServiceMock;

    /**
     * variable of RoleRepository interface mock
     */
    @MockBean
    public RoleRepository roleRepositoryMock;

    /**
     * to intialize default variable value
     */
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        this.roleUrl = this.baseUrl + "/roles";
    }

    /**
     * should return 401 unauthorized becuse there is no JWT bearer token test
     * get all endpoint
     *
     * @throws Exception throwing exception
     * @verifies should return status().isUnauthorized()
     */
    @Test
    @WithAnonymousUser
    public void testShouldGetUnauthorizedGetRequest() throws Exception {
        mvc.perform(get(this.roleUrl)).andExpect(status().isUnauthorized());
    }

    /**
     * should return 401 unauthorized becuse there is no JWT bearer token test
     * post all endpoint
     *
     * @throws Exception
     * @verifies should return status().isUnauthorized()
     */
    @Test
    @WithAnonymousUser
    public void testShouldGetUnauthorizedWithoutJWTTokenPostRequest() throws Exception {
        mvc.perform(post(this.roleUrl)).andExpect(status().isUnauthorized());
    }

    /**
     * test fetch all role data
     * 
     * @throws Exception throwing exception
     * @verifies should return status().isOk() and hasSize(1)
     */
    @Test
    @WithMockUser
    public void testFetchAllDataRoles() throws Exception {

        Role role = new Role(1, "ROLE_STAFF");

        given(roleServiceMock.findAllBydeletedFalse()).willReturn(Arrays.asList(role));

        mvc.perform(get(this.roleUrl).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1))).andExpect(jsonPath("$[0].id").value(role.getId()))
                .andExpect(jsonPath("$[0].roleName").value(role.getRoleName()));
    }

    /**
     * Test Get for empty list role
     *
     * @throws Exception throwing exception
     * @verifies should return status().isOk() and hasSize(0)
     */
    @Test
    @WithMockUser
    public void testGetAllRoleIsEmpty() throws Exception {

        given(roleServiceMock.findAllBydeletedFalse()).willReturn(new ArrayList<>());

        mvc.perform(get(this.roleUrl).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    /**
     * Test POST for new Role Data
     *
     * @throws Exception throwing exception
     * @verifies should return status().isCreated()
     */
    @Test
    @WithMockUser
    public void testPostNewRoleDataWithSuccessfullyCreate() throws Exception {

        // dummy data
        Role roleBody = new Role(1, "ROLE_STAFF");
        roleBody.setDeleted(false);

        given(roleServiceMock.save(Mockito.any(Role.class))).willReturn(roleBody);

        RequestBuilder request = post(this.roleUrl).contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(roleBody));

        mvc.perform(request).andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.id").exists()).andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(roleBody.getId()))
                .andExpect(jsonPath("$.roleName").exists()).andExpect(jsonPath("$.roleName").isString())
                .andExpect(jsonPath("$.roleName").value(roleBody.getRoleName()))
                .andExpect(jsonPath("$.deleted").exists()).andExpect(jsonPath("$.deleted").isBoolean())
                .andExpect(jsonPath("$.deleted").value(false)).andExpect(jsonPath("$.createdTime").exists());
    }

    /**
     * Test POST for new Role Data with not valid request body and should be
     * return Bad request
     *
     * @throws Exception throwing exception
     * @verifies should return status().isBadRequest()
     */
    @Test
    @WithMockUser
    public void testPostNewRoleDataWithBaDRequest() throws Exception {
        mvc.perform(post(this.roleUrl).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
    }

    /**
     * Test POST for new Role Data
     *
     * @throws Exception throwing exception
     * @verifies should return status().isOk()
     */
    @Test
    @WithMockUser
    public void testPutRoleDataWithUpdateSuccessfully() throws Exception {

        // dummy data
        Role roleResponse = new Role(11, "ROLE_XXX");
        roleResponse.setDeleted(false);

        given(roleServiceMock.save(new Role(11, "ROLE_XXX"))).willReturn(roleResponse);

        given(roleServiceMock.findById(Mockito.anyInt())).willReturn(Optional.of(roleResponse));

        RequestBuilder request = put(this.roleUrl + "/11").contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new Role(11, "ROLE_XXX")));

        mvc.perform(request).andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.id").exists()).andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(roleResponse.getId()))
                .andExpect(jsonPath("$.roleName").exists()).andExpect(jsonPath("$.roleName").isString())
                .andExpect(jsonPath("$.roleName").value(roleResponse.getRoleName()))
                .andExpect(jsonPath("$.deleted").exists()).andExpect(jsonPath("$.deleted").isBoolean())
                .andExpect(jsonPath("$.deleted").value(false)).andExpect(jsonPath("$.createdTime").exists());
    }

    /**
     * Test PUT to update Role Data with not valid request body and should be
     * return Bad request
     *
     * @throws Exception throwing exception
     * @verifies should return status().isNotFound()
     */
    @Test
    @WithMockUser
    public void testPutNewRoleDataWithDataNotFound() throws Exception {

        // dummy data
        Integer id = 11;
        Role role = new Role(id, "TEST");

        given(roleServiceMock.findById(id)).willThrow(new ResourceNotFoundException("Data", "id", id));

        when(roleServiceMock.save(Mockito.any(Role.class))).thenReturn(role);

        RequestBuilder request = put(this.roleUrl + "/" + id).contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(role));

        mvc.perform(request).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").exists()).andExpect(jsonPath("$.success").isBoolean())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.timestamp").exists()).andExpect(jsonPath("$.timestamp").isString())
                .andExpect(jsonPath("$.message").exists()).andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.message").value("Data not found with id : '11'"))
                .andExpect(jsonPath("$.details").exists()).andExpect(jsonPath("$.details").isString());
    }

    /**
     * Test PUT to update Role Data with not valid request body and should be
     * return Bad request
     *
     * @throws Exception throwing exception
     * @verifies should return status().isBadRequest()
     */
    @Test
    @WithMockUser
    public void testPutNewRoleDataWithBaDRequest() throws Exception {
        mvc.perform(put(this.roleUrl + "/11").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test PUT to update Role Data with not valid param id
     *
     * @throws Exception throwing exception
     * @verifies should return status().isMethodNotAllowed()
     */
    @Test
    @WithMockUser
    public void testPutWithoutIdRolePK() throws Exception {
        mvc.perform(put(this.roleUrl).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isMethodNotAllowed());
    }

    /**
     * Test GET role by id success
     *
     * @throws Exception throwing exception
     * @verifies should return status().isOk()
     */
    @Test
    @WithMockUser
    public void testGetRoleById() throws Exception {
        Integer id = 1111111;
        given(roleServiceMock.findById(Mockito.anyInt())).willReturn(Optional.of(new Role(id, "xx")));

        mvc.perform(get(this.roleUrl + "/" + id).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists()).andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.roleName").exists()).andExpect(jsonPath("$.roleName").isString())
                .andExpect(jsonPath("$.roleName").value("xx"));
    }

    /**
     * Test GET Not found data by id
     *
     * @throws Exception throwing exception
     * @verifies should return status().isNotFound()
     */
    @Test
    @WithMockUser
    public void testGetRoleByIdWithNotFound() throws Exception {
        Integer id = 1111111;

        given(roleServiceMock.findById(Mockito.anyInt())).willThrow(new ResourceNotFoundException("Data", "id", id));

        mvc.perform(get(this.roleUrl + "/" + id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").exists()).andExpect(jsonPath("$.success").isBoolean())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.message").value("Data not found with id : '" + id + "'"));
    }

    /**
     * Test Get Role Data with not valid param id
     *
     * @throws Exception throwing exception
     * @verifies should return status().isBadRequest()
     */
    @Test
    @WithMockUser
    public void testGetRoleByIdWithoutIdRolePK() throws Exception {
        mvc.perform(get(this.roleUrl + "/xx").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test DELETE success
     *
     * @throws Exception throwing exception
     * @verifies should return status().isOk()
     */
    @Test
    @WithMockUser
    public void testDeleteRoleDataWithSuccessfully() throws Exception {
        Integer id = 1111111;
        given(roleServiceMock.findById(Mockito.anyInt())).willReturn(Optional.of(new Role(id, "xx")));
        doNothing().when(roleServiceMock).deleted(Mockito.any(Role.class));

        mvc.perform(delete(this.roleUrl + "/" + id).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.success").exists()).andExpect(jsonPath("$.success").isBoolean())
                .andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.message").value("Data id : " + id + " deleted successfully"));
    }

    /**
     * Test DELETE Not found data by id
     *
     * @throws Exception throwing exception
     * @verifies should return status().isNotFound()
     */
    @Test
    @WithMockUser
    public void testDeleteRoleWithNotFound() throws Exception {
        Integer id = 1111111;

        given(roleServiceMock.findById(Mockito.anyInt())).willThrow(new ResourceNotFoundException("Data", "id", id));
        doNothing().when(roleServiceMock).deleted(Mockito.any(Role.class));

        mvc.perform(delete(this.roleUrl + "/" + id).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").exists()).andExpect(jsonPath("$.success").isBoolean())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.message").value("Data not found with id : '" + id + "'"));
    }

    /**
     * Test DELETE to delete Role Data with not valid param id
     *
     * @throws Exception throwing exception
     * @verifies should return status().isMethodNotAllowed()
     */
    @Test
    @WithMockUser
    public void testDeleteWithoutIdRolePK() throws Exception {
        mvc.perform(delete(this.roleUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }
}
