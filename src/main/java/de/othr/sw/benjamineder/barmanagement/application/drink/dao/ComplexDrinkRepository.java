package de.othr.sw.benjamineder.barmanagement.application.drink.dao;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.ComplexDrink;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComplexDrinkRepository extends JpaRepository<ComplexDrink, UUID> {
  // no additional methods required
}
