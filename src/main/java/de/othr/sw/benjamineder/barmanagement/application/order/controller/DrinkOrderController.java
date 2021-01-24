package de.othr.sw.benjamineder.barmanagement.application.order.controller;

import de.othr.sw.benjamineder.barmanagement.application.order.entity.DrinkOrder;
import de.othr.sw.benjamineder.barmanagement.application.order.entity.OrderPosition;
import de.othr.sw.benjamineder.barmanagement.application.order.service.DrinkOrderService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order/drink")
public class DrinkOrderController {

  private final DrinkOrderService drinkOrderService;

  @Autowired
  public DrinkOrderController(DrinkOrderService drinkOrderService) {
    this.drinkOrderService = drinkOrderService;
  }

  @GetMapping(produces = "application/json")
  public List<DrinkOrder> getDrinkOrders() {
    return drinkOrderService.getDrinkOrders();
  }

  @PostMapping(consumes = "application/json", produces = "application/json")
  public DrinkOrder postDrinkOrder(@RequestBody List<OrderPosition> orderPositions) {
    return drinkOrderService.order(orderPositions);
  }

  @GetMapping(path = "/revenue")
  public Double getDrinkOrdersRevenue() {
    return drinkOrderService.getDrinkOrdersRevenue();
  }
}
