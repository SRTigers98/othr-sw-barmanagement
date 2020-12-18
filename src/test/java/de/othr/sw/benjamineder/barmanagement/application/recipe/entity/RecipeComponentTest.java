package de.othr.sw.benjamineder.barmanagement.application.recipe.entity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

class RecipeComponentTest {

  @Test
  void constructorTest() {
    var result = new RecipeComponent();

    assertThat(result, is(notNullValue()));
    assertThat(result.getId(), is(notNullValue()));
  }
}