package de.othr.sw.benjamineder.barmanagement.application.order.entity;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.Drink;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
public class OrderPosition {

  @Id
  private UUID    id;
  @ManyToOne
  private Drink   drink;
  private Integer quantity;

  public OrderPosition() {
    this.id = UUID.randomUUID();
  }

  public Double getPrice() {
    return this.drink.getPrice() * this.quantity;
  }
}
