package de.othr.sw.benjamineder.barmanagement.application.order.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Data
@EqualsAndHashCode
public abstract class Order {

  @Id
  private UUID          id;
  private LocalDateTime timestamp;

  protected Order() {
    this.id = UUID.randomUUID();
    this.timestamp = LocalDateTime.now();
  }

  public abstract Double getPrice();
}
