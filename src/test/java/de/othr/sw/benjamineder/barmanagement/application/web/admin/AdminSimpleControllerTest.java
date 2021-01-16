package de.othr.sw.benjamineder.barmanagement.application.web.admin;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.SimpleDrink;
import de.othr.sw.benjamineder.barmanagement.application.drink.entity.SimpleDrinkType;
import de.othr.sw.benjamineder.barmanagement.application.drink.service.SimpleDrinkService;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

@ExtendWith(MockitoExtension.class)
class AdminSimpleControllerTest {

  @InjectMocks
  private AdminSimpleController tested;
  @Mock
  private SimpleDrinkService    simpleDrinkService;

  @Test
  void adminSimpleDrinkFormTest() {
    var drinkId = UUID.randomUUID();
    var model = mock(Model.class);

    var drink = mock(SimpleDrink.class);

    when(simpleDrinkService.getDrinkById(drinkId)).thenReturn(drink);
    when(model.addAttribute("drink", drink)).thenReturn(model);
    when(model.addAttribute("types", SimpleDrinkType.values())).thenReturn(model);
    when(model.addAttribute("saved", false)).thenReturn(model);

    var result = tested.adminSimpleDrinkForm(drinkId, model);

    assertThat(result, is("admin_simple"));

    verify(model, times(1)).addAttribute("drink", drink);
    verify(model, times(1)).addAttribute("types", SimpleDrinkType.values());
    verify(model, times(1)).addAttribute("saved", false);
  }

  @Test
  void adminEditSimpleDrinkTest() {
    var drink = mock(SimpleDrink.class);
    var model = mock(Model.class);

    var savedDrink = mock(SimpleDrink.class);

    when(simpleDrinkService.addOrUpdateDrink(drink)).thenReturn(savedDrink);
    when(model.addAttribute("drink", savedDrink)).thenReturn(model);
    when(model.addAttribute("types", SimpleDrinkType.values())).thenReturn(model);
    when(model.addAttribute("saved", true)).thenReturn(model);

    var result = tested.adminEditSimpleDrink(drink, model);

    assertThat(result, is("admin_simple"));

    verify(model, times(1)).addAttribute("drink", savedDrink);
    verify(model, times(1)).addAttribute("types", SimpleDrinkType.values());
    verify(model, times(1)).addAttribute("saved", true);
  }
}
