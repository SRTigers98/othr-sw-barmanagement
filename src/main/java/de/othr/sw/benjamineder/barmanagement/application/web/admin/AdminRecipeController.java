package de.othr.sw.benjamineder.barmanagement.application.web.admin;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.SimpleDrink;
import de.othr.sw.benjamineder.barmanagement.application.drink.service.ComplexDrinkService;
import de.othr.sw.benjamineder.barmanagement.application.drink.service.SimpleDrinkService;
import de.othr.sw.benjamineder.barmanagement.application.recipe.entity.DrinkRecipe;
import de.othr.sw.benjamineder.barmanagement.application.recipe.entity.RecipeComponent;
import de.othr.sw.benjamineder.barmanagement.application.web.model.ComponentsModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Controller
@RequestMapping("/admin/complex/{drinkId}/recipe")
public class AdminRecipeController {

  private static final String DRINK_ID = "drinkId";
  private static final String SAVED    = "saved";

  private final ComplexDrinkService complexDrinkService;
  private final SimpleDrinkService  simpleDrinkService;

  @Autowired
  public AdminRecipeController(ComplexDrinkService complexDrinkService, SimpleDrinkService simpleDrinkService) {
    this.complexDrinkService = complexDrinkService;
    this.simpleDrinkService = simpleDrinkService;
  }

  @GetMapping
  public String adminComplexDrinkRecipe(@PathVariable("drinkId") UUID drinkId, Model model) {
    var recipe = complexDrinkService.getRecipeForDrink(drinkId)
                                    .orElseGet(DrinkRecipe::new);
    model.addAttribute(DRINK_ID, drinkId)
         .addAttribute("recipe", recipe)
         .addAttribute(SAVED, false);
    return "admin_recipe";
  }

  @PostMapping
  public String adminComplexDrinkRecipe(@PathVariable("drinkId") UUID drinkId, @ModelAttribute DrinkRecipe recipe, Model model) {
    var drink = complexDrinkService.getDrinkById(drinkId)
                                   .orElseThrow(() -> new IllegalArgumentException(String.format("Drink ID %s not found!",
                                                                                                 drinkId.toString())));
    complexDrinkService.getRecipeForDrink(drinkId)
                       .map(DrinkRecipe::getComponents)
                       .ifPresent(recipe::setComponents);
    drink.setRecipe(recipe);
    var savedRecipe = complexDrinkService.addOrUpdateDrink(drink).getRecipe();
    model.addAttribute(DRINK_ID, drinkId)
         .addAttribute("recipe", savedRecipe)
         .addAttribute(SAVED, true);
    return "admin_recipe";
  }

  @GetMapping(path = "/components")
  public String adminRecipeComponents(@PathVariable("drinkId") UUID drinkId, Model model) {
    var recipeComponents = complexDrinkService.getRecipeForDrink(drinkId)
                                              .map(DrinkRecipe::getComponents);
    var components = getComponentsMap(recipeComponents);
    model.addAttribute(DRINK_ID, drinkId)
         .addAttribute("model", new ComponentsModel(components))
         .addAttribute(SAVED, false);
    return "admin_recipe_components";
  }

  private Map<String, Integer> getComponentsMap(java.util.Optional<java.util.List<RecipeComponent>> recipeComponents) {
    return simpleDrinkService.getDrinks().stream()
                             .collect(toMap(SimpleDrink::getName,
                                            simpleDrink -> recipeComponents
                                                .flatMap(compList -> compList.stream()
                                                                             .filter(comp -> comp.getComponent().getId()
                                                                                                 .equals(simpleDrink.getId()))
                                                                             .findFirst())
                                                .map(RecipeComponent::getQuantity)
                                                .orElse(0)));
  }

  @PostMapping(path = "/components")
  public String adminEditRecipeComponents(@PathVariable("drinkId") UUID drinkId,
                                          @ModelAttribute ComponentsModel componentsModel,
                                          Model model) {
    var drink = complexDrinkService.getDrinkById(drinkId)
                                   .orElseThrow(() -> new IllegalArgumentException(String.format("Drink ID %s not found!",
                                                                                                 drinkId.toString())));
    var components = componentsModel.getComponents().entrySet().stream()
                                    .map(this::getRecipeComponentFromEntry)
                                    .filter(comp -> comp.getQuantity() > 0)
                                    .collect(Collectors.toList());
    var recipe = complexDrinkService.getRecipeForDrink(drinkId)
                                    .orElseGet(DrinkRecipe::new);
    recipe.getComponents().clear();
    recipe.getComponents().addAll(components);
    drink.setRecipe(recipe);
    complexDrinkService.addOrUpdateDrink(drink);
    model.addAttribute(DRINK_ID, drinkId)
         .addAttribute("model", componentsModel)
         .addAttribute(SAVED, true);
    return "admin_recipe_components";
  }

  private RecipeComponent getRecipeComponentFromEntry(Map.Entry<String, Integer> entry) {
    var drink = simpleDrinkService.getDrinkByName(entry.getKey());
    var recipeComponent = new RecipeComponent();
    recipeComponent.setComponent(drink.orElseThrow());
    recipeComponent.setQuantity(entry.getValue());
    return recipeComponent;
  }
}
