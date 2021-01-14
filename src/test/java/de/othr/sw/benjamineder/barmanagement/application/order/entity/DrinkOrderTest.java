package de.othr.sw.benjamineder.barmanagement.application.order.entity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DrinkOrderTest {

  @InjectMocks
  private DrinkOrder tested;

  @Test
  void constructorTest() {
    var result = new DrinkOrder();

    assertThat(result, is(notNullValue()));
    assertThat(result.getId(), is(notNullValue()));
  }

  @Test
  void getPriceTest() {
    var orderPosition = mock(OrderPosition.class);
    var orderPositions = List.of(orderPosition, orderPosition, orderPosition);

    tested.setOrderPositions(orderPositions);

    when(orderPosition.getPrice()).thenReturn(42.0, 21.0, 12.5);

    var result = tested.getPrice();

    assertThat(result, is(75.5));
  }
}
