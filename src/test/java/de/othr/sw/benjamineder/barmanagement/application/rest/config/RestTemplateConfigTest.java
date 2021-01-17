package de.othr.sw.benjamineder.barmanagement.application.rest.config;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class RestTemplateConfigTest {

  @InjectMocks
  private RestTemplateConfig tested;

  @Test
  void warehouseRestTemplateTest() {
    var warehouseURL = "http://test.warehouse.org";
    ReflectionTestUtils.setField(tested, "warehouseURL", warehouseURL);

    var result = tested.warehouseRestTemplate();

    assertThat(result, is(notNullValue()));
    assertThat(result.getUriTemplateHandler(), is(notNullValue()));
    var rootURI = (String) ReflectionTestUtils.getField(result.getUriTemplateHandler(), "rootUri");
    assertThat(rootURI, is(warehouseURL));
  }
}
