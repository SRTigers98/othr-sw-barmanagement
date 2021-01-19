package de.othr.sw.benjamineder.barmanagement.application.rest.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

  @Value("${othr.warehouse.host}")
  private String warehouseURL;
  @Value("${othr.drinks-on-demand}")
  private String drinksOnDemandURL;

  @Bean
  @Qualifier("warehouse")
  public RestTemplate warehouseRestTemplate() {
    return new RestTemplateBuilder().rootUri(warehouseURL).build();
  }

  @Bean
  @Qualifier("drinksOnDemand")
  public RestTemplate drinksOnDemandRestTemplate() {
    return new RestTemplateBuilder().rootUri(drinksOnDemandURL).build();
  }
}
