package de.othr.sw.benjamineder.barmanagement.application.order.controller;

import de.othr.sw.benjamineder.barmanagement.application.order.entity.CouponOrder;
import de.othr.sw.benjamineder.barmanagement.application.order.service.CouponOrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CouponOrderControllerTest {

  @InjectMocks
  private CouponOrderController tested;
  @Mock
  private CouponOrderService    couponOrderService;

  @Test
  void getCouponOrdersTest() {
    var orders = List.of(mock(CouponOrder.class), mock(CouponOrder.class), mock(CouponOrder.class));

    when(couponOrderService.getCouponOrders()).thenReturn(orders);

    var result = tested.getCouponOrders();

    assertThat(result, is(orders));
  }

  @Test
  void postCouponOrderTest() {
    var couponValue = 42.0;

    var couponId = UUID.randomUUID().toString();

    when(couponOrderService.order(couponValue)).thenReturn(couponId);

    var result = tested.postCouponOrder(couponValue);

    assertThat(result, is(couponId));
  }

  @Test
  void getCouponOrdersRevenueTest() {
    var revenue = 198.75;

    when(couponOrderService.getCouponOrdersRevenue()).thenReturn(revenue);

    var result = tested.getCouponOrdersRevenue();

    assertThat(result, is(revenue));
  }
}
