package de.othr.sw.benjamineder.barmanagement.application.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class MapCollectors {

  private MapCollectors() {
    // Util Class, not required
  }

  public static <T, K, V> Collector<T, ?, Map<K, V>> toKeySortedMap(Function<T, K> keyMapper, Function<T, V> valueMapper) {
    return Collectors.toMap(keyMapper, valueMapper, (oldVal, newVal) -> oldVal, LinkedHashMap::new);
  }
}
