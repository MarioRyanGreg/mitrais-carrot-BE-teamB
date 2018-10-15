package com.mitrais.carrot.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;

import com.mitrais.carrot.config.jwt.UserPrincipal;
import com.mitrais.carrot.models.User;

/**
 * Helper class for mock authentication for testing controller/api
 *
 * @author Febri_MW251
 *
 */
public class CarrotAuthTestHelper {

    /**
     * build UserPrincipal object
     *
     * @return UserPrincipal default dummy object
     */
    public static UserPrincipal userPrincipalDefault() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return new UserPrincipal(1L, "admin", "admin", "admin@mitrais.com", "admin", authorities);
    }

    /**
     * mock Authorization and mock UserPrincipal to allow access in app
     *
     * @param user User object if null will use default UserPrincipal default object
     * @param isAuthenticated Boolean type that user can access this app or not
     */
    public static void letMeIn(User user, Boolean isAuthenticated) {
        UserPrincipal userPrincipal = null;
        if (user.equals(null)) {
            userPrincipal = userPrincipalDefault();
        } else {
            userPrincipal = UserPrincipal.create(user);
        }
        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(userPrincipal, null);
        testingAuthenticationToken.setAuthenticated(isAuthenticated);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);
    }
}
