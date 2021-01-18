package de.othr.sw.benjamineder.barmanagement.application.web.admin;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.ComplexDrink;
import de.othr.sw.benjamineder.barmanagement.application.drink.entity.ComplexDrinkType;
import de.othr.sw.benjamineder.barmanagement.application.drink.service.ComplexDrinkService;
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
@RequestMapping("/admin/complex")
public class AdminComplexController {

  private static final String ADMIN_COMPLEX_SITE = "admin_complex";

  private final ComplexDrinkService complexDrinkService;

  @Autowired
  public AdminComplexController(ComplexDrinkService complexDrinkService) {
    this.complexDrinkService = complexDrinkService;
  }

  @GetMapping(path = "/new")
  public String adminComplexDrinkNew(Model model) {
    configureAdminComplexModel(model, new ComplexDrink(), false, true);
    return ADMIN_COMPLEX_SITE;
  }

  @GetMapping(path = "/{drinkId}")
  public String adminComplexDrinkForm(@PathVariable("drinkId") UUID drinkId, Model model) {
    configureAdminComplexModel(model, complexDrinkService.getDrinkById(drinkId), false, false);
    return ADMIN_COMPLEX_SITE;
  }

  @PostMapping(path = "/{drinkId}")
  public String adminEditComplexDrink(@ModelAttribute ComplexDrink complexDrink, Model model) {
    complexDrinkService.getRecipeForDrink(complexDrink.getId())
                       .ifPresent(complexDrink::setRecipe);
    configureAdminComplexModel(model, complexDrinkService.addOrUpdateDrink(complexDrink), true, false);
    return ADMIN_COMPLEX_SITE;
  }

  private void configureAdminComplexModel(Model model, ComplexDrink drink, boolean saved, boolean newDrink) {
    model.addAttribute("drink", drink)
         .addAttribute("types", ComplexDrinkType.values())
         .addAttribute("saved", saved)
         .addAttribute("newDrink", newDrink);
  }
}
