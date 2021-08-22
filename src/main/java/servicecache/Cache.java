package servicecache;

import model.Photo;

public interface Cache {

    Photo getPhoto(String key);

    void putPhoto(String key, Photo photo);

}
