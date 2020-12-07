package de.othr.sw.benjamineder.barmanagement.application.simpledrink.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class SimpleDrink {

  private String          brand;
  private SimpleDrinkType type;
  private Integer         stock;
  private Integer         tempStock;
}
