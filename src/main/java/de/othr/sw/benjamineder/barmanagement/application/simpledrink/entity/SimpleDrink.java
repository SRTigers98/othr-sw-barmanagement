package de.othr.sw.benjamineder.barmanagement.application.simpledrink.entity;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.Drink;
import javax.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class SimpleDrink extends Drink {

  private String          brand;
  private SimpleDrinkType type;
  private Integer         stock;
  private Integer         tempStock;
}
