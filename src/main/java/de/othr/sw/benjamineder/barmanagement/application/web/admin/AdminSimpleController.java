package de.othr.sw.benjamineder.barmanagement.application.web.admin;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.SimpleDrink;
import de.othr.sw.benjamineder.barmanagement.application.drink.entity.SimpleDrinkType;
import de.othr.sw.benjamineder.barmanagement.application.drink.service.ComplexDrinkService;
import de.othr.sw.benjamineder.barmanagement.application.drink.service.SimpleDrinkService;
import de.othr.sw.benjamineder.barmanagement.application.order.service.DrinkOrderService;
import de.othr.sw.benjamineder.barmanagement.application.rest.drinksondemand.DrinksOnDemandService;
import de.othr.sw.benjamineder.barmanagement.application.web.auth.BarAdminAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/simple")
public class AdminSimpleController {

  private final SimpleDrinkService    simpleDrinkService;
  private final DrinksOnDemandService drinksOnDemandService;
  private final ComplexDrinkService   complexDrinkService;
  private final DrinkOrderService     drinkOrderService;

  @Autowired
  public AdminSimpleController(SimpleDrinkService simpleDrinkService, DrinksOnDemandService drinksOnDemandService,
                               ComplexDrinkService complexDrinkService, DrinkOrderService drinkOrderService) {
    this.simpleDrinkService = simpleDrinkService;
    this.drinksOnDemandService = drinksOnDemandService;
    this.complexDrinkService = complexDrinkService;
    this.drinkOrderService = drinkOrderService;
  }

  @BarAdminAccess
  @GetMapping(path = "/{drinkId}")
  public String adminSimpleDrinkForm(@PathVariable("drinkId") String drinkId, Model model) {
    var drink = simpleDrinkService.getDrinkById(drinkId)
                                  .orElseGet(() -> drinksOnDemandService.getArticleAsSimpleDrink(drinkId));
    model.addAttribute("drink", drink)
         .addAttribute("types", SimpleDrinkType.values())
         .addAttribute("saved", false)
         .addAttribute("deletable", isDrinkDeletable(drinkId));
    return "admin/admin_simple";
  }

  @BarAdminAccess
  @PostMapping(path = "/{drinkId}")
  public String adminEditSimpleDrink(@ModelAttribute SimpleDrink drink, Model model) {
    var savedDrink = simpleDrinkService.addOrUpdateDrink(drink);
    model.addAttribute("drink", savedDrink)
         .addAttribute("types", SimpleDrinkType.values())
         .addAttribute("saved", true)
         .addAttribute("deletable", isDrinkDeletable(drink.getId()));
    return "admin/admin_simple";
  }

  @BarAdminAccess
  @PostMapping(path = "/{drinkId}/delete")
  public String adminDeleteSimpleDrink(@PathVariable("drinkId") String drinkId) {
    simpleDrinkService.deleteDrink(drinkId);
    return "redirect:/admin";
  }

  private boolean isDrinkDeletable(String drinkId) {
    var isDrinkPresent = simpleDrinkService.getDrinkById(drinkId).isPresent();
    var isRecipeComponent = complexDrinkService.getDrinks().stream()
                                               .flatMap(drink -> drink.getRecipe().getComponents().stream())
                                               .anyMatch(component -> component.getComponent().getId().equals(drinkId));
    var isOrdered = drinkOrderService.getDrinkOrders().stream()
                                     .flatMap(order -> order.getOrderPositions().stream())
                                     .anyMatch(position -> position.getDrink().getId().equals(drinkId));
    return isDrinkPresent && !(isRecipeComponent || isOrdered);
  }
}
