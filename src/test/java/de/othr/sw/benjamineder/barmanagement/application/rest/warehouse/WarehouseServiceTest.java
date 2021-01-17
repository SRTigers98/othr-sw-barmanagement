package de.othr.sw.benjamineder.barmanagement.application.rest.warehouse;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.othr.kef41719.swwarehousedtos.OrderDto;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

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
    var responseEntity = mock(ResponseEntity.class);

    when(restTemplate.postForEntity(eq("/api/orders"), orderDtoCaptor.capture(), eq(Object.class)))
        .thenReturn(responseEntity);
    when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);

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

    var responseEntity = mock(ResponseEntity.class);

    when(restTemplate.postForEntity(eq("/api/orders"), any(OrderDto.class), eq(Object.class)))
        .thenReturn(responseEntity);
    when(responseEntity.getStatusCode()).thenReturn(HttpStatus.BAD_REQUEST);

    var result = tested.orderFromWarehouse(orderPositions);

    assertThat(result, is(false));
  }
}
