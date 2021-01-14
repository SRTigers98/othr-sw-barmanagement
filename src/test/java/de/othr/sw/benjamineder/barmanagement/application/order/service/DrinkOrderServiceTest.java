package de.othr.sw.benjamineder.barmanagement.application.order.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.othr.sw.benjamineder.barmanagement.application.order.dao.DrinkOrderRepository;
import de.othr.sw.benjamineder.barmanagement.application.order.entity.DrinkOrder;
import de.othr.sw.benjamineder.barmanagement.application.order.entity.OrderPosition;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DrinkOrderServiceTest {

  @InjectMocks
  private DrinkOrderService    tested;
  @Mock
  private DrinkOrderRepository drinkOrderRepository;

  @Test
  void getDrinkOrdersTest() {
    var drinkOrders = List.of(mock(DrinkOrder.class), mock(DrinkOrder.class), mock(DrinkOrder.class));

    when(drinkOrderRepository.findAll()).thenReturn(drinkOrders);

    var result = tested.getDrinkOrders();

    assertThat(result, is(drinkOrders));
  }

  @Test
  void orderTest() {
    var orderPositions = List.of(mock(OrderPosition.class), mock(OrderPosition.class), mock(OrderPosition.class));
    var savedOrder = mock(DrinkOrder.class);

    when(drinkOrderRepository.save(any(DrinkOrder.class))).thenReturn(savedOrder);

    var result = tested.order(orderPositions);

    assertThat(result, is(savedOrder));

    var orderCaptor = ArgumentCaptor.forClass(DrinkOrder.class);
    verify(drinkOrderRepository, times(1)).save(orderCaptor.capture());

    var order = orderCaptor.getValue();
    assertThat(order, is(notNullValue()));
    assertThat(order.getId(), is(notNullValue()));
    assertThat(order.getOrderPositions(), is(orderPositions));
  }

  @Test
  void getDrinkOrdersRevenueTest() {
    var drinkOrder = mock(DrinkOrder.class);
    var drinkOrders = List.of(drinkOrder, drinkOrder, drinkOrder);

    when(drinkOrderRepository.findAll()).thenReturn(drinkOrders);
    when(drinkOrder.getPrice()).thenReturn(55.0, 20.5, 2.75);

    var result = tested.getDrinkOrdersRevenue();

    assertThat(result, is(78.25));
  }
}
