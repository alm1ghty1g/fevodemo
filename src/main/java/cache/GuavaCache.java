package cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import model.Photo;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class GuavaCache {

    public static LoadingCache<String, Photo> photoCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build(new PhotoCacheLoader());
}



class PhotoCacheLoader extends CacheLoader<String, Photo> {

    @Override
    public Photo load(String key) throws Exception {
        return new Photo(key);
    }

    @Override
    public Map<String, Photo> loadAll(Iterable<? extends String> keys) throws Exception {

        return super.loadAll(keys);
    }
}



