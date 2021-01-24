package de.othr.sw.benjamineder.barmanagement.application.order.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.othr.sw.benjamineder.barmanagement.application.order.entity.DrinkOrder;
import de.othr.sw.benjamineder.barmanagement.application.order.entity.OrderPosition;
import de.othr.sw.benjamineder.barmanagement.application.order.service.DrinkOrderService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DrinkOrderControllerTest {

  @InjectMocks
  private DrinkOrderController tested;
  @Mock
  private DrinkOrderService    drinkOrderService;

  @Test
  void getDrinkOrdersTest() {
    var drinkOrders = List.of(mock(DrinkOrder.class), mock(DrinkOrder.class), mock(DrinkOrder.class));

    when(drinkOrderService.getDrinkOrders()).thenReturn(drinkOrders);

    var result = tested.getDrinkOrders();

    assertThat(result, is(drinkOrders));
  }

  @Test
  void postDrinkOrderTest() {
    var orderPositions = List.of(mock(OrderPosition.class), mock(OrderPosition.class), mock(OrderPosition.class));
    var savedOrder = mock(DrinkOrder.class);

    when(drinkOrderService.order(orderPositions)).thenReturn(savedOrder);

    var result = tested.postDrinkOrder(orderPositions);

    assertThat(result, is(savedOrder));
  }

  @Test
  void getDrinkOrdersRevenueTest() {
    var revenue = 42.0;

    when(drinkOrderService.getDrinkOrdersRevenue()).thenReturn(revenue);

    var result = tested.getDrinkOrdersRevenue();

    assertThat(result, is(revenue));
  }
}
