package de.othr.sw.benjamineder.barmanagement.application.drink.controller;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.SimpleDrink;
import de.othr.sw.benjamineder.barmanagement.application.drink.service.SimpleDrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/drink/simple")
public class SimpleDrinkController {

  private final SimpleDrinkService simpleDrinkService;

  @Autowired
  public SimpleDrinkController(SimpleDrinkService simpleDrinkService) {
    this.simpleDrinkService = simpleDrinkService;
  }

  @GetMapping(produces = "application/json")
  public List<SimpleDrink> getSimpleDrinks() {
    return simpleDrinkService.getDrinks();
  }

  @PutMapping(produces = "application/json", consumes = "application/json")
  public SimpleDrink putSimpleDrink(@RequestBody SimpleDrink simpleDrink) {
    return simpleDrinkService.addOrUpdateDrink(simpleDrink);
  }

  @DeleteMapping(produces = "application/json")
  public SimpleDrink deleteSimpleDrink(@RequestParam("id") String drinkId) {
    return simpleDrinkService.deleteDrink(drinkId);
  }

  @GetMapping(path = "/{id}", produces = "application/json")
  public SimpleDrink getSimpleDrinkById(@PathVariable("id") String drinkId) {
    return simpleDrinkService.getDrinkById(drinkId)
                             .orElse(null);
  }
}
