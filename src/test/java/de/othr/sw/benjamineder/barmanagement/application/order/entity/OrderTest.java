package de.othr.sw.benjamineder.barmanagement.application.order.entity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

class OrderTest {

  @Test
  void constructorTest() {
    var result = new Order() {
      @Override
      public Double getPrice() {
        return null;
      }
    };

    assertThat(result, is(notNullValue()));
    assertThat(result.getId(), is(notNullValue()));
  }
}
