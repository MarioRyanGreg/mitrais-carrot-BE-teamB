package com.mitrais.carrot.config.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * filtering jwt auth
 *
 * @author Febri
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	/**
	 * JwtTokenProvider variable
	 */
        @Autowired
	private JwtTokenProvider tokenProvider;

	/**
	 * CustomUserDetailsService variable
	 */
        @Autowired
	private CustomUserDetailsService customUserDetailsService;

	/**
	 * logger variable to log error using logger factory
	 */
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    /**
     * override doFilterInternal method to handling custom filter
     *
     * @param request HttpServletRequest variable dependency injection
     * @param response HttpServletResponse variable dependency injection
     * @param filterChain FilterChain variable dependency injection
     * @throws ServletException throwing ServletException if there is ServletException error catch
     * @throws IOException throwing IOException if there is IOException error catch
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                Long userId = tokenProvider.getUserIdFromJWT(jwt);

                /*
                    Note that you could also encode the user's username and roles inside JWT claims
                    and create the UserDetails object by parsing those claims from the JWT.
                    That would avoid the following database hit. It's completely up to you.
                 */
                UserDetails userDetails = customUserDetailsService.loadUserById(userId);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    /**
     * get jwt token from request header
     *
     * @param request HttpServletRequest variable dependency injection
     * @return String of jwt token
     */
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

}
