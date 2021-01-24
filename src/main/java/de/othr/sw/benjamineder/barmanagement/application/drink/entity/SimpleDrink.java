package de.othr.sw.benjamineder.barmanagement.application.drink.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class SimpleDrink extends Drink {

  private String          brand;
  private SimpleDrinkType type;
}
