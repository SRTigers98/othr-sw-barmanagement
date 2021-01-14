package de.othr.sw.benjamineder.barmanagement.application.coupon.entity;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
public class Coupon {

  @Id
  private UUID    id;
  private Double  value;
  private boolean redeemable;

  public Coupon() {
    this.id = UUID.randomUUID();
  }
}
