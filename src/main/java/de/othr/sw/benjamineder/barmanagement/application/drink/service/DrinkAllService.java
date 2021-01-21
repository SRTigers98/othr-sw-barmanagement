package de.othr.sw.benjamineder.barmanagement.application.drink.service;

import de.othr.sw.benjamineder.barmanagement.application.drink.dao.DrinkRepository;
import de.othr.sw.benjamineder.barmanagement.application.drink.entity.Drink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DrinkAllService {

  private final DrinkRepository drinkRepository;

  @Autowired
  public DrinkAllService(DrinkRepository drinkRepository) {
    this.drinkRepository = drinkRepository;
  }

  public List<Drink> getAllDrinks() {
    return drinkRepository.findAll();
  }

  public Optional<Drink> getDrinkByName(String name) {
    return drinkRepository.findByName(name);
  }
}
