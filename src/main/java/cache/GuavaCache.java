package cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GuavaCache {


    private static final com.google.common.cache.Cache<String, List<String>> photoCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();


    public static Cache getCacheInstance() {
        return photoCache;
    }



    private static final GuavaCache guavaCache = new GuavaCache();

    public static GuavaCache getInstance() {
        return guavaCache;
    }



}



