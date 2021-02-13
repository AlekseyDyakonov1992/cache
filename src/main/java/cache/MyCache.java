package cache;

import java.util.Optional;

public abstract class MyCache<K, V> {
    protected final int maxCapacity;

    protected MyCache(Builder<K, V> builder) {
        this.maxCapacity = builder.maxCapacity;
    }

    public abstract Optional<V> get(K key);
    public abstract void put(K key, V value);

    protected static abstract class Builder<K, V> {
        private final int maxCapacity;

        public Builder(int maxCapacity) {
            this.maxCapacity = maxCapacity;
        }

        public abstract MyCache<K, V> build();
    }
}
