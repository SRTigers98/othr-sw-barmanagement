package de.othr.sw.benjamineder.barmanagement.application.recipe.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode
public class DrinkRecipe {

  @Id
  private UUID                  id;
  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  private List<RecipeComponent> components;
  private String                notes;

  public DrinkRecipe() {
    this.id = UUID.randomUUID();
    this.components = new ArrayList<>();
  }
}
