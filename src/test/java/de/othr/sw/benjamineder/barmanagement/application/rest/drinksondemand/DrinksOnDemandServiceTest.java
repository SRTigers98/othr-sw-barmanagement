package de.othr.sw.benjamineder.barmanagement.application.rest.drinksondemand;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import othr.nec37329.beverageproducer.backend.rest.ArticleDTO;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DrinksOnDemandServiceTest {

  @InjectMocks
  private DrinksOnDemandService tested;
  @Mock
  private RestTemplate          restTemplate;

  @Test
  void getArticlesTest() {
    var article1 = mock(ArticleDTO.class);
    var article2 = mock(ArticleDTO.class);
    var article3 = mock(ArticleDTO.class);
    var articleDTOs = new ArticleDTO[]{ article1, article2, article3 };

    when(restTemplate.getForEntity("/restapi/articles", ArticleDTO[].class))
        .thenReturn(ResponseEntity.ok(articleDTOs));

    var result = tested.getArticles();

    assertThat(result, is(notNullValue()));
    assertThat(result.size(), is(3));
    assertThat(result, hasItems(article1, article2, article3));
  }

  @Test
  void getArticleAsSimpleDrinkTest() {
    var articleId = UUID.randomUUID().toString();

    var article = mock(ArticleDTO.class);
    var articleName = "OTH-Bier";
    var articlePrice = 1.19;
    var articleProducer = "DrinksOnDemand GmbH";

    when(restTemplate.getForEntity(String.format("/restapi/articles/article?id=%s", articleId), ArticleDTO.class))
        .thenReturn(ResponseEntity.ok(article));
    when(article.getArtId()).thenReturn(articleId);
    when(article.getName()).thenReturn(articleName);
    when(article.getPrice()).thenReturn(articlePrice);
    when(article.getProducer()).thenReturn(articleProducer);

    var result = tested.getArticleAsSimpleDrink(articleId);

    assertThat(result, is(notNullValue()));
    assertThat(result.getId(), is(UUID.fromString(articleId)));
    assertThat(result.getName(), is(articleName));
    assertThat(result.getPrice(), is(articlePrice));
    assertThat(result.getBrand(), is(articleProducer));
  }
}
