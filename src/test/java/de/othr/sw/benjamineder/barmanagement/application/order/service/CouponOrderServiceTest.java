package de.othr.sw.benjamineder.barmanagement.application.order.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.othr.sw.benjamineder.barmanagement.application.order.dao.CouponOrderRepository;
import de.othr.sw.benjamineder.barmanagement.application.order.entity.CouponOrder;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CouponOrderServiceTest {

  @InjectMocks
  private CouponOrderService    tested;
  @Mock
  private CouponOrderRepository couponOrderRepository;

  @Test
  void getCouponOrdersTest() {
    var orders = List.of(mock(CouponOrder.class), mock(CouponOrder.class), mock(CouponOrder.class));

    when(couponOrderRepository.findAll()).thenReturn(orders);

    var result = tested.getCouponOrders();

    assertThat(result, is(orders));
  }

  @Test
  void orderCouponTest() {
    var couponValue = 42.0;
    var savedOrder = mock(CouponOrder.class);

    when(couponOrderRepository.save(any(CouponOrder.class))).thenReturn(savedOrder);

    var result = tested.orderCoupon(couponValue);

    assertThat(result, is(savedOrder));

    var orderCaptor = ArgumentCaptor.forClass(CouponOrder.class);
    verify(couponOrderRepository, times(1)).save(orderCaptor.capture());

    var order = orderCaptor.getValue();
    assertThat(order, is(notNullValue()));
    assertThat(order.getId(), is(notNullValue()));
    assertThat(order.getCoupon(), is(notNullValue()));
    assertThat(order.getCoupon().getId(), is(notNullValue()));
    assertThat(order.getCoupon().getValue(), is(couponValue));
    assertThat(order.getCoupon().isRedeemable(), is(true));
  }

  @Test
  void getCouponOrderRevenueTest() {
    var couponOrder = mock(CouponOrder.class);
    var orders = List.of(couponOrder, couponOrder, couponOrder);

    when(couponOrderRepository.findAll()).thenReturn(orders);
    when(couponOrder.getPrice()).thenReturn(42.0, 5.5, 12.25);

    var result = tested.getCouponOrdersRevenue();

    assertThat(result, is(59.75));
  }
}
