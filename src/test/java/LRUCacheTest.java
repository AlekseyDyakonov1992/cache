import cache.CacheBuilder;
import cache.lru.LRUCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LRUCacheTest {
    @Test
    public void checkEvictionStrategyByCapacity() {
        LRUCache<String, Integer> cache = CacheBuilder.build(LRUCache.class).with(lfu -> lfu.setMaxCapacity(10)).get();
        IntStream.range(0, 20).forEach(i -> cache.put(String.valueOf(i), i));

        Assertions.assertTrue(IntStream.range(0, 10).noneMatch(i -> cache.get(String.valueOf(i)).isPresent()));
    }

    @Test
    public void checkEvictionStrategyByLatestElement() {
        LRUCache<String, Integer> cache = CacheBuilder.build(LRUCache.class).with(lfu -> lfu.setMaxCapacity(3)).get();
        IntStream.range(0, 3).forEach(i -> cache.put(String.valueOf(i), i));

        Assertions.assertEquals(2, cache.get("2").get());
        cache.put("3", 3);
        Assertions.assertTrue(IntStream.range(1, 4).allMatch(i -> cache.get(String.valueOf(i)).isPresent()));
    }

    @Test
    public void checkEvictionStrategyByFirstElement() {
        LRUCache<String, Integer> cache = CacheBuilder.build(LRUCache.class).with(lfu -> lfu.setMaxCapacity(3)).get();
        IntStream.range(0, 3).forEach(i -> cache.put(String.valueOf(i), i));

        Assertions.assertEquals(0, cache.get("0").get());
        cache.put("3", 3);
        Assertions.assertTrue(Stream.of(2, 0, 3).allMatch(i -> cache.get(String.valueOf(i)).isPresent()));
    }
}
