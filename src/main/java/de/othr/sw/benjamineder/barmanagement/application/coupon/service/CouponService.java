package de.othr.sw.benjamineder.barmanagement.application.coupon.service;

import de.othr.sw.benjamineder.barmanagement.application.coupon.dao.CouponRepository;
import de.othr.sw.benjamineder.barmanagement.application.coupon.entity.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CouponService {

  private final CouponRepository couponRepository;

  @Autowired
  public CouponService(CouponRepository couponRepository) {
    this.couponRepository = couponRepository;
  }

  public Coupon redeemCoupon(String couponId) {
    var coupon = couponRepository.findById(couponId)
                                 .orElseThrow(() -> new IllegalArgumentException(String.format("Coupon with id %s does not exist!", couponId)));
    if (coupon.isRedeemable()) {
      coupon.setRedeemable(false);
      return couponRepository.save(coupon);
    } else {
      throw new IllegalStateException(String.format("Coupon with id %s is not redeemable!", couponId));
    }
  }
}
