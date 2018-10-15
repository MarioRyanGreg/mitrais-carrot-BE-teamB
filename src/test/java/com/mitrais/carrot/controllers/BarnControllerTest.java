package com.mitrais.carrot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitrais.carrot.config.SecurityConfig;
import com.mitrais.carrot.dummy.BarnDummy;
import com.mitrais.carrot.models.Barn;
import com.mitrais.carrot.repositories.BarnRepository;
import com.mitrais.carrot.services.BarnService;
import com.mitrais.carrot.validation.exception.ResourceNotFoundException;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

/**
 * Barn Controller test
 *
 * @author Febri_MW251@mitrais.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(SecurityConfig.class)
public class BarnControllerTest {

    /**
     * get base url from config application.properties
     */
    @Value("${spring.data.rest.basePath}")
    private String baseUrl;

    /**
     * adding barn base url
     */
    private String barnUrl;

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
     * Barn Service mock
     */
    @MockBean
    public BarnService barnService;

    /**
     * Barn Repository mock
     */
    @MockBean
    public BarnRepository barnRepositoryMock;

    /**
     * setup is use for intialize default variable value
     */
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        this.barnUrl = this.baseUrl + "/barns";
    }

    /**
     * should return 401 unauthorized becuse there is no JWT bearer token get
     * all endpoint
     *
     * @throws Exception throwing exception
     * @verifies should return status().isUnauthorized()
     */
    @Test
    @WithAnonymousUser
    public void testGetShouldGetUnauthorizedGetRequest() throws Exception {
        mvc.perform(get(this.barnUrl)).andExpect(status().isUnauthorized());
    }

    /**
     * should return 401 unauthorized becuse there is no JWT bearer token post
     * all endpoint
     *
     * @throws Exception throwing exception if there is error
     * @verifies should return status().isUnauthorized()
     */
    @Test
    @WithAnonymousUser
    public void testPostShouldGetUnauthorizedWithoutJWTTokenPostRequest() throws Exception {
        mvc.perform(post(this.barnUrl)).andExpect(status().isUnauthorized());
    }

    /**
     * should return 401 unauthorized becuse there is no JWT bearer token put
     * endpoint
     *
     * @throws Exception throwing exception if there is error
     * @verifies should return status().isUnauthorized()
     */
    @Test
    @WithAnonymousUser
    public void testPutShouldGetUnauthorizedWithoutJWTTokenPostRequest() throws Exception {
        mvc.perform(delete(this.barnUrl + "/" + 212)).andExpect(status().isUnauthorized());
    }

    /**
     * should return 401 unauthorized becuse there is no JWT bearer token delete
     * endpoint
     *
     * @throws Exception throwing exception if there is error
     * @verifies should return status().isUnauthorized()
     */
    @Test
    @WithAnonymousUser
    public void testDeleteShouldGetUnauthorizedWithoutJWTTokenPostRequest() throws Exception {
        mvc.perform(delete(this.barnUrl + "/" + 212)).andExpect(status().isUnauthorized());
    }

    /**
     * test fetch all data with successfully
     *
     * @throws Exception throwing exception
     * @verifies should return status().isOk() and hasSize(4)
     */
    @Test
    @WithMockUser
    public void testFetchAllShouldBeSuccessfully() throws Exception {

        given(barnService.findAll()).willReturn(BarnDummy.listOfBarns());

        mvc.perform(get(this.barnUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value(BarnDummy.barnObject().getName()));
    }

    /**
     * Test fetch all data returning empty value
     *
     * @throws Exception throwing exception
     * @verifies should return status().isOk() and hasSize(0)
     */
    @Test
    @WithMockUser
    public void testFetchAllReturnEmpty() throws Exception {

        given(barnService.findAll()).willReturn(new ArrayList<>());

        mvc.perform(get(this.barnUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    /**
     * Test fetch all data returning empty value
     *
     * @throws Exception throwing exception
     * @verifies should return status().isNotFound() and jsonPath("$.success").value(Boolean.FALSE)
     */
    @Test
    @WithMockUser
    public void testFetchAllThrowingException() throws Exception {

        String errorEx = "Something error ndan";
        given(barnService.findAll()).willThrow(new RuntimeException(errorEx));

        mvc.perform(get(this.barnUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.timestamp").isString())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").isBoolean())
                .andExpect(jsonPath("$.success").value(Boolean.FALSE))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.message").value(errorEx));
    }

    /**
     * Test fetch by id to get data Barn data returning barn value
     *
     * @throws Exception throwing exception
     * @verifies should return status().isOk() and jsonPath("$.id").exists()
     */
    @Test
    @WithMockUser
    public void testFetchByIdFound() throws Exception {

        Barn responseDummy = BarnDummy.barnObject();
        given(barnService.findById(Mockito.anyInt())).willReturn(responseDummy);

        mvc.perform(get(this.barnUrl + "/" + responseDummy.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(responseDummy.getId()));
    }

    /**
     * Test fetch by id to get data Barn data returning empty value
     *
     * @throws Exception throwing exception
     * @verifies should return status().isNotFound() and
     * jsonPath("$.timestamp").exists()
     */
    @Test
    @WithMockUser
    public void testFetchByIdNotFound() throws Exception {

        given(barnService.findById(Mockito.anyInt())).willThrow(new ResourceNotFoundException("Data", "id", 111111));

        mvc.perform(get(this.barnUrl + "/111111").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.timestamp").isString())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").isBoolean())
                .andExpect(jsonPath("$.success").value(Boolean.FALSE))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.message").value("Data not found with id : '111111'"));
    }

    /**
     * Test DELETE success
     *
     * @throws Exception throwing exception
     * @verifies should return status().isOk() & jsonPath("$.success").value(Boolean.TRUE)
     */
    @Test
    @WithMockUser
    public void testDeleteDataWithSuccessfully() throws Exception {
        Integer id = 1111111;
        Barn responseDummy = BarnDummy.barnObject();
        given(barnService.findById(Mockito.anyInt())).willReturn(responseDummy);

        responseDummy.setDeleted(Boolean.TRUE);
        given(barnService.save(Mockito.any(Barn.class))).willReturn(responseDummy);

        mvc.perform(delete(this.barnUrl + "/" + id).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(jsonPath("$.success").exists()).andExpect(jsonPath("$.success").isBoolean())
                .andExpect(jsonPath("$.success").value(true)).andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.message").value("Data id : " + id + " deleted successfully"));
    }

    /**
     * Test DELETE with not found id of data
     *
     * @throws Exception throwing exception
     * @verifies should return status().isNotFound() and
     * jsonPath("$.success").value(Boolean.FALSE)
     */
    @Test
    @WithMockUser
    public void testDeleteDataWithNotFound() throws Exception {
        Integer id = 111;
        given(barnService.findById(Mockito.anyInt())).willThrow(new ResourceNotFoundException("Data", "id", id));

        mvc.perform(delete(this.barnUrl + "/" + id).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").exists()).andExpect(jsonPath("$.success").isBoolean())
                .andExpect(jsonPath("$.success").value(Boolean.FALSE)).andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.message").value("Data not found with id : '" + id + "'"));
    }

    /**
     * Test DELETE with error saving to db
     *
     * @throws Exception throwing exception
     * @verifies should return status().isNotFound() and
     * jsonPath("$.success").value(Boolean.FALSE)
     */
    @Test
    @WithMockUser
    public void testDeleteDataErrorSaving() throws Exception {
        Integer id = 111;
        Barn responseDummy = BarnDummy.barnObject();
        given(barnService.findById(Mockito.anyInt())).willReturn(responseDummy);

        responseDummy.setDeleted(Boolean.TRUE);
        given(barnService.save(Mockito.any(Barn.class))).willThrow(new RuntimeException("Error during save to db"));

        mvc.perform(delete(this.barnUrl + "/" + id).contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").exists()).andExpect(jsonPath("$.success").isBoolean())
                .andExpect(jsonPath("$.success").value(Boolean.FALSE)).andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.message").value("Error during save to db"));
    }

    /**
     * Test put/update by id to update barn data
     *
     * @throws Exception throwing exception
     * @verifies should return status().isOk() and jsonPath("$.id").exists()
     */
    @Test
    @WithMockUser
    public void testPutByIdFound() throws Exception {
        Barn barnDummy = BarnDummy.barnObject();

        given(barnService.findById(Mockito.anyInt())).willReturn(barnDummy);
        given(barnService.save(Mockito.any(Barn.class))).willReturn(barnDummy);

        RequestBuilder httpRequestBuilder = put(this.barnUrl + "/" + barnDummy.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(BarnDummy.barnRequest()));

        mvc.perform(httpRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(barnDummy.getId()));
    }

    /**
     * Test put/update by id to update barn data
     *
     * @throws Exception throwing exception
     * @verifies should return status().isNotFound() and hasSize(0)
     */
    @Test
    @WithMockUser
    public void testPutByIdNotFound() throws Exception {

        Barn barnDummy = BarnDummy.barnObject();
        given(barnService.findById(Mockito.anyInt())).willThrow(new ResourceNotFoundException("Data", "id", 111));

        when(barnService.save(Mockito.any(Barn.class))).thenReturn(barnDummy);

        RequestBuilder httpRequestBuilder = put(this.barnUrl + "/111") // + barnDummy.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(BarnDummy.barnRequest()));

        mvc.perform(httpRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.timestamp").exists())
                .andExpect(jsonPath("$.timestamp").isString())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").isBoolean())
                .andExpect(jsonPath("$.success").value(Boolean.FALSE))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.message").value("Data not found with id : '111'"));
    }

    /**
     * Test POST endpoint to create new data
     *
     * @throws Exception throwing exception
     * @verifies should return status().isCreated()
     */
    @Test
    @WithMockUser
    public void testPostNewDataWithSuccessfullyCreate() throws Exception {
        given(barnService.save(Mockito.any(Barn.class))).willReturn(BarnDummy.barnObject());

        RequestBuilder httpRequestBuilder = post(this.barnUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(BarnDummy.barnRequest()));

        mvc.perform(httpRequestBuilder).andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(BarnDummy.barnObject().getId()));
    }

    /**
     * Test POST endpoint with not successfully save because of error
     *
     * @throws Exception throwing exception
     * @verifies should return status().isNotFound() &
     * jsonPath("$.success").value(Boolean.FALSE)
     */
    @Test
    @WithMockUser
    public void testPostNewDataWithNotSuccessfullyCreate() throws Exception {
        String error = "There is an error durring executing save";
        given(barnService.save(Mockito.any(Barn.class))).willThrow(new RuntimeException(error));

        RequestBuilder httpRequestBuilder = post(this.barnUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(BarnDummy.barnRequest()));

        mvc.perform(httpRequestBuilder).andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").isBoolean())
                .andExpect(jsonPath("$.success").value(Boolean.FALSE));
    }
}
