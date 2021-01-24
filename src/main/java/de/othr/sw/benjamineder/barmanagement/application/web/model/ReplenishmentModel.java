package de.othr.sw.benjamineder.barmanagement.application.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class ReplenishmentModel {

  private List<ReplenishmentPositionModel> replenishmentPositions;
}
