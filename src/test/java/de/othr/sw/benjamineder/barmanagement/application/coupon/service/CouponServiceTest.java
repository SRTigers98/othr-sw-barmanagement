package de.othr.sw.benjamineder.barmanagement.application.coupon.service;

import de.othr.sw.benjamineder.barmanagement.application.coupon.dao.CouponRepository;
import de.othr.sw.benjamineder.barmanagement.application.coupon.entity.Coupon;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

  @InjectMocks
  private CouponService    tested;
  @Mock
  private CouponRepository couponRepository;

  @Test
  void redeemCouponTest() {
    var couponId = UUID.randomUUID().toString();

    var coupon = mock(Coupon.class);
    var savedCoupon = mock(Coupon.class);

    when(couponRepository.findById(couponId)).thenReturn(Optional.of(coupon));
    when(coupon.isRedeemable()).thenReturn(true);
    doNothing().when(coupon).setRedeemable(any(Boolean.class));
    when(couponRepository.save(coupon)).thenReturn(savedCoupon);

    var result = tested.redeemCoupon(couponId);

    assertThat(result, is(savedCoupon));

    verify(coupon, times(1)).setRedeemable(false);
  }

  @Test
  void redeemCouponNotFoundTest() {
    var couponId = UUID.randomUUID().toString();

    when(couponRepository.findById(couponId)).thenReturn(Optional.empty());

    assertThrows(IllegalArgumentException.class, () -> tested.redeemCoupon(couponId));
  }

  @Test
  void redeemCouponNotRedeemableTest() {
    var couponId = UUID.randomUUID().toString();

    var coupon = mock(Coupon.class);

    when(couponRepository.findById(couponId)).thenReturn(Optional.of(coupon));
    when(coupon.isRedeemable()).thenReturn(false);

    assertThrows(IllegalStateException.class, () -> tested.redeemCoupon(couponId));
  }
}
