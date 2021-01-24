package de.othr.sw.benjamineder.barmanagement.application.drink.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@EqualsAndHashCode
public class Drink {

  @Id
  private String  id;
  @Column(unique = true)
  private String  name;
  private Integer size;
  private Double  percentageOfAlcohol;
  private Double  price;

  protected Drink() {
    this.id = UUID.randomUUID().toString();
  }

  public boolean isAlcoholic() {
    return this.percentageOfAlcohol > 0;
  }
}
