package de.othr.sw.benjamineder.barmanagement.application.recipe.entity;

import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
public class DrinkRecipe {

  @Id
  private UUID                  id;
  @OneToMany(mappedBy = "id")
  private List<RecipeComponent> components;
  private String                notes;

  public DrinkRecipe() {
    this.id = UUID.randomUUID();
  }
}
