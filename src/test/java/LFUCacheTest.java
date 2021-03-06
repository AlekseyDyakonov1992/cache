import cache.CacheBuilder;
import cache.lfu.LFUCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LFUCacheTest {
    @Test
    public void checkEvictionStrategyByCapacity() {
        LFUCache<String, Integer> cache = CacheBuilder.build(LFUCache.class).with(lfu -> lfu.setMaxCapacity(10)).get();
        IntStream.range(0, 20).forEach(i -> cache.put(String.valueOf(i), i));

        Assertions.assertTrue(IntStream.range(9, 19).noneMatch(i -> cache.get(String.valueOf(i)).isPresent()));
    }

    @Test
    public void checkEvictionStrategyByLatestElement() {
        LFUCache<String, Integer> cache = CacheBuilder.build(LFUCache.class).with(lfu -> lfu.setMaxCapacity(3)).get();
        IntStream.range(0, 3).forEach(i -> cache.put(String.valueOf(i), i));

        Assertions.assertEquals(2, cache.get("2").get());
        cache.put("3", 3);
        Assertions.assertTrue(Stream.of(0, 2, 3).allMatch(i -> cache.get(String.valueOf(i)).isPresent()));
    }

    @Test
    public void checkEvictionStrategyByFirstElement() {
        LFUCache<String, Integer> cache = CacheBuilder.build(LFUCache.class).with(lfu -> lfu.setMaxCapacity(3)).get();
        IntStream.range(0, 3).forEach(i -> cache.put(String.valueOf(i), i));

        Assertions.assertEquals(0, cache.get("0").get());
        cache.put("3", 3);
        Assertions.assertTrue(Stream.of(0, 1, 3).allMatch(i -> cache.get(String.valueOf(i)).isPresent()));
    }

    @Test
    public void checkEvictionStrategyWithOverwrite() {
        LFUCache<String, Integer> cache = CacheBuilder.build(LFUCache.class).with(lfu -> lfu.setMaxCapacity(3)).get();
        IntStream.range(0, 3).forEach(i -> cache.put(String.valueOf(i), i));
        cache.put("0", 4);

        Assertions.assertTrue(Stream.of(0, 1, 2).allMatch(i -> cache.get(String.valueOf(i)).isPresent()));
        Assertions.assertEquals(4, cache.get("0").get());
    }
}
