import apirunner.ApiRunner;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        ApiRunner apiRunner = new ApiRunner();

        try {

            Map<String, List<String>> map = apiRunner.apiRunner();

            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


















