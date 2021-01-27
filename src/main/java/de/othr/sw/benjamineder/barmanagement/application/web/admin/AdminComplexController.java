package de.othr.sw.benjamineder.barmanagement.application.web.admin;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.ComplexDrink;
import de.othr.sw.benjamineder.barmanagement.application.drink.entity.ComplexDrinkType;
import de.othr.sw.benjamineder.barmanagement.application.drink.service.ComplexDrinkService;
import de.othr.sw.benjamineder.barmanagement.application.order.service.DrinkOrderService;
import de.othr.sw.benjamineder.barmanagement.application.web.auth.BarAdminAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/complex")
public class AdminComplexController {

  private static final String ADMIN_COMPLEX_SITE = "admin/admin_complex";

  private final ComplexDrinkService complexDrinkService;
  private final DrinkOrderService   drinkOrderService;

  @Autowired
  public AdminComplexController(ComplexDrinkService complexDrinkService, DrinkOrderService drinkOrderService) {
    this.complexDrinkService = complexDrinkService;
    this.drinkOrderService = drinkOrderService;
  }

  @BarAdminAccess
  @GetMapping(path = "/new")
  public String adminComplexDrinkNew(Model model) {
    configureAdminComplexModel(model, new ComplexDrink(), false, true, false);
    return ADMIN_COMPLEX_SITE;
  }

  @BarAdminAccess
  @GetMapping(path = "/{drinkId}")
  public String adminComplexDrinkForm(@PathVariable("drinkId") String drinkId, Model model) {
    var drink = complexDrinkService.getDrinkById(drinkId)
                                   .orElseThrow(() -> new IllegalArgumentException(String.format("Drink ID %s not found!",
                                                                                                 drinkId)));
    configureAdminComplexModel(model, drink, false, false, isDrinkDeletable(drinkId));
    return ADMIN_COMPLEX_SITE;
  }

  @BarAdminAccess
  @PostMapping(path = "/{drinkId}")
  public String adminEditComplexDrink(@ModelAttribute ComplexDrink complexDrink, Model model) {
    complexDrinkService.getRecipeForDrink(complexDrink.getId())
                       .ifPresent(complexDrink::setRecipe);
    configureAdminComplexModel(model, complexDrinkService.addOrUpdateDrink(complexDrink), true, false,
                               isDrinkDeletable(complexDrink.getId()));
    return ADMIN_COMPLEX_SITE;
  }

  @BarAdminAccess
  @PostMapping(path = "/{drinkId}/delete")
  public String adminDeleteComplexDrink(@PathVariable("drinkId") String drinkId) {
    complexDrinkService.deleteDrink(drinkId);
    return "redirect:/admin";
  }

  private void configureAdminComplexModel(Model model, ComplexDrink drink, boolean saved, boolean newDrink,
                                          boolean deletable) {
    model.addAttribute("drink", drink)
         .addAttribute("types", ComplexDrinkType.values())
         .addAttribute("saved", saved)
         .addAttribute("newDrink", newDrink)
         .addAttribute("deletable", deletable);
  }

  private boolean isDrinkDeletable(String drinkId) {
    return drinkOrderService.getDrinkOrders().stream()
                            .flatMap(order -> order.getOrderPositions().stream())
                            .noneMatch(position -> position.getDrink().getId().equals(drinkId));
  }
}
