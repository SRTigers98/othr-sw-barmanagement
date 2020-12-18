package de.othr.sw.benjamineder.barmanagement.application.recipe.entity;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.SimpleDrink;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
public class RecipeComponent {

  @Id
  private UUID        id;
  @ManyToOne(optional = false, cascade = CascadeType.REMOVE)
  private SimpleDrink component;
  private Integer     quantity;

  public RecipeComponent() {
    this.id = UUID.randomUUID();
  }
}
