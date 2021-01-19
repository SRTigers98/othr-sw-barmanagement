package de.othr.sw.benjamineder.barmanagement.application.drink.service;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.Drink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public abstract class DrinkService<T extends Drink> {

  protected final JpaRepository<T, UUID> drinkRepository;

  protected DrinkService(JpaRepository<T, UUID> drinkRepository) {
    this.drinkRepository = drinkRepository;
  }

  public List<T> getDrinks() {
    return drinkRepository.findAll();
  }

  public Optional<T> getDrinkById(UUID drinkId) {
    return drinkRepository.findById(drinkId);
  }

  public T addOrUpdateDrink(T drink) {
    Assert.notNull(drink, "Drink must not be null!");
    return drinkRepository.save(drink);
  }

  public T deleteDrink(UUID drinkId) {
    Assert.notNull(drinkId, "ID of Drink must not be null!");
    var drinkToDelete = drinkRepository.findById(drinkId)
                                       .orElseThrow(() -> this.drinkIdNotFoundException(drinkId));
    drinkRepository.delete(drinkToDelete);
    return drinkToDelete;
  }

  protected IllegalArgumentException drinkIdNotFoundException(UUID drinkId) {
    return new IllegalArgumentException(String.format("Drink with ID %s does not exist!", drinkId));
  }
}
