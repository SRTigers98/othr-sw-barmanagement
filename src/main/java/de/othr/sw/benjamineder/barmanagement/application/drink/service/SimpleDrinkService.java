package de.othr.sw.benjamineder.barmanagement.application.drink.service;

import de.othr.sw.benjamineder.barmanagement.application.drink.dao.SimpleDrinkRepository;
import de.othr.sw.benjamineder.barmanagement.application.drink.entity.SimpleDrink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleDrinkService extends DrinkService<SimpleDrink> {

  @Autowired
  public SimpleDrinkService(SimpleDrinkRepository simpleDrinkRepository) {
    super(simpleDrinkRepository);
  }
}
