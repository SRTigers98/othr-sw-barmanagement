package de.othr.sw.benjamineder.barmanagement.application.order.dao;

import de.othr.sw.benjamineder.barmanagement.application.order.entity.CouponOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponOrderRepository extends JpaRepository<CouponOrder, String> {
  // no additional methods required
}
