package service;

public interface ServiceInterface<T, K> {

    T get(String key);

    void put(K object);

}
