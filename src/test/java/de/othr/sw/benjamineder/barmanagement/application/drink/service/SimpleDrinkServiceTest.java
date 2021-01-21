package de.othr.sw.benjamineder.barmanagement.application.drink.service;

import de.othr.sw.benjamineder.barmanagement.application.drink.dao.SimpleDrinkRepository;
import de.othr.sw.benjamineder.barmanagement.application.drink.entity.SimpleDrink;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
  void getDrinkByIdTest() {
    var drinkId = UUID.randomUUID().toString();
    var drink = mock(SimpleDrink.class);

    when(simpleDrinkRepository.findById(drinkId)).thenReturn(Optional.of(drink));

    var result = tested.getDrinkById(drinkId);

    assertThat(result.isPresent(), is(true));
    assertThat(result.get(), is(drink));
  }

  @Test
  void getDrinkByIdNotFoundTest() {
    var drinkId = UUID.randomUUID().toString();

    when(simpleDrinkRepository.findById(drinkId)).thenReturn(Optional.empty());

    var result = tested.getDrinkById(drinkId);

    assertThat(result.isPresent(), is(false));
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
    var id = UUID.randomUUID().toString();

    var drink = mock(SimpleDrink.class);

    when(simpleDrinkRepository.findById(id)).thenReturn(Optional.of(drink));
    doNothing().when(simpleDrinkRepository).delete(drink);

    var result = tested.deleteDrink(id);

    assertThat(result, is(drink));
  }

  @Test
  void deleteSimpleDrinkExceptionTest() {
    var id = UUID.randomUUID().toString();

    when(simpleDrinkRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(IllegalArgumentException.class, () -> tested.deleteDrink(id));
  }

  @Test
  void deleteSimpleDrinkNullTest() {
    assertThrows(IllegalArgumentException.class, () -> tested.deleteDrink(null));
  }

  @Test
  void getDrinkByNameTest() {
    var name = "Pan Galactic Gargle Blaster";

    var panGalacticGargleBlaster = mock(SimpleDrink.class);

    when(simpleDrinkRepository.findByName(name)).thenReturn(Optional.of(panGalacticGargleBlaster));

    var result = tested.getDrinkByName(name);

    assertThat(result.isPresent(), is(true));
    assertThat(result.get(), is(panGalacticGargleBlaster));
  }

  @Test
  void getDrinkByNameNotFoundTest() {
    var name = "xxx";

    when(simpleDrinkRepository.findByName(name)).thenReturn(Optional.empty());

    var result = tested.getDrinkByName(name);

    assertThat(result.isPresent(), is(false));
  }
}
