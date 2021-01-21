package de.othr.sw.benjamineder.barmanagement.application.order.controller;

import de.othr.sw.benjamineder.barmanagement.application.order.entity.CouponOrder;
import de.othr.sw.benjamineder.barmanagement.application.order.service.CouponOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order/coupon")
public class CouponOrderController {

  private final CouponOrderService couponOrderService;

  @Autowired
  public CouponOrderController(CouponOrderService couponOrderService) {
    this.couponOrderService = couponOrderService;
  }

  @GetMapping(produces = "application/json")
  public List<CouponOrder> getCouponOrders() {
    return couponOrderService.getCouponOrders();
  }

  @PostMapping
  public String postCouponOrder(@RequestParam("value") Double couponValue) {
    return couponOrderService.orderCoupon(couponValue)
                             .getCoupon()
                             .getId();
  }

  @GetMapping(path = "/revenue")
  public Double getCouponOrdersRevenue() {
    return couponOrderService.getCouponOrdersRevenue();
  }
}
