package guavainterface;

import java.util.List;
import java.util.Map;

public interface GuavaCacheInterface {



    List<String> getPair(String key);

    void putPair(String key, List<String> imageList);

    void putAll(Map<String, List<String>> map);

    Map<String, List<String>> asMap();

}
