package de.othr.sw.benjamineder.barmanagement.application.simpledrink.dao;

import de.othr.sw.benjamineder.barmanagement.application.simpledrink.entity.SimpleDrink;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleDrinkRepository extends JpaRepository<SimpleDrink, UUID> {
  // no additional methods required
}
