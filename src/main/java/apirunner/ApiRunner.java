package apirunner;

import cache.GuavaCache;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Camera;
import model.Photo;
import model.Rover;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;


public class ApiRunner {

  private List<Photo> photoListOuter;

   private void checkCache() throws IOException {

       if (GuavaCache.photoCache.asMap().isEmpty()){

           apiRunner();

//            Map<String, Photo> result = photoListOuter.stream().collect(Collectors.toMap(Photo::getId, Function.identity()));
           Map<String, Photo> result = photoListOuter.stream().collect(Collectors.toMap(Photo::getId, Function.identity()));

           GuavaCache.photoCache.asMap().putAll(result);

       }
       else  {

           // check what data in cache and compare to required output for related key

            // if !(List<images> exist)  || list.size() < 3 make a call to API and get another values + fill List properly and show as output;
       }
   }


   private Map<String, List<String>> returnJsonResultMap() {


       return null;
   }

    private static final String API_KEY = "ETQtKwL9zdOgsd3Z1CpqpclDGdCfNZM9QBrIRxvd";
    private static final String BASE_KEY = "&api_key=";
    private static final String BASE_URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?earth_date=";
    private static LocalDate API_DATE = LocalDate.now();
    private static String API_URL = BASE_URL + API_DATE.toString() + BASE_KEY + API_KEY;

    public void apiRunner() throws IOException {

        Map<String, List<String>> resultMap = new HashMap<>();
        List<String> imageList = new ArrayList<>();

        HttpClient httpClient = new DefaultHttpClient();
        LocalDate localDate = API_DATE;

        for (int i = 0; i < 10; i++) {

            Photo photo = null;

            try {
                photo = GuavaCache.photoCache.get(localDate.toString());
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            if (photo != null) {

                imageList.add(photo.getImg_src());
                resultMap.put(localDate.toString(),imageList);

                continue;
            }

            StringBuilder responseBody = new StringBuilder();
            HttpGet request = new HttpGet(API_URL);
            HttpResponse resp = httpClient.execute(request);
            BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));

            String line;
            while ((line = rd.readLine()) != null) {
                responseBody.append(line);
            }
            parseMainObject(responseBody.toString(), localDate);

            localDate = localDate.minusDays(1);
            API_URL = BASE_URL + localDate + BASE_KEY + API_KEY;
        }
    }

    private List<Photo> parseMainObject(String responseBody, LocalDate localDate) {

        Photo photo = new Photo();
        Camera camera;
        Rover rover;

        int index = 0;

        List<Photo> photoList = new ArrayList<>();

        JsonElement fileElement = JsonParser.parseString(responseBody);
        JsonObject fileObject = fileElement.getAsJsonObject();
        JsonArray jsonArrayOfPhotos = fileObject.get("photos").getAsJsonArray();

        for (JsonElement element : jsonArrayOfPhotos) {

            photo = parsePhotoObject(element);
            camera = parseCameraObject(jsonArrayOfPhotos, index);
            rover = parseRoverObject(jsonArrayOfPhotos, index);

            photo.setCamera(camera);
            photo.setRover(rover);

            if (photo.getCamera().getName().equalsIgnoreCase("NAVCAM") &&
                    photo.getRover().getName().equalsIgnoreCase("Curiosity")) {
                photoList.add(photo);
            }
            index++;
        }
//        printResult(photoList);

        // fill cache there

        GuavaCache.photoCache.put(localDate.toString(), photo);
//        System.out.println(photoList.size());

        return photoList;
    }

    private Photo parsePhotoObject(JsonElement element) {

        ObjectMapper objectMapper = new ObjectMapper();
        Photo photo = new Photo();

        try {
            photo = objectMapper.readValue(element.toString(), Photo.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return photo;
    }

    private Camera parseCameraObject(JsonArray jsonArrayOfPhotos, int index) {
        ObjectMapper objectMapper = new ObjectMapper();
        Camera camera = new Camera();

        JsonElement cameraElement = jsonArrayOfPhotos.get(index).getAsJsonObject();
        JsonObject cameraObject = cameraElement.getAsJsonObject();
        JsonObject cameraJson = cameraObject.get("camera").getAsJsonObject();

        try {
            camera = objectMapper.readValue(cameraJson.toString(), Camera.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return camera;
    }


    private Rover parseRoverObject(JsonArray jsonArrayOfPhotos, int index) {

        ObjectMapper objectMapper = new ObjectMapper();
        Rover rover = new Rover();

        JsonElement roverElement = jsonArrayOfPhotos.get(index).getAsJsonObject();
        JsonObject roverObject = roverElement.getAsJsonObject();
        JsonObject roverJson = roverObject.get("rover").getAsJsonObject();

        try {
            rover = objectMapper.readValue(roverJson.toString(), Rover.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rover;
    }

    private void printResult(List<Photo> photoList) {
        for (int i = 0; i < photoList.size(); i++) {
            System.out.println(
                    " NASA id: " + photoList.get(i).getId() +
                            " Earth Date: " + photoList.get(i).getEarth_date() +
                            " Rover Name: " + photoList.get(i).getRover().getName() +
                            " Camera name: " + photoList.get(i).getCamera().getName() +
                            " image " + photoList.get(i).getImg_src());
            if (i == 2) {
                break;
            }
        }
    }
}

