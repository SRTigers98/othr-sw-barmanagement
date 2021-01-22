package de.othr.sw.benjamineder.barmanagement.application.rest.othauth;

import de.othr.brs31213.sw.othauth.dto.requests.LoginRequest;
import de.othr.brs31213.sw.othauth.dto.responses.UserInformationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class OthAuthService {

  @Value("${othr.oth-auth.api-key}")
  private       String       apiKey;
  private final RestTemplate restTemplate;

  @Autowired
  public OthAuthService(@Qualifier("othAuth") RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public Collection<? extends GrantedAuthority> authenticate(String username, String password) throws OthAuthException {
    var login = new LoginRequest();
    login.setUsername(username);
    login.setPassword(password);
    login.setServiceKey(apiKey);

    try {
      var response = restTemplate.postForEntity("/api/authenticate", login, UserInformationResponse.class)
                                 .getBody();
      Assert.notNull(response, "Auth Response must not be null!");
      return response.getRoles().stream()
                     .map(role -> new SimpleGrantedAuthority(String.format("ROLE_%s", role)))
                     .collect(Collectors.toList());
    } catch (HttpClientErrorException e) {
      throw new OthAuthException(e.getMessage(), e);
    }
  }
}
