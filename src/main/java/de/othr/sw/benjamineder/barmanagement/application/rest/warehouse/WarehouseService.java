package de.othr.sw.benjamineder.barmanagement.application.rest.warehouse;

import de.othr.kef41719.swwarehousedtos.OrderDto;
import de.othr.kef41719.swwarehousedtos.PositionDto;
import de.othr.kef41719.swwarehousedtos.StockDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WarehouseService {

  @Value("${othr.warehouse.customer-number}")
  private       String       customerNumber;
  private final RestTemplate restTemplate;

  @Autowired
  public WarehouseService(@Qualifier("warehouse") RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public boolean orderFromWarehouse(Map<UUID, Integer> orderPositions) {
    var positionDtoList = orderPositions.entrySet().stream()
                                        .map(entry -> new PositionDto(entry.getKey().toString(), entry.getValue()))
                                        .collect(Collectors.toList());
    var orderDto = new OrderDto(null, customerNumber, positionDtoList);
    var response = restTemplate.postForEntity("/api/orders", orderDto, Object.class);
    return response.getStatusCode().is2xxSuccessful();
  }

  public Map<String, Integer> getDrinkStocks() {
    var stocks = restTemplate.getForEntity(String.format("/api/stock?customernumber=%s", customerNumber), StockDto[].class)
                             .getBody();
    Assert.notNull(stocks, "Stocks must not be null!");
    return Arrays.stream(stocks)
                 .collect(Collectors.toMap(StockDto::getArticleId, StockDto::getQty));
  }
}
