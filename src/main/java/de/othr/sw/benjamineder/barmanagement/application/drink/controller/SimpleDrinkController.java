package de.othr.sw.benjamineder.barmanagement.application.drink.controller;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.SimpleDrink;
import de.othr.sw.benjamineder.barmanagement.application.drink.service.SimpleDrinkService;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
  public SimpleDrink deleteSimpleDrink(@RequestParam("id") UUID drinkId) {
    return simpleDrinkService.deleteDrink(drinkId);
  }
}
