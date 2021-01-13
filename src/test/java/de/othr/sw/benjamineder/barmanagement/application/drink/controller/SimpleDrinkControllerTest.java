package de.othr.sw.benjamineder.barmanagement.application.drink.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.SimpleDrink;
import de.othr.sw.benjamineder.barmanagement.application.drink.service.SimpleDrinkService;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SimpleDrinkControllerTest {

  @InjectMocks
  private SimpleDrinkController tested;
  @Mock
  private SimpleDrinkService    simpleDrinkService;

  @Test
  void getSimpleDrinksTest() {
    var drinks = List.of(mock(SimpleDrink.class), mock(SimpleDrink.class), mock(SimpleDrink.class));

    when(simpleDrinkService.getSimpleDrinks()).thenReturn(drinks);

    var result = tested.getSimpleDrinks();

    assertThat(result, is(drinks));
  }

  @Test
  void putSimpleDrinkTest() {
    var drink = mock(SimpleDrink.class);
    var savedDrink = mock(SimpleDrink.class);

    when(simpleDrinkService.addOrUpdateSimpleDrink(drink)).thenReturn(savedDrink);

    var result = tested.putSimpleDrink(drink);

    assertThat(result, is(savedDrink));
  }

  @Test
  void deleteSimpleDrinkTest() {
    var drinkId = UUID.randomUUID();
    var deletedDrink = mock(SimpleDrink.class);

    when(simpleDrinkService.deleteSimpleDrink(drinkId)).thenReturn(deletedDrink);

    var result = tested.deleteSimpleDrink(drinkId);

    assertThat(result, is(deletedDrink));
  }
}
