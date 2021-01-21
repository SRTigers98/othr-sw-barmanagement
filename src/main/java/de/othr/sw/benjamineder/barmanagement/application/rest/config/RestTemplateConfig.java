package de.othr.sw.benjamineder.barmanagement.application.rest.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

  @Value("${othr.oth-auth.host}")
  private String othAuthURL;
  @Value("${othr.warehouse.host}")
  private String warehouseURL;
  @Value("${othr.drinks-on-demand.host}")
  private String drinksOnDemandURL;

  @Bean
  @Qualifier("othAuth")
  public RestTemplate othAuthRestTemplate() {
    return buildTemplateWithRootUri(othAuthURL);
  }

  @Bean
  @Qualifier("warehouse")
  public RestTemplate warehouseRestTemplate() {
    return buildTemplateWithRootUri(warehouseURL);
  }

  @Bean
  @Qualifier("drinksOnDemand")
  public RestTemplate drinksOnDemandRestTemplate() {
    return buildTemplateWithRootUri(drinksOnDemandURL);
  }

  private RestTemplate buildTemplateWithRootUri(String rootUri) {
    return new RestTemplateBuilder().rootUri(rootUri).build();
  }
}
