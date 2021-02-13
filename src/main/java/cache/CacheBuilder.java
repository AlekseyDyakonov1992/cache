package cache;

import java.util.function.Consumer;

public class CacheBuilder<T extends MyCache> {
    private T instance;

    public CacheBuilder(Class<T> clazz){
        try {
            instance = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public CacheBuilder<T> with(Consumer<T> setter){
        setter.accept(instance);
        return this;
    }

    public T get(){
        return instance;
    }

    public static <T extends MyCache> CacheBuilder<T> build(Class<T> clazz) {
        return new CacheBuilder<>(clazz);
    }
}
