package de.othr.sw.benjamineder.barmanagement.application.drink.entity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DrinkTest {

  private Drink tested;

  @BeforeEach
  void setUp() {
    this.tested = new Drink() {
    };
  }

  @Test
  void isAlcoholicTest() {
    tested.setPercentageOfAlcohol(5.1);

    var result = tested.isAlcoholic();

    assertThat(result, is(true));
  }

  @Test
  void isAlcoholicFalseTest() {
    tested.setPercentageOfAlcohol(0.0);

    var result = tested.isAlcoholic();

    assertThat(result, is(false));
  }
}
