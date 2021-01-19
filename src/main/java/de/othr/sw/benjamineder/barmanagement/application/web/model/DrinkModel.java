package de.othr.sw.benjamineder.barmanagement.application.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@AllArgsConstructor
@Data
@EqualsAndHashCode
public class DrinkModel {

  private UUID    id;
  private String  name;
  private Integer stock;
}
