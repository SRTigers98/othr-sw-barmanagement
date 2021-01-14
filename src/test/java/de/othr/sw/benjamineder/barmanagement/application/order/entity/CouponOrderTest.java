package de.othr.sw.benjamineder.barmanagement.application.order.entity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import de.othr.sw.benjamineder.barmanagement.application.coupon.entity.Coupon;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CouponOrderTest {

  @InjectMocks
  private CouponOrder tested;
  @Mock
  private Coupon      coupon;

  @Test
  void getPriceTest() {
    var price = 42.99;

    when(coupon.getValue()).thenReturn(price);

    var result = tested.getPrice();

    assertThat(result, is(price));
  }
}
