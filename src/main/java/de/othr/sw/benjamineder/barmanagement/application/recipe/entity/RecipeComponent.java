package de.othr.sw.benjamineder.barmanagement.application.recipe.entity;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.SimpleDrink;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
@Data
@EqualsAndHashCode
public class RecipeComponent {

  @Id
  private String      id;
  @ManyToOne(optional = false)
  private SimpleDrink component;
  private Integer     quantity;

  public RecipeComponent() {
    this.id = UUID.randomUUID().toString();
  }
}
