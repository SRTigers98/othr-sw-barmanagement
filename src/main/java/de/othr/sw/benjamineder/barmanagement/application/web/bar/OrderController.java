package de.othr.sw.benjamineder.barmanagement.application.web.bar;

import de.othr.sw.benjamineder.barmanagement.application.order.service.DrinkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/order")
public class OrderController {

  private final DrinkOrderService drinkOrderService;

  @Autowired
  public OrderController(DrinkOrderService drinkOrderService) {
    this.drinkOrderService = drinkOrderService;
  }

  @GetMapping
  public String drinkOrderOverview(Model model) {
    var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    model.addAttribute("drinkOrders", drinkOrderService.getDrinkOrders())
         .addAttribute("formatter", formatter)
         .addAttribute("totalRevenue", drinkOrderService.getDrinkOrdersRevenue());
    return "bar_order-overview";
  }
}
