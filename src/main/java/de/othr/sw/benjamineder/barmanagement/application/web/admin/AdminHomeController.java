package de.othr.sw.benjamineder.barmanagement.application.web.admin;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.Drink;
import de.othr.sw.benjamineder.barmanagement.application.drink.service.ComplexDrinkService;
import de.othr.sw.benjamineder.barmanagement.application.drink.service.SimpleDrinkService;
import de.othr.sw.benjamineder.barmanagement.application.rest.warehouse.WarehouseService;
import de.othr.sw.benjamineder.barmanagement.application.web.auth.BarAdminAccess;
import de.othr.sw.benjamineder.barmanagement.application.web.model.DrinkModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminHomeController {

  private final SimpleDrinkService  simpleDrinkService;
  private final ComplexDrinkService complexDrinkService;
  private final WarehouseService    warehouseService;

  @Autowired
  public AdminHomeController(SimpleDrinkService simpleDrinkService, ComplexDrinkService complexDrinkService,
                             WarehouseService warehouseService) {
    this.simpleDrinkService = simpleDrinkService;
    this.complexDrinkService = complexDrinkService;
    this.warehouseService = warehouseService;
  }

  @BarAdminAccess
  @GetMapping
  public String adminHome(Model model) {
    model.addAttribute("simpleDrinks", mapDrinks(simpleDrinkService.getDrinks()))
         .addAttribute("complexDrinks", mapDrinks(complexDrinkService.getDrinks()));
    return "admin/admin_home";
  }

  private List<DrinkModel> mapDrinks(List<? extends Drink> drinks) {
    var stocks = warehouseService.getDrinkStocks();
    return drinks.stream()
                 .map(drink -> new DrinkModel(drink.getId(),
                                              drink.getName(),
                                              stocks.getOrDefault(drink.getId(), 0)))
                 .collect(Collectors.toList());
  }
}
