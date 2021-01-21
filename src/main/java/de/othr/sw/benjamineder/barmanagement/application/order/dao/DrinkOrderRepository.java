package de.othr.sw.benjamineder.barmanagement.application.order.dao;

import de.othr.sw.benjamineder.barmanagement.application.order.entity.DrinkOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrinkOrderRepository extends JpaRepository<DrinkOrder, String> {
  // no additional methods required
}
