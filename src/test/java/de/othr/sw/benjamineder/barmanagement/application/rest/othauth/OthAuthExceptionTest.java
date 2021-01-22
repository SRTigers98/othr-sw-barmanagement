package de.othr.sw.benjamineder.barmanagement.application.rest.othauth;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

class OthAuthExceptionTest {

  @Test
  void constructorTest() {
    var nestedException = mock(Exception.class);
    var exceptionMessage = "An Error!";

    var result = new OthAuthException(exceptionMessage, nestedException);

    assertThat(result, is(notNullValue()));
    assertThat(result.getMessage(), is(exceptionMessage));
    assertThat(result.getCause(), is(nestedException));
  }
}
