package com.mitrais.carrot.controllers;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitrais.carrot.config.SecurityConfig;
import com.mitrais.carrot.dummy.SharingTypeDummy;
import com.mitrais.carrot.models.ShareType;
import com.mitrais.carrot.services.SharingTypeService;
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
public class SharingTypeControllerTest {

	/**
	 * get base url from config application.properties
	 */
	@Value("${spring.data.rest.basePath}")
	private String baseUrl;

	/**
	 * adding role base url
	 */
	private String shareTypeUrl;

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
	public SharingTypeService shareTypeService;

	/**
	 * to intialize default variable value
	 */
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
		this.shareTypeUrl = this.baseUrl + "/sharing-types";
	}

	/**
	 * should return 401 unauthorized becuse there is no JWT bearer token test get
	 * all endpoint
	 *
	 * @throws Exception
	 *             throwing exception
	 * @verifies should return status().isUnauthorized()
	 */
	@Test
	@WithAnonymousUser
	public void testShouldGetUnauthorizedGetRequest() throws Exception {
		mvc.perform(get(this.shareTypeUrl)).andExpect(status().isUnauthorized());
	}

	/**
	 * should return 401 unauthorized becuse there is no JWT bearer token test post
	 * all endpoint
	 *
	 * @throws Exception
	 * @verifies should return status().isUnauthorized()
	 */
	@Test
	@WithAnonymousUser
	public void testShouldGetUnauthorizedWithoutJWTTokenPostRequest() throws Exception {
		mvc.perform(post(this.shareTypeUrl)).andExpect(status().isUnauthorized());
	}

	/**
	 * should return 401 unauthorized becuse there is no JWT bearer token test post
	 * all endpoint
	 *
	 * @throws Exception
	 * @verifies should return status().isUnauthorized()
	 */
	@Test
	@WithAnonymousUser
	public void testShouldGetUnauthorizedWithoutJWTTokenPutRequest() throws Exception {
		mvc.perform(put(this.shareTypeUrl + "/" + 1133)).andExpect(status().isUnauthorized());
	}

	/**
	 * should return 401 unauthorized becuse there is no JWT bearer token test post
	 * all endpoint
	 *
	 * @throws Exception
	 * @verifies should return status().isUnauthorized()
	 */
	@Test
	@WithAnonymousUser
	public void testShouldGetUnauthorizedWithoutJWTTokenDeleteRequest() throws Exception {
		mvc.perform(delete(this.shareTypeUrl + "/" + 1133)).andExpect(status().isUnauthorized());
	}

	/**
     * test fetch all data
     *
     * @throws Exception throwing exception
     * @verifies should return status().isOk() and hasSize(1)
     */
    @Test
    @WithMockUser
    public void testFetchAllDataShouldReturnCorrectly() throws Exception {
    	given(shareTypeService.findAll()).willReturn(SharingTypeDummy.listOfShareTypes());
        mvc.perform(get(this.shareTypeUrl).contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[1].sharingType").exists())
                .andExpect(jsonPath("$[3].carrot").exists());
    }

    /**
     * test fetch all data
     *
     * @throws Exception throwing exception
     * @verifies should return status().isOk() and hasSize(0)
     */
    @Test
    @WithMockUser
    public void testFetchAllEmptyButNoError() throws Exception {
    	given(shareTypeService.findAll()).willReturn(new ArrayList<>());
        mvc.perform(get(this.shareTypeUrl).contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    /**
     * test fetch all data with exception
     *
     * @throws Exception throwing exception
     * @verifies should return status().isNotFound() and hasSize(0)
     */
    @Test
    @WithMockUser
    public void testFetchAllWithException() throws Exception {
    	given(shareTypeService.findAll()).willThrow(new RuntimeException("Error Coffee Not Found"));
        mvc.perform(get(this.shareTypeUrl).contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").exists())
                .andExpect(jsonPath("$.success").isBoolean())
                .andExpect(jsonPath("$.success").value(Boolean.FALSE))
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isString())
                .andExpect(jsonPath("$.message").value("Error Coffee Not Found"));
    }


    /**
     * test fetch data with ok
     *
     * @throws Exception throwing exception
     * @verifies should return status().isOk() and hasSize(0)
     */
    @Test
    @WithMockUser
    public void testFetchByIdShouldBeSuccessfully() throws Exception {
    	ShareType st = SharingTypeDummy.shareTypeObject();
    	given(shareTypeService.findById(Mockito.anyInt())).willReturn(st);
		mvc.perform(get(this.shareTypeUrl + "/" + st.getId()).contentType(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(st.getId()))
                .andExpect(jsonPath("$.sharingType").exists())
                .andExpect(jsonPath("$.sharingType").isString())
                .andExpect(jsonPath("$.sharingType").value(st.getSharingType()));
    }

	/**
	 * test fetch data by pk with exception
	 *
	 * @throws Exception throwing exception
	 * @verifies should return status().isNotFound() and jsonPath("$.success").value(Boolean.FALSE)
	 */
	@Test
	@WithMockUser
	public void testFetchByIdWithException() throws Exception {
		ShareType st = SharingTypeDummy.shareTypeObject();
		given(shareTypeService.findById(Mockito.anyInt())).willThrow(new ResourceNotFoundException("Data", "id", st.getId()));

		mvc.perform(get(this.shareTypeUrl + "/" + st.getId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.success").exists())
				.andExpect(jsonPath("$.success").isBoolean())
				.andExpect(jsonPath("$.success").value(Boolean.FALSE))
				.andExpect(jsonPath("$.message").exists())
				.andExpect(jsonPath("$.message").isString())
				.andExpect(jsonPath("$.message").value("Data not found with id : '" + st.getId() + "'"));
	}

	/**
	 * test post
	 *
	 * @throws Exception throwing exception
	 * @verifies should return status().isCreated() and hasSize(0)
	 */
	@Test
	@WithMockUser
	public void testPostDataWithSuccessfulyScenario() throws Exception {
		ShareType st = SharingTypeDummy.shareTypeObject();
		given(shareTypeService.save(Mockito.any(ShareType.class))).willReturn(st);

		RequestBuilder httpRequestBuilder = post(this.shareTypeUrl).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(st));

		mvc.perform(httpRequestBuilder).andExpect(status().isCreated())
				.andExpect(jsonPath("$.success").exists())
				.andExpect(jsonPath("$.success").isBoolean())
				.andExpect(jsonPath("$.success").value(Boolean.TRUE));
	}

	/**
	 * test post
	 *
	 * @throws Exception throwing exception
	 * @verifies should return status().isNotFound() and hasSize(0)
	 */
	@Test
	@WithMockUser
	public void testPostDataWithExceptionScenario() throws Exception {
		given(shareTypeService.save(Mockito.any(ShareType.class))).willThrow(new RuntimeException("Error chicken not found"));

		RequestBuilder httpRequestBuilder = post(this.shareTypeUrl).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(SharingTypeDummy.shareTypeObject()));

		mvc.perform(httpRequestBuilder).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.success").exists())
				.andExpect(jsonPath("$.success").isBoolean())
				.andExpect(jsonPath("$.success").value(Boolean.FALSE));
	}

	/**
	 * test put
	 *
	 * @throws Exception throwing exception
	 * @verifies should return status().isOk() and jsonPath("$.success").value(Boolean.TRUE)
	 */
	@Test
	@WithMockUser
	public void testPutDataWithSuccessfulyScenario() throws Exception {
		ShareType st = SharingTypeDummy.shareTypeObject();
		given(shareTypeService.update(Mockito.anyInt(), Mockito.any(ShareType.class))).willReturn(st);

		RequestBuilder httpRequestBuilder = put(this.shareTypeUrl + "/" + st.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(st));

		mvc.perform(httpRequestBuilder).andExpect(status().isOk())
				.andExpect(jsonPath("$.success").exists())
				.andExpect(jsonPath("$.success").isBoolean())
				.andExpect(jsonPath("$.success").value(Boolean.TRUE));
	}

	/**
	 * test put
	 *
	 * @throws Exception throwing exception
	 * @verifies should return status().isNotFound() and hasSize(0)
	 */
	@Test
	@WithMockUser
	public void testPutDataWithExceptionScenario() throws Exception {
		ShareType st = SharingTypeDummy.shareTypeObject();
		given(shareTypeService.update(Mockito.anyInt(), Mockito.any(ShareType.class))).willThrow(new ResourceNotFoundException("Data", "id", st.getId()));

		RequestBuilder httpRequestBuilder = put(this.shareTypeUrl + "/" + st.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(st));

		mvc.perform(httpRequestBuilder).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.success").exists())
				.andExpect(jsonPath("$.success").isBoolean())
				.andExpect(jsonPath("$.success").value(Boolean.FALSE));
	}

	/**
	 * test DELETE
	 *
	 * @throws Exception throwing exception
	 * @verifies should return status().isOk() and hasSize(0)
	 */
	@Test
	@WithMockUser
	public void testDeleteDataWithSuccessfulyScenario() throws Exception {
		ShareType st = SharingTypeDummy.shareTypeObject();
		given(shareTypeService.delete(Mockito.anyInt())).willReturn(st);

		mvc.perform(delete(this.shareTypeUrl + "/" + st.getId())).andExpect(status().isOk())
				.andExpect(jsonPath("$.success").exists())
				.andExpect(jsonPath("$.success").isBoolean())
				.andExpect(jsonPath("$.success").value(Boolean.TRUE));
	}

	/**
	 * test DELETE
	 *
	 * @throws Exception throwing exception
	 * @verifies should return status().isCreated() and hasSize(0)
	 */
	@Test
	@WithMockUser
	public void testDeleteDataWithExceptionScenario() throws Exception {
		ShareType st = SharingTypeDummy.shareTypeObject();
		given(shareTypeService.delete(Mockito.anyInt())).willThrow(new ResourceNotFoundException("Data", "id", st.getId()));

		mvc.perform(delete(this.shareTypeUrl + "/" + st.getId())).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.success").exists())
				.andExpect(jsonPath("$.success").isBoolean())
				.andExpect(jsonPath("$.success").value(Boolean.FALSE));
	}
}
