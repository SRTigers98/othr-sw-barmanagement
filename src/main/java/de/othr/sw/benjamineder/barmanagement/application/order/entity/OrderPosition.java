package de.othr.sw.benjamineder.barmanagement.application.order.entity;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.Drink;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode
public class OrderPosition {

  @Id
  private String  id;
  @ManyToOne(optional = false)
  private Drink   drink;
  private Integer quantity;

  public OrderPosition() {
    this.id = UUID.randomUUID().toString();
  }

  public Double getPrice() {
    return this.drink.getPrice() * this.quantity;
  }
}
