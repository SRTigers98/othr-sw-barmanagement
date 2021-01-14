package de.othr.sw.benjamineder.barmanagement.application.coupon.entity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

class CouponTest {

  @Test
  void constructorTest() {
    var result = new Coupon();

    assertThat(result, is(notNullValue()));
    assertThat(result.getId(), is(notNullValue()));
  }
}
