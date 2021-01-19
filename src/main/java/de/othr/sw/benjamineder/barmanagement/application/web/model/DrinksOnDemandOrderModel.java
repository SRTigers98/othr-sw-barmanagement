package de.othr.sw.benjamineder.barmanagement.application.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class DrinksOnDemandOrderModel {

  private Map<String, Integer> articlePositions;
}
