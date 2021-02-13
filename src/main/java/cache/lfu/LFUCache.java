package cache.lfu;

import cache.MyCache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class LFUCache<K, V> extends MyCache<K, V> {
    private final LinkedHashMap<K, Node> cache;

    public LFUCache(int maxCapacity) {
        super(maxCapacity);
        this.cache = new LinkedHashMap<>(maxCapacity, 0.75f);
    }

    @Override
    public Optional<V> get(K key) {
        Node node = cache.get(key);
        if (node == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(node.incrementFrequency().getValue());
    }

    @Override
    public void put(K key, V value) {
        doEvictionIfNeeded(key);
        cache.put(key, new Node(value));
    }

    private void doEvictionIfNeeded(K putKey) {
        if (cache.size() < maxCapacity) {
            return;
        }
        long minFrequency = Long.MAX_VALUE;
        K keyToRemove = null;
        for (Map.Entry<K, Node> entry : cache.entrySet()) {
            if (Objects.equals(entry.getKey(), putKey)) {
                return;
            }
            if (minFrequency >= entry.getValue().getFrequency()) {
                minFrequency = entry.getValue().getFrequency();
                keyToRemove = entry.getKey();
            }
        }
        cache.remove(keyToRemove);
    }

    private class Node {
        private final V value;
        private long frequency;

        public Node(V value) {
            this.value = value;
            this.frequency = 1;
        }

        public V getValue() {
            return value;
        }

        public long getFrequency() {
            return frequency;
        }

        public Node incrementFrequency() {
            ++frequency;
            return this;
        }
    }
}
