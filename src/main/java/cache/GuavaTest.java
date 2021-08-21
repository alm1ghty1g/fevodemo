package cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class GuavaTest {

    LoadingCache<String, Object> cache  = CacheBuilder.newBuilder()
            .build(new CacheLoader<String, Object>() {
                @Override
                public Object load(String key) throws Exception {
                    return new Object();
                }
            });




//    private LoadingCache<String, String> cache;
//
//
//    public GuavaTest() {
//        cache = CacheBuilder.newBuilder()
//                .maximumSize(1000)
//                .expireAfterWrite(5, TimeUnit.MINUTES)
//                .build(new CacheLoader<String, String>(
//
//                ) {
//                    @Override
//                    public String load(String arg0) throws Exception {
//                        // TODO Auto-generated method stub
//                        return addcache(arg0);
//                    }
//
//                    @Override
//                    public Map<String, String> loadAll(Iterable<? extends String> keys) throws Exception {
//                        System.out.println("inside load all");
//                        return addcacheAll(keys);
//                    }
//
//                });
//    }
//
//    private String addcache(String arg0) {
//        System.out.println("adding cache");
//        return arg0.toUpperCase();
//    }
//
//    private Map<String, String> addcacheAll(Iterable<? extends String> keys) {
//        Map<String, String> map = new HashMap<String, String>();
//        for (String s : keys) {
//            map.put(s, s.toUpperCase());
//        }
//        return map;
//    }
//
//    public String getEntry(String args) throws ExecutionException {
//        System.out.println(cache.size());
//        return cache.get(args);
//    }
//
//    public ImmutableMap<String, String> getEntryAll(String args) throws ExecutionException {
//        List<String> list = new ArrayList<String>();
//        list.add(args);
//        return cache.getAll(list);
//    }
}
