package de.othr.sw.benjamineder.barmanagement.application.drink.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@EqualsAndHashCode
public abstract class Drink {

  @Id
  private UUID    id;
  @Column(unique = true)
  private String  name;
  private Integer size;
  private Double  percentageOfAlcohol;
  private Double  price;

  protected Drink() {
    this.id = UUID.randomUUID();
  }

  public boolean isAlcoholic() {
    return this.percentageOfAlcohol > 0;
  }
}
