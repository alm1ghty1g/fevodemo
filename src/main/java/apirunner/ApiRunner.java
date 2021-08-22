package apirunner;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.*;


public class ApiRunner {


    private static final String API_KEY = "ETQtKwL9zdOgsd3Z1CpqpclDGdCfNZM9QBrIRxvd";
    private static final String BASE_KEY = "&api_key=";
    private static final String BASE_URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?earth_date=";


    public Map<String, List<String>> apiRunner() throws IOException {

        LocalDate API_DATE = LocalDate.now();
        String API_URL = BASE_URL + API_DATE.toString() + BASE_KEY + API_KEY;

        Map<String, List<String>> resultMap = new LinkedHashMap<>(); // change

        HttpClient httpClient = new DefaultHttpClient();

        LocalDate localDate = API_DATE;

        for (int i = 0; i < 10; i++) {

            StringBuilder responseBody = new StringBuilder();
            HttpGet request = new HttpGet(API_URL);
            HttpResponse resp = httpClient.execute(request);
            try (BufferedReader rd = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()))) {
                String line;

                while ((line = rd.readLine()) != null) {
                    responseBody.append(line);
                }
            }

            resultMap.putAll(parseMainObject(responseBody.toString(), localDate));

            localDate = localDate.minusDays(1);
            API_URL = BASE_URL + localDate + BASE_KEY + API_KEY;
        }
        return resultMap;
    }

    private Map<String, List<String>> parseMainObject(String responseBody, LocalDate localDate) {

        Camera camera;
        Rover rover;

        int index = 0;

        List<Photo> photoList = new ArrayList<>();

        JsonElement fileElement = JsonParser.parseString(responseBody);
        JsonObject fileObject = fileElement.getAsJsonObject();
        JsonArray jsonArrayOfPhotos = fileObject.get("photos").getAsJsonArray();

        for (JsonElement element : jsonArrayOfPhotos) {

            Photo photo = parsePhotoObject(element);
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


        try {
            return convertMap(photoList, localDate.toString());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
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

        JsonElement cameraElement = jsonArrayOfPhotos.get(index).getAsJsonObject();
        JsonObject cameraObject = cameraElement.getAsJsonObject();
        JsonObject cameraJson = cameraObject.get("camera").getAsJsonObject();

        try {
            return objectMapper.readValue(cameraJson.toString(), Camera.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot parse...", e);
        }


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


    private Map<String, List<String>> convertMap(List<Photo> photoList, String localDate) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, List<String>> convertedMap = new HashMap<>();

        List<String> imageList = new ArrayList<>();

        if (photoList.size() > 2) {
            photoList = photoList.subList(0,3);
        }

        for (Photo photo: photoList) {
            imageList.add(photo.getImgSrc());
        }

        String jsonDate = objectMapper.writeValueAsString(localDate);
        String jsonImage;

        List<String> jsonList = new ArrayList<>();

        for (String image: imageList) {
            jsonImage = objectMapper.writeValueAsString(image);
            jsonList.add(jsonImage);
        }

        convertedMap.put(jsonDate,jsonList);


        return convertedMap;
    }
}

