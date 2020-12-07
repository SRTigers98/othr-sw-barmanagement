package de.othr.sw.benjamineder.barmanagement.application.drink.entity;

import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public abstract class Drink {

  private UUID    id;
  private String  name;
  private Integer size;
  private Double  percentageOfAlcohol;
  private Double  price;

  public boolean isAlcoholic() {
    return this.percentageOfAlcohol > 0;
  }
}
