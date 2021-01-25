package de.othr.sw.benjamineder.barmanagement.application.order.service;

import de.othr.sw.benjamineder.barmanagement.application.order.dao.DrinkOrderRepository;
import de.othr.sw.benjamineder.barmanagement.application.order.entity.DrinkOrder;
import de.othr.sw.benjamineder.barmanagement.application.order.entity.OrderPosition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import java.util.List;

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

  @Transactional(TxType.REQUIRED)
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
