package de.othr.sw.benjamineder.barmanagement.application.drink.controller;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.ComplexDrink;
import de.othr.sw.benjamineder.barmanagement.application.drink.service.ComplexDrinkService;
import de.othr.sw.benjamineder.barmanagement.application.recipe.entity.DrinkRecipe;
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
class ComplexDrinkControllerTest {

  @InjectMocks
  private ComplexDrinkController tested;
  @Mock
  private ComplexDrinkService    complexDrinkService;

  @Test
  void getComplexDrinksTest() {
    var drinks = List.of(mock(ComplexDrink.class), mock(ComplexDrink.class), mock(ComplexDrink.class));

    when(complexDrinkService.getDrinks()).thenReturn(drinks);

    var result = tested.getComplexDrinks();

    assertThat(result, is(drinks));
  }

  @Test
  void putComplexDrinkTest() {
    var drink = mock(ComplexDrink.class);
    var savedDrink = mock(ComplexDrink.class);

    when(complexDrinkService.addOrUpdateDrink(drink)).thenReturn(savedDrink);

    var result = tested.putComplexDrink(drink);

    assertThat(result, is(savedDrink));
  }

  @Test
  void deleteComplexDrinkTest() {
    var drinkId = UUID.randomUUID().toString();
    var deletedDrink = mock(ComplexDrink.class);

    when(complexDrinkService.deleteDrink(drinkId)).thenReturn(deletedDrink);

    var result = tested.deleteComplexDrink(drinkId);

    assertThat(result, is(deletedDrink));
  }

  @Test
  void getComplexDrinkByIdTest() {
    var drinkId = UUID.randomUUID().toString();
    var drink = mock(ComplexDrink.class);

    when(complexDrinkService.getDrinkById(drinkId)).thenReturn(Optional.of(drink));

    var result = tested.getComplexDrinkById(drinkId);

    assertThat(result, is(drink));
  }

  @Test
  void getComplexDrinkByIdNotFoundTest() {
    var drinkId = UUID.randomUUID().toString();

    when(complexDrinkService.getDrinkById(drinkId)).thenReturn(Optional.empty());

    var result = tested.getComplexDrinkById(drinkId);

    assertThat(result, is(nullValue()));
  }

  @Test
  void getRecipeForDrinkTest() {
    var drinkId = UUID.randomUUID().toString();
    var drinkRecipe = mock(DrinkRecipe.class);

    when(complexDrinkService.getRecipeForDrink(drinkId)).thenReturn(Optional.of(drinkRecipe));

    var result = tested.getRecipeForDrink(drinkId);

    assertThat(result, is(drinkRecipe));
  }

  @Test
  void getRecipeForDrinkNoRecipeTest() {
    var drinkId = UUID.randomUUID().toString();
    var drinkRecipe = mock(DrinkRecipe.class);

    when(complexDrinkService.getRecipeForDrink(drinkId)).thenReturn(Optional.empty());

    var result = tested.getRecipeForDrink(drinkId);

    assertThat(result, is(nullValue()));
  }
}
