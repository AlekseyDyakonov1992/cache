import cache.lru.LRUCache;
import cache.MyCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LRUCacheTest {
    @Test
    public void checkEvictionStrategyByCapacity() {
        MyCache<String, Integer> cache = new LRUCache.Builder<String, Integer>(10).build();
        IntStream.range(0, 20).forEach(i -> cache.put(String.valueOf(i), i));

        Assertions.assertTrue(IntStream.range(0, 10).noneMatch(i -> cache.get(String.valueOf(i)).isPresent()));
    }

    @Test
    public void checkEvictionStrategyByLatestElement() {
        MyCache<String, Integer> cache = new LRUCache.Builder<String, Integer>(3).build();
        IntStream.range(0, 3).forEach(i -> cache.put(String.valueOf(i), i));

        Assertions.assertEquals(2, cache.get("2").get());
        cache.put("3", 3);
        Assertions.assertTrue(IntStream.range(1, 4).allMatch(i -> cache.get(String.valueOf(i)).isPresent()));
    }

    @Test
    public void checkEvictionStrategyByFirstElement() {
        MyCache<String, Integer> cache = new LRUCache.Builder<String, Integer>(3).build();
        IntStream.range(0, 3).forEach(i -> cache.put(String.valueOf(i), i));

        Assertions.assertEquals(0, cache.get("0").get());
        cache.put("3", 3);
        Assertions.assertTrue(Stream.of(2, 0, 3).allMatch(i -> cache.get(String.valueOf(i)).isPresent()));
    }
}
