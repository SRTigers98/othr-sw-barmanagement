package de.othr.sw.benjamineder.barmanagement.application.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Data
@EqualsAndHashCode
public class DrinkModel {

  private String  id;
  private String  name;
  private Integer stock;
}
