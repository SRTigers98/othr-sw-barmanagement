package de.othr.sw.benjamineder.barmanagement.application.rest.warehouse;

import de.othr.kef41719.swwarehousedtos.OrderDto;
import de.othr.kef41719.swwarehousedtos.StockDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WarehouseServiceTest {

  private static final String CUSTOMER_NUMBER = "00000001";

  @InjectMocks
  private WarehouseService tested;
  @Mock
  private RestTemplate     restTemplate;

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(tested, "customerNumber", CUSTOMER_NUMBER);
  }

  @Test
  void orderFromWarehouseTest() {
    var firstID = UUID.randomUUID();
    var secondID = UUID.randomUUID();
    var orderPositions = Map.of(firstID, 42, secondID, 5);

    var orderDtoCaptor = ArgumentCaptor.forClass(OrderDto.class);

    when(restTemplate.postForEntity(eq("/api/orders"), orderDtoCaptor.capture(), eq(Object.class)))
        .thenReturn(ResponseEntity.ok().build());

    var result = tested.orderFromWarehouse(orderPositions);

    assertThat(result, is(true));

    var orderDto = orderDtoCaptor.getValue();
    assertThat(orderDto, is(notNullValue()));
    assertThat(orderDto.getDestinationAddress(), is(nullValue()));
    assertThat(orderDto.getCustomerNumber(), is(CUSTOMER_NUMBER));
    assertThat(orderDto.getPositions(), is(notNullValue()));
    var positionsDtoList = orderDto.getPositions();
    assertThat(positionsDtoList.size(), is(2));
    assertThat(positionsDtoList, hasItems(allOf(hasProperty("articleId", is(firstID.toString())),
                                                hasProperty("qty", is(42))),
                                          allOf(hasProperty("articleId", is(secondID.toString())),
                                                hasProperty("qty", is(5)))));
  }

  @Test
  void orderFromWarehouseBadRequestTest() {
    var firstID = UUID.randomUUID();
    var secondID = UUID.randomUUID();
    var orderPositions = Map.of(firstID, 42, secondID, 5);

    when(restTemplate.postForEntity(eq("/api/orders"), any(OrderDto.class), eq(Object.class)))
        .thenReturn(ResponseEntity.badRequest().build());

    var result = tested.orderFromWarehouse(orderPositions);

    assertThat(result, is(false));
  }

  @Test
  void getDrinkStocksTest() {
    var stock = mock(StockDto.class);
    var stocks = new StockDto[]{ stock, stock, stock };

    when(restTemplate.getForEntity(String.format("/api/stock?customernumber=%s", CUSTOMER_NUMBER), StockDto[].class))
        .thenReturn(ResponseEntity.ok(stocks));
    when(stock.getArticleId()).thenReturn("42", "99", "123");
    when(stock.getQty()).thenReturn(10, 20, 50);

    var result = tested.getDrinkStocks();

    assertThat(result, is(notNullValue()));
    assertThat(result, allOf(hasEntry("42", 10), hasEntry("99", 20), hasEntry("123", 50)));
  }
}
