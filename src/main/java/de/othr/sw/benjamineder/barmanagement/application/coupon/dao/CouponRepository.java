package de.othr.sw.benjamineder.barmanagement.application.coupon.dao;

import de.othr.sw.benjamineder.barmanagement.application.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, UUID> {
  // no additional methods required
}
