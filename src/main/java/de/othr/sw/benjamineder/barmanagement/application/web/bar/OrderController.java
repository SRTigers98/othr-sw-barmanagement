package de.othr.sw.benjamineder.barmanagement.application.web.bar;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.Drink;
import de.othr.sw.benjamineder.barmanagement.application.drink.service.DrinkAllService;
import de.othr.sw.benjamineder.barmanagement.application.order.entity.DrinkOrder;
import de.othr.sw.benjamineder.barmanagement.application.order.entity.OrderPosition;
import de.othr.sw.benjamineder.barmanagement.application.order.service.DrinkOrderService;
import de.othr.sw.benjamineder.barmanagement.application.util.MapCollectors;
import de.othr.sw.benjamineder.barmanagement.application.web.auth.BarUserAccess;
import de.othr.sw.benjamineder.barmanagement.application.web.model.OrderModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
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

  @BarUserAccess
  @GetMapping
  public String drinkOrderOverview(Model model) {
    var drinkOrders = drinkOrderService.getDrinkOrders().stream()
                                       .sorted(Comparator.comparing(DrinkOrder::getTimestamp).reversed())
                                       .collect(Collectors.toList());
    drinkOrders.forEach(order -> order.getOrderPositions().sort(Comparator.comparing(pos -> pos.getDrink().getName())));
    var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    model.addAttribute("drinkOrders", drinkOrders)
         .addAttribute("formatter", formatter)
         .addAttribute("totalRevenue", drinkOrderService.getDrinkOrdersRevenue());
    return "bar/bar_order-overview";
  }

  @BarUserAccess
  @GetMapping(path = "/new")
  public String drinkOrderForm(Model model) {
    var positions = drinkAllService.getAllDrinks().stream()
                                   .sorted(Comparator.comparing(Drink::getName))
                                   .collect(MapCollectors.toKeySortedMap(Drink::getName, drink -> 0));
    model.addAttribute("model", new OrderModel(positions))
         .addAttribute("saved", false);
    return "bar/bar_order";
  }

  @BarUserAccess
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
    return "bar/bar_order";
  }

  private OrderPosition createOrderPosition(Drink drink, Integer quantity) {
    var position = new OrderPosition();
    position.setDrink(drink);
    position.setQuantity(quantity);
    return position;
  }
}
