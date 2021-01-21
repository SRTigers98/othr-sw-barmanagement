package de.othr.sw.benjamineder.barmanagement.application.drink.service;

import de.othr.sw.benjamineder.barmanagement.application.drink.dao.DrinkRepository;
import de.othr.sw.benjamineder.barmanagement.application.drink.entity.Drink;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DrinkAllServiceTest {

  @InjectMocks
  private DrinkAllService tested;
  @Mock
  private DrinkRepository drinkRepository;

  @Test
  void getAllDrinksTest() {
    var drinks = List.of(mock(Drink.class), mock(Drink.class), mock(Drink.class));

    when(drinkRepository.findAll()).thenReturn(drinks);

    var result = tested.getAllDrinks();

    assertThat(result, is(drinks));
  }

  @Test
  void getDrinkByNameTest() {
    var name = "Rischal";

    var drink = mock(Drink.class);

    when(drinkRepository.findByName(name)).thenReturn(Optional.of(drink));

    var result = tested.getDrinkByName(name);

    assertThat(result.isPresent(), is(true));
    assertThat(result.get(), is(drink));
  }

  @Test
  void getDrinkByNameNotFoundTest() {
    var name = "Rischal";

    when(drinkRepository.findByName(name)).thenReturn(Optional.empty());

    var result = tested.getDrinkByName(name);

    assertThat(result.isPresent(), is(false));
  }
}
