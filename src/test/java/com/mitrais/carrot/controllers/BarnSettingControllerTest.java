package com.mitrais.carrot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitrais.carrot.config.SecurityConfig;
import com.mitrais.carrot.dummy.BarnDummy;
import com.mitrais.carrot.models.BarnSetting;
import com.mitrais.carrot.repositories.BarnSettingRepository;
import com.mitrais.carrot.services.BarnSettingService;
import com.mitrais.carrot.validation.exception.ResourceNotFoundException;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
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
 * BarnSetting Controller test
 *
 * @author Febri_MW251@mitrais.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(SecurityConfig.class)
public class BarnSettingControllerTest {

    /**
     * get base url from config application.properties
     */
    @Value("${spring.data.rest.basePath}")
    private String baseUrl;

    /**
     * adding reward base url
     */
    private String barnSettingUrl;

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
     * BarnSetting Service mock
     */
    @MockBean
    public BarnSettingService barnSettingService;

    /**
     * BarnSetting Repository mock
     */
    @MockBean
    public BarnSettingRepository barnSettingRepositoryMock;

    /**
     * setup is use for intialize default variable value
     */
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        this.barnSettingUrl = this.baseUrl + "/barns-settings";
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
    public void testGetShouldGetUnauthorizedGetRequest() throws Exception {
        mvc.perform(get(this.barnSettingUrl)).andExpect(status().isUnauthorized());
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
        mvc.perform(post(this.barnSettingUrl)).andExpect(status().isUnauthorized());
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
        mvc.perform(delete(this.barnSettingUrl + "/" + 212)).andExpect(status().isUnauthorized());
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
        mvc.perform(delete(this.barnSettingUrl + "/" + 212)).andExpect(status().isUnauthorized());
    }

    /**
     * test fetch all data with successfully
     *
     * @throws Exception throwing exception
     * @verifies should return status().isOk() and hasSize(1)
     */
    @Test
    @WithMockUser
    public void testFetchAllShouldBeSuccessfully() throws Exception {

        given(barnSettingService.findAll()).willReturn(BarnDummy.listOfBarnSettings());

        mvc.perform(get(this.barnSettingUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value(BarnDummy.barnSettingObject().getName()));
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

        given(barnSettingService.findAll()).willReturn(new ArrayList<>());

        mvc.perform(get(this.barnSettingUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    /**
     * Test fetch all data returning empty value
     *
     * @throws Exception throwing exception
     * @verifies should return status().isOk() and hasSize(0)
     */
    @Test
    @WithMockUser
    public void testFetchAllThrowingException() throws Exception {

        String errorEx = "Something error ndan";
        given(barnSettingService.findAll()).willThrow(new RuntimeException(errorEx));

        mvc.perform(get(this.barnSettingUrl).contentType(MediaType.APPLICATION_JSON))
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
     * test fetch by id data with successfully
     *
     * @throws Exception throwing exception
     * @verifies should return status().isOk() and jsonPath("$.id").exists()
     */
    @Test
    @WithMockUser
    public void testFetchByIdShouldBeSuccessfully() throws Exception {

        BarnSetting bs = BarnDummy.barnSettingObject();
        given(barnSettingService.findById(Mockito.anyInt())).willReturn(bs);

        mvc.perform(get(this.barnSettingUrl + "/" + bs.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(bs.getId()))
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.name").value(bs.getName()));
    }

    /**
     * Test fetch by id data returning empty value
     *
     * @throws Exception throwing exception
     * @verifies should return status().isOk() and hasSize(0)
     */
    @Test
    @WithMockUser
    public void testFetchByIdReturnEmpty() throws Exception {
        given(barnSettingService.findById(Mockito.anyInt())).willReturn(new BarnSetting());
        mvc.perform(get(this.barnSettingUrl + "/" + 111).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    /**
     * Test fetch by id data returning empty value
     *
     * @throws Exception throwing exception
     * @verifies should return status().isNotFound() and
     * jsonPath("$.success").exists()
     */
    @Test
    @WithMockUser
    public void testFetchByIdThrowingException() throws Exception {
        BarnSetting bs = BarnDummy.barnSettingObject();
        given(barnSettingService.findById(Mockito.anyInt())).willThrow(new ResourceNotFoundException("Data", "id", bs.getId()));

        mvc.perform(get(this.barnSettingUrl + "/" + bs.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").isBoolean())
                .andExpect(jsonPath("$.success").value(Boolean.FALSE))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.message").value("Data not found with id : '" + bs.getId() + "'"));
    }

    /**
     * test post data with successfully
     *
     * @throws Exception throwing exception
     * @verifies should return status().isCreated() and
     * jsonPath("$.success").exists()
     */
    @Test
    @WithMockUser
    public void testPostCreateDataShouldBeSuccessfully() throws Exception {
        BarnSetting bs = BarnDummy.barnSettingObject();
        given(barnSettingService.save(Mockito.any(BarnSetting.class))).willReturn(bs);

        RequestBuilder httpRequestBuilder = post(this.barnSettingUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(BarnDummy.barnSettingRequest()));

        mvc.perform(httpRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").isBoolean())
                .andExpect(jsonPath("$.success").value(Boolean.TRUE))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.message").value("Barn setting save successfully"));
    }

    /**
     * Test post data throwing error
     *
     * @throws Exception throwing exception
     * @verifies should return status().isNotFound() and
     * jsonPath("$.success").value(Boolean.FALSE)
     */
    @Test
    @WithMockUser
    public void testPostCreateDataReturnEmpty() throws Exception {
        given(barnSettingService.save(Mockito.any(BarnSetting.class))).willThrow(new RuntimeException("Error"));

        RequestBuilder httpRequestBuilder = post(this.barnSettingUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(BarnDummy.barnSettingRequest()));

        mvc.perform(httpRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").isBoolean())
                .andExpect(jsonPath("$.success").value(Boolean.FALSE))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.message").value("Error"));
    }

    /**
     * test put data with successfully
     *
     * @throws Exception throwing exception
     * @verifies should return status().isOk() and
     * jsonPath("$.success").exists()
     */
    @Test
    @WithMockUser
    public void testPutDataShouldBeSuccessfully() throws Exception {
        BarnSetting bs = BarnDummy.barnSettingObject();
        given(barnSettingService.findById(Mockito.anyInt())).willReturn(bs);
        given(barnSettingService.save(Mockito.any(BarnSetting.class))).willReturn(bs);

        RequestBuilder httpRequestBuilder = put(this.barnSettingUrl + "/" + bs.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(BarnDummy.barnSettingRequest()));

        mvc.perform(httpRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(bs.getId()))
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.name").value(BarnDummy.barnSettingRequest().getName()));
    }

    /**
     * Test put data throwing error
     *
     * @throws Exception throwing exception
     * @verifies should return status().isNotFound() and
     * jsonPath("$.success").value(Boolean.FALSE)
     */
    @Test
    @WithMockUser
    public void testPutThrowException() throws Exception {
        given(barnSettingService.findById(Mockito.anyInt())).willThrow(new ResourceNotFoundException("Data", "id", 1213213));
        given(barnSettingService.save(Mockito.any(BarnSetting.class))).willThrow(new RuntimeException("Error"));

        RequestBuilder httpRequestBuilder = put(this.barnSettingUrl + "/" + 1213213)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(BarnDummy.barnSettingRequest()));

        mvc.perform(httpRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").isBoolean())
                .andExpect(jsonPath("$.success").value(Boolean.FALSE))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isString());
    }

    /**
     * test delete data with successfully
     *
     * @throws Exception throwing exception
     * @verifies should return status().isOk() and
     * jsonPath("$.success").exists()
     */
    @Test
    @WithMockUser
    public void testDeleteShouldBeSuccessfully() throws Exception {
        BarnSetting bs = BarnDummy.barnSettingObject();
        given(barnSettingService.findById(Mockito.anyInt())).willReturn(bs);
        given(barnSettingService.save(Mockito.any(BarnSetting.class))).willReturn(bs);

        mvc.perform(delete(this.barnSettingUrl + "/" + bs.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").isBoolean())
                .andExpect(jsonPath("$.success").value(Boolean.TRUE))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isString());
    }

    /**
     * Test delete data throwing error
     *
     * @throws Exception throwing exception
     * @verifies should return status().isNotFound() and
     * jsonPath("$.success").value(Boolean.FALSE)
     */
    @Test
    @WithMockUser
    public void testDeleteThrowException() throws Exception {
        given(barnSettingService.findById(Mockito.anyInt())).willThrow(new ResourceNotFoundException("Data", "id", 1213213));
        given(barnSettingService.save(Mockito.any(BarnSetting.class))).willThrow(new RuntimeException("Error"));

        mvc.perform(delete(this.barnSettingUrl + "/" + 1213213))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").isBoolean())
                .andExpect(jsonPath("$.success").value(Boolean.FALSE))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isString());
    }
}
