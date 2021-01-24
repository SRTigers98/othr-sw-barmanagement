package de.othr.sw.benjamineder.barmanagement.application.coupon.controller;

import de.othr.sw.benjamineder.barmanagement.application.coupon.entity.Coupon;
import de.othr.sw.benjamineder.barmanagement.application.coupon.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/coupon")
public class CouponController {

  private final CouponService couponService;

  @Autowired
  public CouponController(CouponService couponService) {
    this.couponService = couponService;
  }

  @PutMapping(path = "/redeem", produces = "application/json")
  public Coupon putCouponRedemption(@RequestParam("id") String couponId) {
    return couponService.redeemCoupon(couponId);
  }
}
