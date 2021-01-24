package de.othr.sw.benjamineder.barmanagement.application.order.entity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.Drink;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderPositionTest {

  @InjectMocks
  private OrderPosition tested;

  @Test
  void constructorTest() {
    var result = new OrderPosition();

    assertThat(result, is(notNullValue()));
    assertThat(result.getId(), is(notNullValue()));
  }

  @Test
  void getPriceTest() {
    var drink = mock(Drink.class);
    var drinkPrice = 21.0;
    var quantity = 2;

    tested.setDrink(drink);
    tested.setQuantity(quantity);

    when(drink.getPrice()).thenReturn(drinkPrice);

    var result = tested.getPrice();

    assertThat(result, is(42.0));
  }
}
