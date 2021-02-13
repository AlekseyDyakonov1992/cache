package cache;

import java.util.Optional;

public abstract class MyCache<K, V> {
    private int maxCapacity;

    protected int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public abstract Optional<V> get(K key);
    public abstract void put(K key, V value);
}
