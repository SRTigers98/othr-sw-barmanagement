package de.othr.sw.benjamineder.barmanagement.application.drink.dao;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.SimpleDrink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SimpleDrinkRepository extends JpaRepository<SimpleDrink, String> {

  Optional<SimpleDrink> findByName(String name);
}
