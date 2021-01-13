package de.othr.sw.benjamineder.barmanagement.application.drink.service;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.Drink;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.Assert;

public abstract class DrinkService<T extends Drink> {

  private final JpaRepository<T, UUID> drinkRepository;

  protected DrinkService(JpaRepository<T, UUID> drinkRepository) {
    this.drinkRepository = drinkRepository;
  }

  public List<T> getDrinks() {
    return drinkRepository.findAll();
  }

  public T addOrUpdateDrink(T drink) {
    Assert.notNull(drink, "SimpleDrink must not be null!");
    return drinkRepository.save(drink);
  }

  public T deleteDrink(UUID drinkID) {
    Assert.notNull(drinkID, "ID of Drink must not be null!");
    var drinkToDelete = drinkRepository.findById(drinkID).orElseThrow(
        () -> new IllegalArgumentException(String.format("Drink with ID %s does not exist!", drinkID)));
    drinkRepository.delete(drinkToDelete);
    return drinkToDelete;
  }
}
