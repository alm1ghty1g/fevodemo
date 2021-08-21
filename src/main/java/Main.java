import apirunner.ApiRunner;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import implementation.CameraService;
import implementation.PhotoService;
import model.Photo;
import service.ServiceInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;





//class Main {
//
//
////    private static final String API_KEY = "ETQtKwL9zdOgsd3Z1CpqpclDGdCfNZM9QBrIRxvd";
////    private static final String BASE_KEY = "&api_key=";
////    private static final String BASE_URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?earth_date=";
////    private static String API_DATE = LocalDate.now().toString();
////    private static String API_URL = BASE_URL + API_DATE + BASE_KEY + API_KEY;
////
////    public void apiRunner() throws IOException {
////
////        HttpClient httpClient = new DefaultHttpClient();
////
////        for (int i = 0; i < 10; i++) {
////
////            StringBuilder responseBody = new StringBuilder();
////            HttpGet request = new HttpGet(API_URL);
////            HttpResponse resp = httpClient.execute(request);
////            BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
////
////            String line;
////            while ((line = rd.readLine()) != null) {
////                responseBody.append(line);
////            }
////            parseMainObject(responseBody.toString());
////
////
////            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
////            LocalDate localDate = LocalDate.parse(API_DATE, formatter);
////            localDate = localDate.minusDays(1);
////            API_DATE = localDate.toString();
////            API_URL = BASE_URL + API_DATE + BASE_KEY + API_KEY;
////        }
////    }
////
////    public String parseMainObject(String responseBody) {
////
////        System.out.println("API_DATE--> " + API_DATE);
////
////        Photo photo;
////        Camera camera;
////        Rover rover;
////
////        int index = 0;
////
////        List<Photo> photoList = new ArrayList<>();
////
////
////        JsonElement fileElement = JsonParser.parseString(responseBody);
////        JsonObject fileObject = fileElement.getAsJsonObject();
////        JsonArray jsonArrayOfPhotos = fileObject.get("photos").getAsJsonArray();
////
////        for (JsonElement element : jsonArrayOfPhotos) {
////
////            photo = parsePhotoObject(element);
////
////            camera = parseCameraObject(jsonArrayOfPhotos, index);
////
////            rover = parseRoverObject(jsonArrayOfPhotos, index);
////
////            photo.setCamera(camera);
////            photo.setRover(rover);
////
////
////            if (photo.getCamera().getName().equalsIgnoreCase("NAVCAM") &&
////                    photo.getRover().getName().equalsIgnoreCase("Curiosity")) {
////                photoList.add(photo);
////            }
////            index++;
////        }
////        printResult(photoList);
////        System.out.println(photoList.size());
////
////        return null;
////    }
////
////    private Photo parsePhotoObject(JsonElement element) {
////
////        ObjectMapper objectMapper = new ObjectMapper();
////
////        Photo photo = new Photo();
////
////        try {
////            photo = objectMapper.readValue(element.toString(), Photo.class);
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////
////        return photo;
////    }
////
////    private Camera parseCameraObject(JsonArray jsonArrayOfPhotos, int index) {
////        ObjectMapper objectMapper = new ObjectMapper();
////        Camera camera = new Camera();
////
////        JsonElement cameraElement = jsonArrayOfPhotos.get(index).getAsJsonObject();
////        JsonObject cameraObject = cameraElement.getAsJsonObject();
////        JsonObject cameraJson = cameraObject.get("camera").getAsJsonObject();
////
////        try {
////            camera = objectMapper.readValue(cameraJson.toString(), Camera.class);
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////
////        return camera;
////    }
////
////
////    private Rover parseRoverObject(JsonArray jsonArrayOfPhotos, int index) {
////
////        ObjectMapper objectMapper = new ObjectMapper();
////
////        Rover rover = new Rover();
////
////        JsonElement roverElement = jsonArrayOfPhotos.get(index).getAsJsonObject();
////        JsonObject roverObject = roverElement.getAsJsonObject();
////        JsonObject roverJson = roverObject.get("rover").getAsJsonObject();
////
////        try {
////            rover = objectMapper.readValue(roverJson.toString(), Rover.class);
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////
////        return rover;
////    }
////
////    private void printResult(List<Photo> photoList) {
////        for (int i = 0; i < photoList.size(); i++) {
////            System.out.println(
////                    " NASA id: " + photoList.get(i).getId() +
////                            " Earth Date: " + photoList.get(i).getEarth_date() +
////                            " Rover Name: " + photoList.get(i).getRover().getName() +
////                            " Camera name: " + photoList.get(i).getCamera().getName() +
////                            " image " + photoList.get(i).getImg_src());
////            if (i == 2) {
////                break;
////            }
////        }
////    }
//
//}

public class Main{

    public static void main(String[] args) {

        ApiRunner apiRunner = new ApiRunner();

        try {
            apiRunner.apiRunner();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}




//        GuavaTest gt = GuavaTest.getInstance();
//
//        try {
//            System.out.println(gt.getEntry("Suvendu"));
//            System.out.println(gt.getEntry("Suvendu"));
//            Thread.sleep(2100);
//            System.out.println(gt.getEntry("Suvendu"));
//            System.out.println(gt.getEntry("Suvendu"));
//
//            System.out.println(gt.getEntryAll("Suvendu1"));
//            System.out.println(gt.getEntry("Suvendu1"));
//
//        } catch (ExecutionException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }


















