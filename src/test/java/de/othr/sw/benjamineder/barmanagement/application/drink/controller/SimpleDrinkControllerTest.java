package de.othr.sw.benjamineder.barmanagement.application.drink.controller;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.SimpleDrink;
import de.othr.sw.benjamineder.barmanagement.application.drink.service.SimpleDrinkService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SimpleDrinkControllerTest {

  @InjectMocks
  private SimpleDrinkController tested;
  @Mock
  private SimpleDrinkService    simpleDrinkService;

  @Test
  void getSimpleDrinksTest() {
    var drinks = List.of(mock(SimpleDrink.class), mock(SimpleDrink.class), mock(SimpleDrink.class));

    when(simpleDrinkService.getDrinks()).thenReturn(drinks);

    var result = tested.getSimpleDrinks();

    assertThat(result, is(drinks));
  }

  @Test
  void putSimpleDrinkTest() {
    var drink = mock(SimpleDrink.class);
    var savedDrink = mock(SimpleDrink.class);

    when(simpleDrinkService.addOrUpdateDrink(drink)).thenReturn(savedDrink);

    var result = tested.putSimpleDrink(drink);

    assertThat(result, is(savedDrink));
  }

  @Test
  void deleteSimpleDrinkTest() {
    var drinkId = UUID.randomUUID();
    var deletedDrink = mock(SimpleDrink.class);

    when(simpleDrinkService.deleteDrink(drinkId)).thenReturn(deletedDrink);

    var result = tested.deleteSimpleDrink(drinkId);

    assertThat(result, is(deletedDrink));
  }

  @Test
  void getSimpleDrinkByIdTest() {
    var drinkId = UUID.randomUUID();
    var drink = mock(SimpleDrink.class);

    when(simpleDrinkService.getDrinkById(drinkId)).thenReturn(Optional.of(drink));

    var result = tested.getSimpleDrinkById(drinkId);

    assertThat(result, is(drink));
  }

  @Test
  void getSimpleDrinkByIdNotFoundTest() {
    var drinkId = UUID.randomUUID();

    when(simpleDrinkService.getDrinkById(drinkId)).thenReturn(Optional.empty());

    var result = tested.getSimpleDrinkById(drinkId);

    assertThat(result, is(nullValue()));
  }
}
