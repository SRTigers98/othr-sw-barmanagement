package de.othr.sw.benjamineder.barmanagement.application.drink.dao;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.Drink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, UUID> {

  Optional<Drink> findByName(String name);
}
