package de.othr.sw.benjamineder.barmanagement.application.drink.service;

import de.othr.sw.benjamineder.barmanagement.application.drink.dao.ComplexDrinkRepository;
import de.othr.sw.benjamineder.barmanagement.application.drink.entity.ComplexDrink;
import de.othr.sw.benjamineder.barmanagement.application.recipe.entity.DrinkRecipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ComplexDrinkService extends DrinkService<ComplexDrink> {

  @Autowired
  public ComplexDrinkService(ComplexDrinkRepository complexDrinkRepository) {
    super(complexDrinkRepository);
  }

  public Optional<DrinkRecipe> getRecipeForDrink(String drinkId) {
    return drinkRepository.findById(drinkId)
                          .map(ComplexDrink::getRecipe);
  }
}
