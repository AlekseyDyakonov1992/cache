package cache.lru;

import cache.MyCache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class LRUCache<K, V> extends MyCache<K, V> {
    private final Map<K, V> lruCache = new LinkedHashMap<K, V>(maxCapacity, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > maxCapacity;
        }
    };

    private LRUCache(Builder<K, V> builder) {
        super(builder);
    }

    public static class Builder<K, V> extends MyCache.Builder<K, V> {
        public Builder(int maxCapacity) {
            super(maxCapacity);
        }

        @Override
        public MyCache<K, V> build() {
            return new LRUCache<>(this);
        }
    }

    @Override
    public Optional<V> get(K key) {
        return Optional.ofNullable(lruCache.get(key));
    }

    @Override
    public void put(K key, V value) {
        lruCache.putIfAbsent(key, value);
    }
}
