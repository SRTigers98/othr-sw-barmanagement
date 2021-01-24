package de.othr.sw.benjamineder.barmanagement.application.rest.othauth;

import de.othr.brs31213.sw.othauth.dto.requests.LoginRequest;
import de.othr.brs31213.sw.othauth.dto.responses.UserInformationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OthAuthServiceTest {

  private static final String API_KEY = "42";

  @InjectMocks
  private OthAuthService tested;
  @Mock
  private RestTemplate   restTemplate;

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(tested, "apiKey", API_KEY);
  }

  @Test
  void authenticateTest() {
    var username = "arthur.dent";
    var password = "marvin4ever";

    var loginRequestCaptor = ArgumentCaptor.forClass(LoginRequest.class);
    var userInformation = mock(UserInformationResponse.class);

    when(restTemplate.postForEntity(eq("/api/authenticate"), loginRequestCaptor.capture(), eq(UserInformationResponse.class)))
        .thenReturn(ResponseEntity.ok(userInformation));
    when(userInformation.getRoles()).thenReturn(List.of("USER", "ADMIN"));

    var result = tested.authenticate(username, password);

    assertThat(result, is(notNullValue()));
    assertThat(result.size(), is(2));
    assertThat(result, hasItems(equalTo(new SimpleGrantedAuthority("ROLE_USER")),
                                equalTo(new SimpleGrantedAuthority("ROLE_ADMIN"))));

    var loginRequest = loginRequestCaptor.getValue();
    assertThat(loginRequest, is(notNullValue()));
    assertThat(loginRequest.getUsername(), is(username));
    assertThat(loginRequest.getPassword(), is(password));
    assertThat(loginRequest.getServiceKey(), is(API_KEY));
  }

  @Test
  void authenticateAuthExceptionTest() {
    var username = "arthur.dent";
    var password = "marvin4ever";

    var loginRequestCaptor = ArgumentCaptor.forClass(LoginRequest.class);

    when(restTemplate.postForEntity(eq("/api/authenticate"), loginRequestCaptor.capture(), eq(UserInformationResponse.class)))
        .thenThrow(new HttpClientErrorException(HttpStatus.FORBIDDEN));

    assertThrows(OthAuthException.class, () -> tested.authenticate(username, password));

    var loginRequest = loginRequestCaptor.getValue();
    assertThat(loginRequest, is(notNullValue()));
    assertThat(loginRequest.getUsername(), is(username));
    assertThat(loginRequest.getPassword(), is(password));
    assertThat(loginRequest.getServiceKey(), is(API_KEY));
  }

  @Test
  void authenticateResponseNullTest() {
    var username = "arthur.dent";
    var password = "marvin4ever";

    var loginRequestCaptor = ArgumentCaptor.forClass(LoginRequest.class);

    when(restTemplate.postForEntity(eq("/api/authenticate"), loginRequestCaptor.capture(), eq(UserInformationResponse.class)))
        .thenReturn(ResponseEntity.ok(null));

    assertThrows(IllegalArgumentException.class, () -> tested.authenticate(username, password));

    var loginRequest = loginRequestCaptor.getValue();
    assertThat(loginRequest, is(notNullValue()));
    assertThat(loginRequest.getUsername(), is(username));
    assertThat(loginRequest.getPassword(), is(password));
    assertThat(loginRequest.getServiceKey(), is(API_KEY));
  }
}
