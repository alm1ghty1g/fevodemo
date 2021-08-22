package guavacache;


import com.google.common.cache.CacheBuilder;
import model.Photo;
import service.Cache;

import java.util.concurrent.TimeUnit;

public class GuavaCacheImpl implements Cache {


    public static com.google.common.cache.Cache<String, Photo> photoCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();


    @Override
    public Photo getPhoto(String key) {
        return new Photo();
    }

    @Override
    public void putPhoto(String key, Photo photo) {
        photoCache.put(key, photo);
    }
}
