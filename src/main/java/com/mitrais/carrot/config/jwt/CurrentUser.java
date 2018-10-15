package com.mitrais.carrot.config.jwt;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import java.lang.annotation.*;

/**
 * interface class CurrentUser that implement AuthenticationPrincipal annotation
 * 
 * @author Febri_MW251
 *
 */
@Target({ElementType.PARAMETER, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
public @interface CurrentUser {

}
