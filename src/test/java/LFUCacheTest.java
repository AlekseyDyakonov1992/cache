import cache.lfu.LFUCache;
import cache.MyCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LFUCacheTest {
    @Test
    public void checkEvictionStrategyByCapacity() {
        MyCache<String, Integer> cache = new LFUCache<>(10);
        IntStream.range(0, 20).forEach(i -> cache.put(String.valueOf(i), i));

        Assertions.assertTrue(IntStream.range(9, 19).noneMatch(i -> cache.get(String.valueOf(i)).isPresent()));
    }

    @Test
    public void checkEvictionStrategyByLatestElement() {
        MyCache<String, Integer> cache = new LFUCache<>(3);
        IntStream.range(0, 3).forEach(i -> cache.put(String.valueOf(i), i));

        Assertions.assertEquals(2, cache.get("2").get());
        cache.put("3", 3);
        Assertions.assertTrue(Stream.of(0, 2, 3).allMatch(i -> cache.get(String.valueOf(i)).isPresent()));
    }

    @Test
    public void checkEvictionStrategyByFirstElement() {
        MyCache<String, Integer> cache = new LFUCache<>(3);
        IntStream.range(0, 3).forEach(i -> cache.put(String.valueOf(i), i));

        Assertions.assertEquals(0, cache.get("0").get());
        cache.put("3", 3);
        Assertions.assertTrue(Stream.of(0, 1, 3).allMatch(i -> cache.get(String.valueOf(i)).isPresent()));
    }

    @Test
    public void checkEvictionStrategyWithOverwrite() {
        MyCache<String, Integer> cache = new LFUCache<>(3);
        IntStream.range(0, 3).forEach(i -> cache.put(String.valueOf(i), i));
        cache.put("0", 4);

        Assertions.assertTrue(Stream.of(0, 1, 2).allMatch(i -> cache.get(String.valueOf(i)).isPresent()));
        Assertions.assertEquals(4, cache.get("0").get());
    }
}
