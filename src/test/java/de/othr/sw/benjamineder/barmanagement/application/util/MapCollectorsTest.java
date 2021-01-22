package de.othr.sw.benjamineder.barmanagement.application.util;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInRelativeOrder;

class MapCollectorsTest {

  @Test
  void toKeySortedMapTest() {
    var map = Map.of(2, "second", 3, "third", 1, "first");

    var result = map.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .collect(MapCollectors.toKeySortedMap(Map.Entry::getKey, Map.Entry::getValue));

    assertThat(result, is(notNullValue()));
    assertThat(result.entrySet(), containsInRelativeOrder(
        equalTo(Map.entry(1, "first")),
        equalTo(Map.entry(2, "second")),
        equalTo(Map.entry(3, "third"))
    ));
  }
}
