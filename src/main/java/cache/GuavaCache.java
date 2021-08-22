package cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import model.Photo;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class GuavaCache {

    private static final GuavaCache guavaCache = new GuavaCache();

    public static GuavaCache getInstance() {
        return guavaCache;
    }


    public static com.google.common.cache.Cache<String, Photo> photoCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();





    public static LoadingCache<String, Photo> photoCachee = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build(new PhotoCacheLoader());




//    public static com.google.common.cache.Cache<String, Photo> photoCache = CacheBuilder.newBuilder()
//            .maximumSize(1000)
//            .expireAfterWrite(5, TimeUnit.MINUTES)
//            .build();


    public static Cache<String, Photo> getPhotoCache() {
        return photoCache;
    }
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



