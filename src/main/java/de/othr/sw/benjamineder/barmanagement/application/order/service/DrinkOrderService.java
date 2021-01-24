package de.othr.sw.benjamineder.barmanagement.application.order.service;

import de.othr.sw.benjamineder.barmanagement.application.order.dao.DrinkOrderRepository;
import de.othr.sw.benjamineder.barmanagement.application.order.entity.DrinkOrder;
import de.othr.sw.benjamineder.barmanagement.application.order.entity.OrderPosition;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrinkOrderService {

  private final DrinkOrderRepository drinkOrderRepository;

  @Autowired
  public DrinkOrderService(DrinkOrderRepository drinkOrderRepository) {
    this.drinkOrderRepository = drinkOrderRepository;
  }

  public List<DrinkOrder> getDrinkOrders() {
    return drinkOrderRepository.findAll();
  }

  public DrinkOrder order(List<OrderPosition> orderPositions) {
    var order = new DrinkOrder();
    order.setOrderPositions(orderPositions);
    return drinkOrderRepository.save(order);
  }

  public Double getDrinkOrdersRevenue() {
    return drinkOrderRepository.findAll()
                               .stream()
                               .mapToDouble(DrinkOrder::getPrice)
                               .sum();
  }
}
