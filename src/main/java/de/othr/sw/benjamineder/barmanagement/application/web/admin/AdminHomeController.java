package de.othr.sw.benjamineder.barmanagement.application.web.admin;

import de.othr.sw.benjamineder.barmanagement.application.drink.service.ComplexDrinkService;
import de.othr.sw.benjamineder.barmanagement.application.drink.service.SimpleDrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminHomeController {

  private final SimpleDrinkService  simpleDrinkService;
  private final ComplexDrinkService complexDrinkService;

  @Autowired
  public AdminHomeController(SimpleDrinkService simpleDrinkService, ComplexDrinkService complexDrinkService) {
    this.simpleDrinkService = simpleDrinkService;
    this.complexDrinkService = complexDrinkService;
  }

  @GetMapping
  public String adminHome(Model model) {
    model.addAttribute("simpleDrinks", simpleDrinkService.getDrinks())
         .addAttribute("complexDrinks", complexDrinkService.getDrinks());
    return "admin_home";
  }
}
