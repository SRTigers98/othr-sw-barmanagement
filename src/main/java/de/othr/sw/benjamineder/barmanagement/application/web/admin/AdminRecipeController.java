package de.othr.sw.benjamineder.barmanagement.application.web.admin;

import de.othr.sw.benjamineder.barmanagement.application.drink.service.ComplexDrinkService;
import de.othr.sw.benjamineder.barmanagement.application.recipe.entity.DrinkRecipe;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/complex/{drinkId}/recipe")
public class AdminRecipeController {

  private final ComplexDrinkService complexDrinkService;

  @Autowired
  public AdminRecipeController(ComplexDrinkService complexDrinkService) {
    this.complexDrinkService = complexDrinkService;
  }

  @GetMapping
  public String adminComplexDrinkRecipe(@PathVariable("drinkId") UUID drinkId, Model model) {
    var recipe = complexDrinkService.getRecipeForDrink(drinkId)
                                    .orElseGet(DrinkRecipe::new);
    model.addAttribute("drinkId", drinkId)
         .addAttribute("recipe", recipe)
         .addAttribute("saved", false);
    return "admin_recipe";
  }

  @PostMapping
  public String adminComplexDrinkRecipe(@PathVariable("drinkId") UUID drinkId, @ModelAttribute DrinkRecipe recipe, Model model) {
    var drink = complexDrinkService.getDrinkById(drinkId);
    complexDrinkService.getRecipeForDrink(drinkId)
                       .map(DrinkRecipe::getComponents)
                       .ifPresent(recipe::setComponents);
    drink.setRecipe(recipe);
    var savedRecipe = complexDrinkService.addOrUpdateDrink(drink).getRecipe();
    model.addAttribute("drinkId", drinkId)
         .addAttribute("recipe", savedRecipe)
         .addAttribute("saved", true);
    return "admin_recipe";
  }
}
