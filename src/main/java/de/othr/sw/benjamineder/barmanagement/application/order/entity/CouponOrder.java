package de.othr.sw.benjamineder.barmanagement.application.order.entity;

import de.othr.sw.benjamineder.barmanagement.application.coupon.entity.Coupon;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class CouponOrder extends Order {

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  private Coupon coupon;

  @Override
  public Double getPrice() {
    return coupon.getValue();
  }
}
