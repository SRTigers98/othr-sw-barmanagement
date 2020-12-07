package de.othr.sw.benjamineder.barmanagement.application.drink.entity;

import java.util.UUID;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@MappedSuperclass
@Data
@EqualsAndHashCode
public abstract class Drink {

  @Id
  private UUID    id;
  private String  name;
  private Integer size;
  private Double  percentageOfAlcohol;
  private Double  price;

  public Drink() {
    this.id = UUID.randomUUID();
  }

  public boolean isAlcoholic() {
    return this.percentageOfAlcohol > 0;
  }
}
