package com.mitrais.carrot.config.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * handling error if token us not authorized
 * 
 * @author Febri
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	/**
	 * logger variable
	 */
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

	/**
	 * override commence method to adding custom servlet error response
	 * 
	 * @param httpServletRequest  HttpServletRequest class dependency
	 * @param httpServletResponse httpServletResponse class dependency
     * @param authenticationException AuthenticationException class dependency
	 * @throws IOException throwing IOException if there is catch IOException
	 * @throws ServletException throwing ServletException if there is catch ServletException
	 */
	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			AuthenticationException authenticationException) throws IOException, ServletException {
		logger.error("Responding with unauthorized error. Message - {}", authenticationException.getMessage());
		httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
				"Sorry, You're not authorized to access this resource.");
	}
}
