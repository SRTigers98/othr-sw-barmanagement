package de.othr.sw.benjamineder.barmanagement.application.drink.controller;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.ComplexDrink;
import de.othr.sw.benjamineder.barmanagement.application.drink.service.ComplexDrinkService;
import de.othr.sw.benjamineder.barmanagement.application.recipe.entity.DrinkRecipe;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/drink/complex")
public class ComplexDrinkController {

  private final ComplexDrinkService complexDrinkService;

  @Autowired
  public ComplexDrinkController(ComplexDrinkService complexDrinkService) {
    this.complexDrinkService = complexDrinkService;
  }

  @GetMapping(produces = "application/json")
  public List<ComplexDrink> getComplexDrinks() {
    return complexDrinkService.getDrinks();
  }

  @PutMapping(produces = "application/json", consumes = "application/json")
  public ComplexDrink putComplexDrink(@RequestBody ComplexDrink complexDrink) {
    return complexDrinkService.addOrUpdateDrink(complexDrink);
  }

  @DeleteMapping(produces = "application/json")
  public ComplexDrink deleteComplexDrink(@RequestParam("id") UUID drinkId) {
    return complexDrinkService.deleteDrink(drinkId);
  }

  @GetMapping(path = "/{id}/recipe", produces = "application/json")
  public DrinkRecipe getRecipeForDrink(@PathVariable("id") UUID drinkId) {
    return complexDrinkService.getRecipeForDrink(drinkId);
  }
}
