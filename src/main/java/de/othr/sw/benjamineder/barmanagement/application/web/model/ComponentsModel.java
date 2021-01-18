package de.othr.sw.benjamineder.barmanagement.application.web.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode
public class ComponentsModel {

  private Map<String, Integer> components;

  public ComponentsModel() {
    this.components = new HashMap<>();
  }

  public ComponentsModel(Map<String, Integer> components) {
    this.components = components;
  }
}
