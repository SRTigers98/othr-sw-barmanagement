package de.othr.sw.benjamineder.barmanagement.application.web.bar;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.ComplexDrink;
import de.othr.sw.benjamineder.barmanagement.application.drink.entity.SimpleDrink;
import de.othr.sw.benjamineder.barmanagement.application.drink.service.ComplexDrinkService;
import de.othr.sw.benjamineder.barmanagement.application.drink.service.SimpleDrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController {

  private final SimpleDrinkService  simpleDrinkService;
  private final ComplexDrinkService complexDrinkService;

  @Autowired
  public HomeController(SimpleDrinkService simpleDrinkService, ComplexDrinkService complexDrinkService) {
    this.simpleDrinkService = simpleDrinkService;
    this.complexDrinkService = complexDrinkService;
  }

  @GetMapping
  public String home(Model model) {
    var simpleDrinks = simpleDrinkService.getDrinks().stream()
                                         .collect(Collectors.groupingBy(SimpleDrink::getType));
    var complexDrinks = complexDrinkService.getDrinks().stream()
                                           .collect(Collectors.groupingBy(ComplexDrink::getType));
    model.addAttribute("simpleDrinks", simpleDrinks)
         .addAttribute("complexDrinks", complexDrinks);
    return "bar/bar_home";
  }
}
