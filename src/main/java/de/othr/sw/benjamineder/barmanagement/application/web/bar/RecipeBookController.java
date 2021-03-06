package de.othr.sw.benjamineder.barmanagement.application.web.bar;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.ComplexDrink;
import de.othr.sw.benjamineder.barmanagement.application.drink.service.ComplexDrinkService;
import de.othr.sw.benjamineder.barmanagement.application.web.auth.BarUserAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/recipe-book")
public class RecipeBookController {

  private final ComplexDrinkService complexDrinkService;

  @Autowired
  public RecipeBookController(ComplexDrinkService complexDrinkService) {
    this.complexDrinkService = complexDrinkService;
  }

  @BarUserAccess
  @GetMapping
  public String recipeOverview(Model model) {
    var drinksWithRecipe = complexDrinkService.getDrinks().stream()
                                              .filter(drink -> drink.getRecipe() != null)
                                              .sorted(Comparator.comparing(ComplexDrink::getName))
                                              .collect(Collectors.toList());
    drinksWithRecipe.forEach(
        drink -> drink.getRecipe().getComponents().sort(Comparator.comparing(comp -> comp.getComponent().getName())));
    model.addAttribute("drinksWithRecipe", drinksWithRecipe);
    return "bar/bar_recipe-book";
  }
}
