package de.othr.sw.benjamineder.barmanagement.application.coupon.controller;

import de.othr.sw.benjamineder.barmanagement.application.coupon.entity.Coupon;
import de.othr.sw.benjamineder.barmanagement.application.coupon.service.CouponService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CouponControllerTest {

  @InjectMocks
  private CouponController tested;
  @Mock
  private CouponService    couponService;

  @Test
  void putCouponRedemptionTest() {
    var couponId = UUID.randomUUID();

    var coupon = mock(Coupon.class);

    when(couponService.redeemCoupon(couponId)).thenReturn(coupon);

    var result = tested.putCouponRedemption(couponId);

    assertThat(result, is(coupon));
  }
}
