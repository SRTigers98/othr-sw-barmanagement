package de.othr.sw.benjamineder.barmanagement.application.web.admin;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.othr.sw.benjamineder.barmanagement.application.drink.entity.ComplexDrink;
import de.othr.sw.benjamineder.barmanagement.application.drink.entity.SimpleDrink;
import de.othr.sw.benjamineder.barmanagement.application.drink.service.ComplexDrinkService;
import de.othr.sw.benjamineder.barmanagement.application.drink.service.SimpleDrinkService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

@ExtendWith(MockitoExtension.class)
class AdminHomeControllerTest {

  @InjectMocks
  private AdminHomeController tested;
  @Mock
  private SimpleDrinkService  simpleDrinkService;
  @Mock
  private ComplexDrinkService complexDrinkService;

  @Test
  void adminHomeTest() {
    var model = mock(Model.class);
    var simpleDrinks = List.of(mock(SimpleDrink.class), mock(SimpleDrink.class), mock(SimpleDrink.class));
    var complexDrinks = List.of(mock(ComplexDrink.class), mock(ComplexDrink.class), mock(ComplexDrink.class));

    when(simpleDrinkService.getDrinks()).thenReturn(simpleDrinks);
    when(complexDrinkService.getDrinks()).thenReturn(complexDrinks);
    when(model.addAttribute("simpleDrinks", simpleDrinks)).thenReturn(model);
    when(model.addAttribute("complexDrinks", complexDrinks)).thenReturn(model);

    var result = tested.adminHome(model);

    assertThat(result, is("admin_home"));

    verify(model, times(1)).addAttribute("simpleDrinks", simpleDrinks);
    verify(model, times(1)).addAttribute("complexDrinks", complexDrinks);
  }
}
