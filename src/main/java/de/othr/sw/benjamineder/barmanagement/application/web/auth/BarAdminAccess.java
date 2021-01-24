package de.othr.sw.benjamineder.barmanagement.application.web.auth;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@PreAuthorize("hasRole('BAR_ADMIN')")
public @interface BarAdminAccess {
  // no attributes required
}
