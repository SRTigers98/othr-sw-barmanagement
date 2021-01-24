package de.othr.sw.benjamineder.barmanagement.application.rest.othauth;

import lombok.EqualsAndHashCode;
import org.springframework.security.core.AuthenticationException;

@EqualsAndHashCode(callSuper = true)
public class OthAuthException extends AuthenticationException {

  public OthAuthException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
