package de.othr.sw.benjamineder.barmanagement.application.auth.provider;

import de.othr.sw.benjamineder.barmanagement.application.rest.othauth.OthAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class AuthProvider implements AuthenticationProvider {

  private final OthAuthService othAuthService;

  @Autowired
  public AuthProvider(OthAuthService othAuthService) {
    this.othAuthService = othAuthService;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    var username = authentication.getPrincipal().toString();
    var password = authentication.getCredentials().toString();
    var authorities = othAuthService.authenticate(username, password);
    var user = new User(username, password, authorities);
    return new UsernamePasswordAuthenticationToken(user, password, authorities);
  }

  @Override
  public boolean supports(Class<?> tokenClass) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(tokenClass);
  }
}
