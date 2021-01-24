package de.othr.sw.benjamineder.barmanagement.application.drink.service;

import de.othr.sw.benjamineder.barmanagement.application.drink.dao.SimpleDrinkRepository;
import de.othr.sw.benjamineder.barmanagement.application.drink.entity.SimpleDrink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SimpleDrinkService extends DrinkService<SimpleDrink> {

  private final SimpleDrinkRepository simpleDrinkRepository;

  @Autowired
  public SimpleDrinkService(SimpleDrinkRepository simpleDrinkRepository) {
    super(simpleDrinkRepository);
    this.simpleDrinkRepository = simpleDrinkRepository;
  }

  public Optional<SimpleDrink> getDrinkByName(String name) {
    return simpleDrinkRepository.findByName(name);
  }
}
