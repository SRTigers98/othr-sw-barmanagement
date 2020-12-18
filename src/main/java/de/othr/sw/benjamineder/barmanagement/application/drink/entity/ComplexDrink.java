package de.othr.sw.benjamineder.barmanagement.application.drink.entity;

import de.othr.sw.benjamineder.barmanagement.application.recipe.entity.DrinkRecipe;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class ComplexDrink extends Drink {

  @OneToOne(optional = false)
  private DrinkRecipe      recipe;
  private ComplexDrinkType type;
}
