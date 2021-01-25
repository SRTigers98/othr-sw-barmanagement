package de.othr.sw.benjamineder.barmanagement.application.order.service;

import de.othr.sw.benjamineder.barmanagement.application.coupon.entity.Coupon;
import de.othr.sw.benjamineder.barmanagement.application.order.dao.CouponOrderRepository;
import de.othr.sw.benjamineder.barmanagement.application.order.entity.CouponOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import java.util.List;

@Service
public class CouponOrderService {

  private final CouponOrderRepository couponOrderRepository;

  @Autowired
  public CouponOrderService(CouponOrderRepository couponOrderRepository) {
    this.couponOrderRepository = couponOrderRepository;
  }

  public List<CouponOrder> getCouponOrders() {
    return couponOrderRepository.findAll();
  }

  @Transactional(TxType.REQUIRED)
  public CouponOrder orderCoupon(Double value) {
    var coupon = new Coupon();
    coupon.setValue(value);
    coupon.setRedeemable(true);
    var couponOrder = new CouponOrder();
    couponOrder.setCoupon(coupon);
    return couponOrderRepository.save(couponOrder);
  }

  public Double getCouponOrdersRevenue() {
    return couponOrderRepository.findAll()
                                .stream()
                                .mapToDouble(CouponOrder::getPrice)
                                .sum();
  }
}
