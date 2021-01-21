package de.othr.sw.benjamineder.barmanagement.application.web.bar;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.Drink;
import de.othr.sw.benjamineder.barmanagement.application.drink.service.DrinkAllService;
import de.othr.sw.benjamineder.barmanagement.application.order.entity.OrderPosition;
import de.othr.sw.benjamineder.barmanagement.application.order.service.DrinkOrderService;
import de.othr.sw.benjamineder.barmanagement.application.web.model.OrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
public class OrderController {

  private final DrinkOrderService drinkOrderService;
  private final DrinkAllService   drinkAllService;

  @Autowired
  public OrderController(DrinkOrderService drinkOrderService, DrinkAllService drinkAllService) {
    this.drinkOrderService = drinkOrderService;
    this.drinkAllService = drinkAllService;
  }

  @GetMapping
  public String drinkOrderOverview(Model model) {
    var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    model.addAttribute("drinkOrders", drinkOrderService.getDrinkOrders())
         .addAttribute("formatter", formatter)
         .addAttribute("totalRevenue", drinkOrderService.getDrinkOrdersRevenue());
    return "bar_order-overview";
  }

  @GetMapping(path = "/new")
  public String drinkOrderForm(Model model) {
    var positions = drinkAllService.getAllDrinks().stream()
                                   .collect(Collectors.toMap(Drink::getName, drink -> 0));
    model.addAttribute("model", new OrderModel(positions))
         .addAttribute("saved", false);
    return "bar_order";
  }

  @PostMapping(path = "/new")
  public String drinkOrder(@ModelAttribute OrderModel order, Model model) {
    var orderPositions = order.getOrderPositions().entrySet().stream()
                              .filter(entry -> entry.getValue() > 0)
                              .map(entry -> {
                                var drink = drinkAllService.getDrinkByName(entry.getKey());
                                return drink.map(d -> createOrderPosition(d, entry.getValue()))
                                            .orElse(null);
                              })
                              .filter(Objects::nonNull)
                              .collect(Collectors.toList());
    drinkOrderService.order(orderPositions);
    model.addAttribute("model", order)
         .addAttribute("saved", true);
    return "bar_order";
  }

  private OrderPosition createOrderPosition(Drink drink, Integer quantity) {
    var position = new OrderPosition();
    position.setDrink(drink);
    position.setQuantity(quantity);
    return position;
  }
}
