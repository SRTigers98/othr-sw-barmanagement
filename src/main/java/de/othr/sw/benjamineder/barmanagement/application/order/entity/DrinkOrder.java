package de.othr.sw.benjamineder.barmanagement.application.order.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class DrinkOrder extends Order {

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderPosition> orderPositions;

  @Override
  public Double getPrice() {
    return this.orderPositions.stream()
                              .mapToDouble(OrderPosition::getPrice)
                              .sum();
  }
}
