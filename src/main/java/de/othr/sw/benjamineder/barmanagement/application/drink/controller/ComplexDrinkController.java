package de.othr.sw.benjamineder.barmanagement.application.drink.controller;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.ComplexDrink;
import de.othr.sw.benjamineder.barmanagement.application.drink.service.ComplexDrinkService;
import de.othr.sw.benjamineder.barmanagement.application.recipe.entity.DrinkRecipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
  public ComplexDrink deleteComplexDrink(@RequestParam("id") String drinkId) {
    return complexDrinkService.deleteDrink(drinkId);
  }

  @GetMapping(path = "/{id}", produces = "application/json")
  public ComplexDrink getComplexDrinkById(@PathVariable("id") String drinkId) {
    return complexDrinkService.getDrinkById(drinkId)
                              .orElse(null);
  }

  @GetMapping(path = "/{id}/recipe", produces = "application/json")
  public DrinkRecipe getRecipeForDrink(@PathVariable("id") String drinkId) {
    return complexDrinkService.getRecipeForDrink(drinkId)
                              .orElse(null);
  }
}
