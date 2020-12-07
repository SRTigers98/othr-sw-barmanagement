package de.othr.sw.benjamineder.barmanagement.application.simpledrink.service;

import de.othr.sw.benjamineder.barmanagement.application.simpledrink.dao.SimpleDrinkRepository;
import de.othr.sw.benjamineder.barmanagement.application.simpledrink.entity.SimpleDrink;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class SimpleDrinkService {

  private final SimpleDrinkRepository repository;

  @Autowired
  public SimpleDrinkService(SimpleDrinkRepository repository) {
    this.repository = repository;
  }

  public List<SimpleDrink> getSimpleDrinks() {
    return repository.findAll();
  }

  public SimpleDrink addOrUpdateSimpleDrink(SimpleDrink simpleDrink) {
    Assert.notNull(simpleDrink, "SimpleDrink must not be null!");
    return repository.save(simpleDrink);
  }

  public SimpleDrink deleteSimpleDrink(UUID drinkID) {
    Assert.notNull(drinkID, "ID of SimpleDrink must not be null!");
    var drinkToDelete = repository.findById(drinkID);
    repository.delete(drinkToDelete.orElseThrow(
        () -> new IllegalArgumentException(String.format("SimpleDrink with ID %s does not exist!", drinkID))));
    return drinkToDelete.get();
  }
}
