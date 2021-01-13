package de.othr.sw.benjamineder.barmanagement.application.drink.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.othr.sw.benjamineder.barmanagement.application.drink.dao.ComplexDrinkRepository;
import de.othr.sw.benjamineder.barmanagement.application.drink.entity.ComplexDrink;
import de.othr.sw.benjamineder.barmanagement.application.recipe.entity.DrinkRecipe;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ComplexDrinkServiceTest {

  @InjectMocks
  private ComplexDrinkService    tested;
  @Mock
  private ComplexDrinkRepository complexDrinkRepository;

  @Test
  void getComplexDrinksTest() {
    var drinks = List.of(mock(ComplexDrink.class), mock(ComplexDrink.class), mock(ComplexDrink.class));

    when(complexDrinkRepository.findAll()).thenReturn(drinks);

    var result = tested.getDrinks();

    assertThat(result, is(drinks));
  }

  @Test
  void getDrinkByIdTest() {
    var drinkId = UUID.randomUUID();
    var drink = mock(ComplexDrink.class);

    when(complexDrinkRepository.findById(drinkId)).thenReturn(Optional.of(drink));

    var result = tested.getDrinkById(drinkId);

    assertThat(result, is(drink));
  }

  @Test
  void getDrinkByIdExceptionTest() {
    var drinkId = UUID.randomUUID();

    when(complexDrinkRepository.findById(drinkId)).thenReturn(Optional.empty());

    assertThrows(IllegalArgumentException.class, () -> tested.getDrinkById(drinkId));
  }

  @Test
  void addOrUpdateComplexDrinkTest() {
    var drink = mock(ComplexDrink.class);
    var drinkFromDB = mock(ComplexDrink.class);

    when(complexDrinkRepository.save(drink)).thenReturn(drinkFromDB);

    var result = tested.addOrUpdateDrink(drink);

    assertThat(result, is(drinkFromDB));
  }

  @Test
  void addOrUpdateComplexDrinkNullTest() {
    assertThrows(IllegalArgumentException.class, () -> tested.addOrUpdateDrink(null));
  }

  @Test
  void deleteComplexDrinkTest() {
    var id = UUID.randomUUID();

    var drink = mock(ComplexDrink.class);

    when(complexDrinkRepository.findById(id)).thenReturn(Optional.of(drink));
    doNothing().when(complexDrinkRepository).delete(drink);

    var result = tested.deleteDrink(id);

    assertThat(result, is(drink));
  }

  @Test
  void deleteComplexDrinkExceptionTest() {
    var id = UUID.randomUUID();

    when(complexDrinkRepository.findById(id)).thenReturn(Optional.empty());

    assertThrows(IllegalArgumentException.class, () -> tested.deleteDrink(id));
  }

  @Test
  void deleteComplexDrinkNullTest() {
    assertThrows(IllegalArgumentException.class, () -> tested.deleteDrink(null));
  }

  @Test
  void getRecipeForDrinkTest() {
    var drinkId = UUID.randomUUID();
    var drink = mock(ComplexDrink.class);
    var drinkRecipe = mock(DrinkRecipe.class);

    when(complexDrinkRepository.findById(drinkId)).thenReturn(Optional.of(drink));
    when(drink.getRecipe()).thenReturn(drinkRecipe);

    var result = tested.getRecipeForDrink(drinkId);

    assertThat(result, is(drinkRecipe));
  }

  @Test
  void getRecipeForDrinkIllegalArgumentTest() {
    var drinkId = UUID.randomUUID();

    when(complexDrinkRepository.findById(drinkId)).thenReturn(Optional.empty());

    assertThrows(IllegalArgumentException.class, () -> tested.getRecipeForDrink(drinkId));
  }
}
