package com.mitrais.carrot.config.swagger;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger configuration
 * 
 * @author Febri
 * @author Medianto
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	/**
	 * instance Docket object to set api swagger configuration
	 * 
	 * @return Docket object bean
	 */
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build().securitySchemes(Arrays.asList(apiKey()));

	}

	/**
	 * instance new ApiKey object with the authorization that will be use
	 * 
	 * @return ApiKey object 
	 */
	private ApiKey apiKey() {
		return new ApiKey("authkey", "Authorization", "header");
	}

	/**
	 * security configuration for swagger
	 * 
	 * @return SecurityConfiguration object
	 */
	@Bean
	SecurityConfiguration security() {
		return new SecurityConfiguration(null, null, null, // realm Needed for authenticate button to work
				"mitrais-carrot", // appName Needed for authenticate button to work
				" ", // apiKeyValue
				ApiKeyVehicle.HEADER, "Authorization", // apiKeyName
				null);
	}
}
