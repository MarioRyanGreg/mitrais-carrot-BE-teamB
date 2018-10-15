package com.mitrais.carrot.config;

import com.mitrais.carrot.config.jwt.CustomUserDetailsService;
import com.mitrais.carrot.config.jwt.JwtAuthenticationEntryPoint;
import com.mitrais.carrot.config.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security config for this app using JWT
 *
 * @author febri
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * this variable to store base of uri api
     */
	@Value("${spring.data.rest.basePath}")
    private String baseUriApi;

    /**
     * variable of CustomUserDetailsService service
     */
    private CustomUserDetailsService customUserDetailsService;

    /**
     * variable of  JWT authorize handler (JwtAuthenticationEntryPoint)
     */
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    /**
     * SecurityConfig constructor
     *
     * @param customUserDetailsService customUserDetailsService service
     * @param unauthorizedHandler unauthorizedHandler service
     */
    public SecurityConfig(@Autowired CustomUserDetailsService customUserDetailsService, @Autowired JwtAuthenticationEntryPoint unauthorizedHandler) {
        this.customUserDetailsService = customUserDetailsService;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    /**
     * set JwtAuthenticationFilter with Bean annotation
     * @return new instance JwtAuthenticationFilter
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    /**
     * set PasswordEncoder with Bean annotation
     * @return new instance PasswordEncoder object
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * manage bean
     *
     * @return AuthenticationManager object
     * @throws Exception throw exception if there is error
     */
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * override configure method to set user details service
     *
     * @param authenticationManagerBuilder AuthenticationManagerBuilder object
     * @throws Exception throw exception if there is error
     */
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * override configure method to set custom http security what endpoint
     * should be allow to access without authentication and not permit
     *
     * @param http HttpSecurity object
     * @throws Exception throwing exception if there is error
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js")
                .permitAll()
                .antMatchers(
                        this.baseUriApi + "/signin",
                        this.baseUriApi + "/signup",
                        this.baseUriApi + "/users/availability",
                        "/swagger-ui.html",
                        "/swagger-resources/configuration/security",
                        "/swagger-resources",
                        "/swagger-resources/configuration/ui",
                        "/v2/api-docs"
                )
                .permitAll()
                .anyRequest()
                .authenticated();

        // Add our custom JWT security filter
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
