package abstraction;


import com.google.common.cache.CacheLoader;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.Map;

public class AbstractImplementation extends CacheLoader<String, Object> {

    private static final AbstractImplementation abstractImplementation = new AbstractImplementation();

    public static AbstractImplementation getInstance() {
        return abstractImplementation;
    }

    protected AbstractImplementation() {
        super();
    }

    @Override
    public Object load(String key) throws Exception {
        return new Object();
    }

    @Override
    public ListenableFuture<Object> reload(String key, Object oldValue) throws Exception {
        return super.reload(key, oldValue);
    }

    @Override
    public Map<String, Object> loadAll(Iterable<? extends String> keys) throws Exception {
        return super.loadAll(keys);
    }
}
