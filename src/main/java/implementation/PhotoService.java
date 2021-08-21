package implementation;

import abstraction.AbstractImplementation;
import service.ServiceInterface;

public class PhotoService implements ServiceInterface {


   private AbstractImplementation abstractImplementation = AbstractImplementation.getInstance();


    @Override
    public Object get(String key) {
        try {
        return abstractImplementation.load(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void put(Object object) {
        try {
            abstractImplementation.load(object.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
