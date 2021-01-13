package de.othr.sw.benjamineder.barmanagement.application.drink.service;

import de.othr.sw.benjamineder.barmanagement.application.drink.dao.ComplexDrinkRepository;
import de.othr.sw.benjamineder.barmanagement.application.drink.entity.ComplexDrink;
import de.othr.sw.benjamineder.barmanagement.application.recipe.entity.DrinkRecipe;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComplexDrinkService extends DrinkService<ComplexDrink> {

  @Autowired
  public ComplexDrinkService(ComplexDrinkRepository complexDrinkRepository) {
    super(complexDrinkRepository);
  }

  public DrinkRecipe getRecipeForDrink(UUID drinkId) {
    return drinkRepository.findById(drinkId)
                          .orElseThrow(() -> new IllegalArgumentException(String.format("Drink with ID %s does not exist!", drinkId)))
                          .getRecipe();
  }
}
