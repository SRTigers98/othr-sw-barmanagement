package de.othr.sw.benjamineder.barmanagement.application.auth.provider;

import de.othr.sw.benjamineder.barmanagement.application.rest.othauth.OthAuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthProviderTest {

  @InjectMocks
  private AuthProvider   tested;
  @Mock
  private OthAuthService othAuthService;

  @Test
  void authenticateTest() {
    var authentication = mock(Authentication.class);

    var principal = "ford.prefect";
    var credentials = "pan_galactic_gargle_blaster!42";
    var authority1 = mock(GrantedAuthority.class);
    var authority2 = mock(GrantedAuthority.class);
    Collection authoritiesCollection = List.of(authority1, authority2);

    when(authentication.getPrincipal()).thenReturn(principal);
    when(authentication.getCredentials()).thenReturn(credentials);
    when(othAuthService.authenticate(eq(principal), eq(credentials))).thenReturn(authoritiesCollection);

    var result = tested.authenticate(authentication);

    assertThat(result, is(notNullValue()));
    assertThat(result.getPrincipal(), is(notNullValue()));
    var resultPrincipal = (User) result.getPrincipal();
    assertThat(resultPrincipal.getUsername(), is(principal));
    assertThat(resultPrincipal.getPassword(), is(credentials));
    assertThat(resultPrincipal.getAuthorities(), hasItems(authority1, authority2));
    assertThat(result.getCredentials(), is(credentials));
    assertThat(result.getAuthorities(), hasItems(equalTo(authority1), equalTo(authority2)));
  }

  @Test
  void supportsTest() {
    var tokenClass = UsernamePasswordAuthenticationToken.class;

    var result = tested.supports(tokenClass);

    assertThat(result, is(true));
  }

  @Test
  void supportsNotTest() {
    var tokenClass = Object.class;

    var result = tested.supports(tokenClass);

    assertThat(result, is(false));
  }
}
