package de.othr.sw.benjamineder.barmanagement.application.rest.drinksondemand;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.SimpleDrink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import othr.nec37329.beverageproducer.backend.rest.ArticleDTO;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class DrinksOnDemandService {

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

  public SimpleDrink getArticleAsSimpleDrink(String articleId) {
    var article = restTemplate.getForEntity(String.format("/restapi/articles/article?id=%s", articleId),
                                            ArticleDTO.class)
                              .getBody();
    Assert.notNull(article, "Article must not be null!");
    return mapArticleToSimpleDrink(article);
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
