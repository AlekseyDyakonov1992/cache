package cache;

import java.util.Optional;

public abstract class MyCache<K, V> {
    protected int maxCapacity;

    public MyCache(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public abstract Optional<V> get(K key);
    public abstract void put(K key, V value);
}
