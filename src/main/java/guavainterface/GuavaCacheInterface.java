package guavainterface;

import model.Photo;

public interface GuavaCacheInterface {

    Photo getPhoto(String key);

    void putPhoto(String key, Photo photo);

}
