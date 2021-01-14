package de.othr.sw.benjamineder.barmanagement.application.order.entity;

import java.util.UUID;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@MappedSuperclass
@Data
@EqualsAndHashCode
public abstract class Order {

  @Id
  private UUID id;

  protected Order() {
    this.id = UUID.randomUUID();
  }

  public abstract Double getPrice();
}
