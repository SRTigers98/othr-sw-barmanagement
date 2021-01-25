package de.othr.sw.benjamineder.barmanagement.application.drink.service;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.Drink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import java.util.List;
import java.util.Optional;

public abstract class DrinkService<T extends Drink> {

  protected final JpaRepository<T, String> drinkRepository;

  protected DrinkService(JpaRepository<T, String> drinkRepository) {
    this.drinkRepository = drinkRepository;
  }

  public List<T> getDrinks() {
    return drinkRepository.findAll();
  }

  public Optional<T> getDrinkById(String drinkId) {
    return drinkRepository.findById(drinkId);
  }

  @Transactional(TxType.REQUIRED)
  public T addOrUpdateDrink(T drink) {
    Assert.notNull(drink, "Drink must not be null!");
    return drinkRepository.save(drink);
  }

  @Transactional(TxType.REQUIRED)
  public T deleteDrink(String drinkId) {
    Assert.notNull(drinkId, "ID of Drink must not be null!");
    var drinkToDelete = drinkRepository.findById(drinkId)
                                       .orElseThrow(() -> this.drinkIdNotFoundException(drinkId));
    drinkRepository.delete(drinkToDelete);
    return drinkToDelete;
  }

  protected IllegalArgumentException drinkIdNotFoundException(String drinkId) {
    return new IllegalArgumentException(String.format("Drink with ID %s does not exist!", drinkId));
  }
}
