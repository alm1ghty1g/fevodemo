package GuavaCacheImpl;

import cache.GuavaCache;
import guavainterface.GuavaCacheInterface;
import model.Photo;

public class GuavaCacheImpl implements GuavaCacheInterface {


     private GuavaCache guavaCache = GuavaCache.getInstance();


    private static final String KEY = "PHOTO";

    @Override
    public Photo getPhoto(String key) {
//        GuavaCache.photoCache.get(key);

//        guavaCache.

        return null;
    }

    @Override
    public void putPhoto(String key, Photo photo) {
        GuavaCache.getPhotoCache().put(key, photo);
    }
}
