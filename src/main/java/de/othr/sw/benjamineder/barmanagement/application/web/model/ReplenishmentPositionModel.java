package de.othr.sw.benjamineder.barmanagement.application.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class ReplenishmentPositionModel {

  private String  name;
  private Integer availableStock;
  private Integer replenishmentQuantity;
}
