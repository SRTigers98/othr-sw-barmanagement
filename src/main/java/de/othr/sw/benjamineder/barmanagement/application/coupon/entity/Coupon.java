package de.othr.sw.benjamineder.barmanagement.application.coupon.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode
public class Coupon {

  @Id
  private String  id;
  private Double  value;
  private boolean redeemable;

  public Coupon() {
    this.id = UUID.randomUUID().toString();
  }
}
