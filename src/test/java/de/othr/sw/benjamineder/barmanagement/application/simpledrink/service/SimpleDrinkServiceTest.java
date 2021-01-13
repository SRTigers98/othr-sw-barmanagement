package de.othr.sw.benjamineder.barmanagement.application.simpledrink.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.othr.sw.benjamineder.barmanagement.application.drink.dao.SimpleDrinkRepository;
import de.othr.sw.benjamineder.barmanagement.application.drink.entity.SimpleDrink;
import de.othr.sw.benjamineder.barmanagement.application.drink.service.SimpleDrinkService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SimpleDrinkServiceTest {

  @InjectMocks
  private SimpleDrinkService    tested;
  @Mock
  private SimpleDrinkRepository simpleDrinkRepository;

  @Test
  void getSimpleDrinksTest() {
    var drinks = List.of(mock(SimpleDrink.class), mock(SimpleDrink.class), mock(SimpleDrink.class));

    when(simpleDrinkRepository.findAll()).thenReturn(drinks);

    var result = tested.getDrinks();

    assertThat(result, is(drinks));
  }

  @Test
  void addOrUpdateSimpleDrinkTest() {
    var drink = mock(SimpleDrink.class);
    var drinkFromDB = mock(SimpleDrink.class);

    when(simpleDrinkRepository.save(drink)).thenReturn(drinkFromDB);

    var result = tested.addOrUpdateDrink(drink);

    assertThat(result, is(drinkFromDB));
  }

  @Test
  void addOrUpdateSimpleDrinkNullTest() {
    assertThrows(IllegalArgumentException.class, () -> tested.addOrUpdateDrink(null));
  }

  @Test
  void deleteSimpleDrinkTest() {
    var id = UUID.randomUUID();

    var drink = mock(SimpleDrink.class);

    when(simpleDrinkRepository.findById(id)).thenReturn(Optional.of(drink));
    doNothing().when(simpleDrinkRepository).delete(drink);

    var result = tested.deleteDrink(id);

    assertThat(result, is(drink));
  }

  @Test
  void deleteSimpleDrinkExceptionTest() {
    var id = UUID.randomUUID();

    when(simpleDrinkRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(IllegalArgumentException.class, () -> tested.deleteDrink(id));
  }

  @Test
  void deleteSimpleDrinkNullTest() {
    assertThrows(IllegalArgumentException.class, () -> tested.deleteDrink(null));
  }
}
