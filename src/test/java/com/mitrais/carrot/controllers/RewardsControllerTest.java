package com.mitrais.carrot.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitrais.carrot.config.SecurityConfig;
import com.mitrais.carrot.dummy.RewardDummy;
import com.mitrais.carrot.models.Rewards;
import com.mitrais.carrot.repositories.RewardsRepository;
import com.mitrais.carrot.services.RewardService;
import com.mitrais.carrot.validation.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.Optional;

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
 * Rewards Controller test
 *
 * @author Febri_MW251@mitrais.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Import(SecurityConfig.class)
public class RewardsControllerTest {

    /**
     * get base url from config application.properties
     */
    @Value("${spring.data.rest.basePath}")
    private String baseUrl;

    /**
     * adding reward base url
     */
    private String rewardUrl;

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
     * Barn Service mock
     */
    @MockBean
    public RewardService rewardService;

    /**
     * Barn Repository mock
     */
    @MockBean
    public RewardsRepository rewardRepositoryMock;

    /**
     * setup is use for intialize default variable value
     */
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
        this.rewardUrl = this.baseUrl + "/rewards";
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
        mvc.perform(get(this.rewardUrl)).andExpect(status().isUnauthorized());
    }

    /**
     * should return 401 unauthorized becuse there is no JWT bearer token test
     * post all endpoint
     *
     * @throws Exception throwing exception if there is error
     * @verifies should return status().isUnauthorized()
     */
    @Test
    @WithAnonymousUser
    public void testPostShouldGetUnauthorizedWithoutJWTTokenPostRequest() throws Exception {
        mvc.perform(post(this.rewardUrl)).andExpect(status().isUnauthorized());
    }

    /**
     * should return 401 unauthorized becuse there is no JWT bearer token test
     * put endpoint
     *
     * @throws Exception throwing exception if there is error
     * @verifies should return status().isUnauthorized()
     */
    @Test
    @WithAnonymousUser
    public void testPutShouldGetUnauthorizedWithoutJWTTokenPostRequest() throws Exception {
        mvc.perform(delete(this.rewardUrl + "/" + 212)).andExpect(status().isUnauthorized());
    }

    /**
     * should return 401 unauthorized becuse there is no JWT bearer token test
     * delete endpoint
     *
     * @throws Exception throwing exception if there is error
     * @verifies should return status().isUnauthorized()
     */
    @Test
    @WithAnonymousUser
    public void testDeleteShouldGetUnauthorizedWithoutJWTTokenPostRequest() throws Exception {
        mvc.perform(delete(this.rewardUrl + "/" + 212)).andExpect(status().isUnauthorized());
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

        given(rewardService.all()).willReturn(RewardDummy.listOfRewards());

        mvc.perform(get(this.rewardUrl).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id").value(RewardDummy.rewardObject().getId()))
                .andExpect(jsonPath("$[0].typeName").value(RewardDummy.rewardObject().getTypeName()));
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

        given(rewardService.all()).willReturn(new ArrayList<>());

        mvc.perform(get(this.rewardUrl).contentType(MediaType.APPLICATION_JSON))
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
        given(rewardService.all()).willThrow(new RuntimeException(errorEx));

        mvc.perform(get(this.rewardUrl).contentType(MediaType.APPLICATION_JSON))
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
     * Test POST endpoint to create new data
     *
     * @throws Exception throwing exception
     * @verifies should return status().isCreated()
     */
    @Test
    @WithMockUser
    public void testPostNewDataWithSuccessfullyCreate() throws Exception {
        Rewards rewardExpected = RewardDummy.rewardObject();

        given(rewardService.save(Mockito.any(Rewards.class))).willReturn(rewardExpected);

        RequestBuilder httpRequestBuilder = post(this.rewardUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(RewardDummy.rewardRequestBody()));

        mvc.perform(httpRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(rewardExpected.getId()));
    }

    /**
     * Test POST endpoint to create new data
     *
     * @throws Exception throwing exception
     * @verifies should return status().isNotFound() & jsonPath("$.success").value(Boolean.FALSE)
     */
    @Test
    @WithMockUser
    public void testPostNewDataWithThrowingException() throws Exception {
        given(rewardService.save(Mockito.any(Rewards.class))).willThrow(new RuntimeException("error sri"));

        RequestBuilder httpRequestBuilder = post(this.rewardUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(RewardDummy.rewardRequestBody()));

        mvc.perform(httpRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").isBoolean())
                .andExpect(jsonPath("$.success").value(Boolean.FALSE))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.message").value("error sri"));
    }

    /**
     * test fetch data by pk with successfully
     *
     * @throws Exception throwing exception
     * @verifies should return status().isOk() and (jsonPath("$.id").exists()
     */
    @Test
    @WithMockUser
    public void testFetchByIdShouldBeSuccessfully() throws Exception {
        Rewards reward = RewardDummy.rewardObject();
        given(rewardService.findById(Mockito.anyInt())).willReturn(Optional.of(reward));

        mvc.perform(get(this.rewardUrl + "/" + reward.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(reward.getId()))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.typeName").exists())
                .andExpect(jsonPath("$.typeName").isString())
                .andExpect(jsonPath("$.typeName").value(reward.getTypeName()));
    }

    /**
     * Test fetch data by pk returning empty value
     *
     * @throws Exception throwing exception
     * @verifies should return status().isOk() and hasSize(0)
     */
    @Test
    @WithMockUser
    public void testFetchByIdReturnEmpty() throws Exception {

        Rewards reward = RewardDummy.rewardObject();
        given(rewardService.findById(Mockito.anyInt())).willReturn(Optional.ofNullable(new Rewards()));

        mvc.perform(get(this.rewardUrl + "/" + reward.getId()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    /**
     * Test fetch data by pk throwing exception
     *
     * @throws Exception throwing exception
     * @verifies should return status().isNotFound() & jsonPath("$.success").value(Boolean.FALSE)
     */
    @Test
    @WithMockUser
    public void testFetchByIdThrowingException() throws Exception {
        Integer pk = 111;
        given(rewardService.findById(Mockito.anyInt())).willThrow(new ResourceNotFoundException("Data", "id", pk));

        mvc.perform(get(this.rewardUrl + "/" + pk).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").isBoolean())
                .andExpect(jsonPath("$.success").value(Boolean.FALSE))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.message").value("Data not found with id : '" + pk + "'"));
    }

    /**
     * Test PUT endpoint to update data
     *
     * @throws Exception throwing exception
     * @verifies should return status().isOk()
     */
    @Test
    @WithMockUser
    public void testPutDataWithSuccessfully() throws Exception {
        Rewards rewardExpected = RewardDummy.rewardObject();

        given(rewardService.findById(Mockito.anyInt())).willReturn(Optional.of(rewardExpected));
        given(rewardService.save(Mockito.any(Rewards.class))).willReturn(rewardExpected);

        RequestBuilder httpRequestBuilder = put(this.rewardUrl + "/" + rewardExpected.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(RewardDummy.rewardRequestBody()));

        mvc.perform(httpRequestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(rewardExpected.getId()));
    }

    /**
     * Test PUT endpoint to update data
     *
     * @throws Exception throwing exception
     * @verifies should return status().isNotFound() & jsonPath("$.success").value(Boolean.FALSE)
     */
    @Test
    @WithMockUser
    public void testPutDataNotFound() throws Exception {
        Rewards rewardExpected = RewardDummy.rewardObject();

        given(rewardService.findById(Mockito.anyInt())).willThrow(new ResourceNotFoundException("Data", "id", rewardExpected.getId()));
        given(rewardService.save(Mockito.any(Rewards.class))).willReturn(rewardExpected);

        RequestBuilder httpRequestBuilder = put(this.rewardUrl + "/" + rewardExpected.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(RewardDummy.rewardRequestBody()));

        mvc.perform(httpRequestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").isBoolean())
                .andExpect(jsonPath("$.success").value(Boolean.FALSE))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.message").value("Data not found with id : '" + rewardExpected.getId() + "'"));
    }

    /**
     * Test PUT endpoint to update data
     *
     * @throws Exception throwing exception
     * @verifies should return status().isNotFound()
     */
    @Test
    @WithMockUser
    public void testPutErrorFound() throws Exception {
        Rewards rewardExpected = RewardDummy.rewardObject();

        given(rewardService.findById(Mockito.anyInt())).willReturn(Optional.of(rewardExpected));
        given(rewardService.update(Mockito.any(Rewards.class), Mockito.any(Rewards.class))).willThrow(new RuntimeException("error"));

        RequestBuilder httpRequestBuilder = put(this.rewardUrl + "/" + rewardExpected.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(RewardDummy.rewardRequestBody()));

        mvc.perform(httpRequestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").isBoolean())
                .andExpect(jsonPath("$.success").value(Boolean.FALSE))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.message").value("error"));
    }

    /**
     * Test DELETE endpoint to update data
     *
     * @throws Exception throwing exception
     * @verifies should return status().isOk()
     */
    @Test
    @WithMockUser
    public void testDeleteDataWithSuccessfully() throws Exception {
        Rewards rewardExpected = RewardDummy.rewardObject();

        given(rewardService.findById(Mockito.anyInt())).willReturn(Optional.of(rewardExpected));
        given(rewardService.save(Mockito.any(Rewards.class))).willReturn(rewardExpected);

        mvc.perform(delete(this.rewardUrl + "/" + rewardExpected.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").isBoolean())
                .andExpect(jsonPath("$.success").value(Boolean.TRUE))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.message").value("Data deleted successfully"));
    }

    /**
     * Test DELETE endpoint to update data
     *
     * @throws Exception throwing exception
     * @verifies should return status().isNotFound()
     */
    @Test
    @WithMockUser
    public void testDeleteDataNotFound() throws Exception {
        Rewards rewardExpected = RewardDummy.rewardObject();

        given(rewardService.findById(Mockito.anyInt())).willThrow(new ResourceNotFoundException("Data", "id", rewardExpected.getId()));
        given(rewardService.save(Mockito.any(Rewards.class))).willReturn(rewardExpected);

        mvc.perform(delete(this.rewardUrl + "/" + rewardExpected.getId()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").isBoolean())
                .andExpect(jsonPath("$.success").value(Boolean.FALSE))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.message").value("Data not found with id : '" + rewardExpected.getId() + "'"));
    }

    /**
     * Test DELETE endpoint to update data
     *
     * @throws Exception throwing exception
     * @verifies should return status().isNotFound()
     */
    @Test
    @WithMockUser
    public void testDeleteErrorFound() throws Exception {
        Rewards rewardExpected = RewardDummy.rewardObject();

        given(rewardService.findById(Mockito.anyInt())).willReturn(Optional.of(rewardExpected));
        given(rewardService.delete(Mockito.any(Rewards.class))).willThrow(new RuntimeException("error"));

        mvc.perform(delete(this.rewardUrl + "/" + rewardExpected.getId()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").isBoolean())
                .andExpect(jsonPath("$.success").value(Boolean.FALSE))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.message").value("error"));
    }
}
