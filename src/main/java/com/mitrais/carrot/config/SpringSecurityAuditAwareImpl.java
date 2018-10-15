package com.mitrais.carrot.config;

import com.mitrais.carrot.config.jwt.UserPrincipal;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * spring security audit implements AuditorAware class
 *
 * @author Febri
 */
class SpringSecurityAuditAwareImpl implements AuditorAware<Long> {

	/**
	 * get current user that already signin into app
	 * 
	 * @return Optional object that contain id of user with long type varible
	 */
    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        return Optional.ofNullable(userPrincipal.getId());
    }
}
