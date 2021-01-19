package de.othr.sw.benjamineder.barmanagement.application.rest.drinksondemand;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;
import othr.nec37329.beverageproducer.backend.rest.ArticleDTO;
import othr.nec37329.beverageproducer.backend.rest.CustomerOrderDTO;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DrinksOnDemandServiceTest {

  private static final String CUSTOMER_ID = "ArthurDent42";

  @InjectMocks
  private DrinksOnDemandService tested;
  @Mock
  private RestTemplate          restTemplate;

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(tested, "customerId", CUSTOMER_ID);
  }

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
  void getArticleByNameTest() {
    var tested = spy(this.tested);

    var name = "Pan Galactic Gargle Blaster";

    var article = mock(ArticleDTO.class);
    var panGalacticGargleBlaster = mock(ArticleDTO.class);
    var articles = List.of(article, article, article, panGalacticGargleBlaster, article);

    doReturn(articles).when(tested).getArticles();
    when(article.getName()).thenReturn("Not The Wanted One");
    when(panGalacticGargleBlaster.getName()).thenReturn(name);

    var result = tested.getArticleByName(name);

    assertThat(result.isPresent(), is(true));
    assertThat(result.get(), is(panGalacticGargleBlaster));
  }

  @Test
  void getArticleByNameNotFoundTest() {
    var tested = spy(this.tested);

    var name = "Pan Galactic Gargle Blaster";

    var article = mock(ArticleDTO.class);
    var articles = List.of(article, article, article, article, article);

    doReturn(articles).when(tested).getArticles();
    when(article.getName()).thenReturn("Not The Wanted One");

    var result = tested.getArticleByName(name);

    assertThat(result.isPresent(), is(false));
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

  @Test
  void orderArticlesTest() {
    var beer = mock(ArticleDTO.class);
    var wine = mock(ArticleDTO.class);
    var articlePositions = Map.of(beer, 20, wine, 5);

    var orderCaptor = ArgumentCaptor.forClass(CustomerOrderDTO.class);
    when(restTemplate.postForEntity(eq(String.format("/restapi/orders?id=%s", CUSTOMER_ID)), orderCaptor.capture(), eq(Object.class)))
        .thenReturn(ResponseEntity.ok().build());

    var result = tested.orderArticles(articlePositions);

    assertThat(result, is(true));

    var order = orderCaptor.getValue();
    assertThat(order, is(notNullValue()));
    assertThat(order.getOrderedArticles(), is(notNullValue()));
    assertThat(order.getOrderedArticles(), hasItems(
        allOf(hasProperty("article", is(beer)), hasProperty("quantity", is(20))),
        allOf(hasProperty("article", is(wine)), hasProperty("quantity", is(5)))
    ));
  }

  @Test
  void orderArticlesBadRequestTest() {
    var beer = mock(ArticleDTO.class);
    var wine = mock(ArticleDTO.class);
    var articlePositions = Map.of(beer, 20, wine, 5);

    when(restTemplate.postForEntity(eq(String.format("/restapi/orders?id=%s", CUSTOMER_ID)), ArgumentMatchers.any(CustomerOrderDTO.class), eq(Object.class)))
        .thenReturn(ResponseEntity.badRequest().build());

    var result = tested.orderArticles(articlePositions);

    assertThat(result, is(false));
  }
}
