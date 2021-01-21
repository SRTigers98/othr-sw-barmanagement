package de.othr.sw.benjamineder.barmanagement.application.web.admin;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.SimpleDrink;
import de.othr.sw.benjamineder.barmanagement.application.drink.entity.SimpleDrinkType;
import de.othr.sw.benjamineder.barmanagement.application.drink.service.SimpleDrinkService;
import de.othr.sw.benjamineder.barmanagement.application.rest.drinksondemand.DrinksOnDemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/simple")
public class AdminSimpleController {

  private final SimpleDrinkService    simpleDrinkService;
  private final DrinksOnDemandService drinksOnDemandService;

  @Autowired
  public AdminSimpleController(SimpleDrinkService simpleDrinkService, DrinksOnDemandService drinksOnDemandService) {
    this.simpleDrinkService = simpleDrinkService;
    this.drinksOnDemandService = drinksOnDemandService;
  }

  @GetMapping(path = "/{drinkId}")
  public String adminSimpleDrinkForm(@PathVariable("drinkId") String drinkId, Model model) {
    var drink = simpleDrinkService.getDrinkById(drinkId)
                                  .orElseGet(() -> drinksOnDemandService.getArticleAsSimpleDrink(drinkId));
    model.addAttribute("drink", drink)
         .addAttribute("types", SimpleDrinkType.values())
         .addAttribute("saved", false);
    return "admin_simple";
  }

  @PostMapping(path = "/{drinkId}")
  public String adminEditSimpleDrink(@ModelAttribute SimpleDrink drink, Model model) {
    var savedDrink = simpleDrinkService.addOrUpdateDrink(drink);
    model.addAttribute("drink", savedDrink)
         .addAttribute("types", SimpleDrinkType.values())
         .addAttribute("saved", true);
    return "admin_simple";
  }

  @PostMapping(path = "/{drinkId}/delete")
  public String adminDeleteSimpleDrink(@PathVariable("drinkId") String drinkId) {
    simpleDrinkService.deleteDrink(drinkId);
    return "redirect:/admin";
  }
}
