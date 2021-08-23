package guavacacheimpl;

import cache.GuavaCache;
import guavainterface.GuavaCacheInterface;

import java.util.List;
import java.util.Map;

public class GuavaCacheImpl implements GuavaCacheInterface {


    private static final GuavaCacheImpl guavaCacheImpl = new GuavaCacheImpl();

    public static GuavaCacheImpl getGuavaCacheImpl() {
        return guavaCacheImpl;
    }

    private com.google.common.cache.Cache<String, List<String>> photoCache = GuavaCache.getCacheInstance();


    @Override
    public List<String> getPair(String key) {
        return photoCache.getIfPresent(key);
    }

    @Override
    public void putPair(String key, List<String> imageList) {
        photoCache.put(key, imageList);
    }


    @Override
    public void putAll(Map<String, List<String>> map) {

        photoCache.putAll(map);
    }

    @Override
    public Map<String, List<String>> asMap() {
        return this.photoCache.asMap();
    }
}
