package de.othr.sw.benjamineder.barmanagement.application.web.admin;

import de.othr.sw.benjamineder.barmanagement.application.drink.service.SimpleDrinkService;
import de.othr.sw.benjamineder.barmanagement.application.rest.warehouse.WarehouseService;
import de.othr.sw.benjamineder.barmanagement.application.web.auth.BarAdminAccess;
import de.othr.sw.benjamineder.barmanagement.application.web.model.ReplenishmentModel;
import de.othr.sw.benjamineder.barmanagement.application.web.model.ReplenishmentPositionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/replenishment")
public class AdminReplenishmentController {

  private final SimpleDrinkService simpleDrinkService;
  private final WarehouseService   warehouseService;

  @Autowired
  public AdminReplenishmentController(SimpleDrinkService simpleDrinkService, WarehouseService warehouseService) {
    this.simpleDrinkService = simpleDrinkService;
    this.warehouseService = warehouseService;
  }

  @BarAdminAccess
  @GetMapping
  public String adminReplenishmentForm(Model model) {
    var drinkStocks = warehouseService.getDrinkStocks();
    var drinksMap = simpleDrinkService.getDrinks().stream()
                                      .map(drink -> new ReplenishmentPositionModel(drink.getName(),
                                                                                   drinkStocks.get(drink.getId()),
                                                                                   0))
                                      .sorted(Comparator.comparing(ReplenishmentPositionModel::getName))
                                      .collect(Collectors.toList());
    model.addAttribute("model", new ReplenishmentModel(drinksMap))
         .addAttribute("saved", false);
    return "admin/admin_replenishment";
  }

  @BarAdminAccess
  @PostMapping
  public String adminOrderReplenishment(@ModelAttribute ReplenishmentModel replenishment, Model model) {
    var replenishmentPositions = replenishment.getReplenishmentPositions().stream()
                                              .filter(pos -> pos.getReplenishmentQuantity() > 0)
                                              .collect(Collectors.toMap(pos -> simpleDrinkService.getDrinkByName(pos.getName())
                                                                                                 .orElseThrow().getId(),
                                                                        ReplenishmentPositionModel::getReplenishmentQuantity));
    warehouseService.orderFromWarehouse(replenishmentPositions);
    model.addAttribute("model", replenishment)
         .addAttribute("saved", true);
    return "admin/admin_replenishment";
  }
}
