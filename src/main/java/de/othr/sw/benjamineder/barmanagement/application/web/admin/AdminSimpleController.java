package de.othr.sw.benjamineder.barmanagement.application.web.admin;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.SimpleDrink;
import de.othr.sw.benjamineder.barmanagement.application.drink.entity.SimpleDrinkType;
import de.othr.sw.benjamineder.barmanagement.application.drink.service.SimpleDrinkService;
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
@RequestMapping("/admin/simple")
public class AdminSimpleController {

  private final SimpleDrinkService simpleDrinkService;

  @Autowired
  public AdminSimpleController(SimpleDrinkService simpleDrinkService) {
    this.simpleDrinkService = simpleDrinkService;
  }

  @GetMapping(path = "/{drinkId}")
  public String adminSimpleDrinkForm(@PathVariable("drinkId") UUID drinkId, Model model) {
    model.addAttribute("drink", simpleDrinkService.getDrinkById(drinkId))
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
}
