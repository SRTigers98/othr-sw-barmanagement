package de.othr.sw.benjamineder.barmanagement.application.rest.drinksondemand;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.SimpleDrink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import othr.nec37329.beverageproducer.backend.rest.ArticleDTO;
import othr.nec37329.beverageproducer.backend.rest.CustomerOrderDTO;
import othr.nec37329.beverageproducer.backend.rest.OrderpositionDTO;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DrinksOnDemandService {

  @Value("${othr.drinks-on-demand.customer-id}")
  private       String       customerId;
  private final RestTemplate restTemplate;

  @Autowired
  public DrinksOnDemandService(@Qualifier("drinksOnDemand") RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public List<ArticleDTO> getArticles() {
    var articles = restTemplate.getForEntity("/restapi/articles", ArticleDTO[].class)
                               .getBody();
    Assert.notNull(articles, "Articles must not be null!");
    return Arrays.asList(articles);
  }

  public Optional<ArticleDTO> getArticleByName(String name) {
    return this.getArticles().stream()
               .filter(article -> article.getName().equals(name))
               .findFirst();
  }

  public SimpleDrink getArticleAsSimpleDrink(String articleId) {
    var article = restTemplate.getForEntity(String.format("/restapi/articles/article?id=%s", articleId),
                                            ArticleDTO.class)
                              .getBody();
    Assert.notNull(article, "Article must not be null!");
    return mapArticleToSimpleDrink(article);
  }

  public boolean orderArticles(Map<ArticleDTO, Integer> articlePositions) {
    var positionDTOs = articlePositions.entrySet().stream()
                                       .map(entry -> new OrderpositionDTO(entry.getKey(), entry.getValue()))
                                       .collect(Collectors.toList());
    var orderDTO = new CustomerOrderDTO(positionDTOs);
    var response = restTemplate.postForEntity(String.format("/restapi/orders?id=%s", customerId),
                                              orderDTO, Object.class);
    return response.getStatusCode().is2xxSuccessful();
  }

  private SimpleDrink mapArticleToSimpleDrink(ArticleDTO article) {
    var drink = new SimpleDrink();
    drink.setId(UUID.fromString(article.getArtId()));
    drink.setName(article.getName());
    drink.setPrice(article.getPrice());
    drink.setBrand(article.getProducer());
    return drink;
  }
}
