package de.othr.sw.benjamineder.barmanagement.application.web.bar;

import de.othr.sw.benjamineder.barmanagement.application.drink.service.ComplexDrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/recipe-book")
public class RecipeBookController {

  private final ComplexDrinkService complexDrinkService;

  @Autowired
  public RecipeBookController(ComplexDrinkService complexDrinkService) {
    this.complexDrinkService = complexDrinkService;
  }

  @GetMapping
  public String recipeOverview(Model model) {
    var drinksWithRecipe = complexDrinkService.getDrinks().stream()
                                              .filter(drink -> drink.getRecipe() != null)
                                              .collect(Collectors.toList());
    model.addAttribute("drinksWithRecipe", drinksWithRecipe);
    return "bar/bar_recipe-book";
  }
}
