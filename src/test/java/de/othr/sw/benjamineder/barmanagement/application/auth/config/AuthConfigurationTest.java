package de.othr.sw.benjamineder.barmanagement.application.auth.config;

import de.othr.sw.benjamineder.barmanagement.application.auth.provider.AuthProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthConfigurationTest {

  @InjectMocks
  private AuthConfiguration tested;
  @Mock
  private AuthProvider      authProvider;

  @Test
  void configureAuthenticationManagerBuilderTest() {
    var auth = mock(AuthenticationManagerBuilder.class);

    when(auth.authenticationProvider(authProvider)).thenReturn(auth);

    tested.configure(auth);

    verify(auth, times(1)).authenticationProvider(authProvider);
  }

  @Test
  void configureHttpSecurityTest() throws Exception {
    var http = new HttpSecurity(mock(ObjectPostProcessor.class), mock(AuthenticationManagerBuilder.class), Map.of());

    // Only test if the configuration method runs without errors
    tested.configure(http);
  }
}
