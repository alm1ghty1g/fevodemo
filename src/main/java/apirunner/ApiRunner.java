package apirunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import guavacacheimpl.GuavaCacheImpl;
import guavainterface.GuavaCacheInterface;
import model.Camera;
import model.Photo;
import model.Rover;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.*;

public class ApiRunner {

    private static final Logger LOG = LoggerFactory.getLogger(ApiRunner.class);

    private static final String API_KEY = "ETQtKwL9zdOgsd3Z1CpqpclDGdCfNZM9QBrIRxvd";
    private static final String BASE_KEY = "&api_key=";
    private static final String BASE_URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?earth_date=";

    private GuavaCacheInterface guavaCacheInterface = GuavaCacheImpl.getGuavaCacheImpl();

    /**
     * Method apiRunner() returns Map for cache if it's exist, otherwise make an API call.
     */
    public Map<String, List<String>> apiRunner() throws IOException {

        LocalDate API_DATE = LocalDate.now();
        String API_URL = BASE_URL + API_DATE.toString() + BASE_KEY + API_KEY;
        Map<String, List<String>> resultMap = new LinkedHashMap<>();
        HttpClient httpClient = new DefaultHttpClient();
        LocalDate localDate = API_DATE;

        if (!guavaCacheInterface.asMap().isEmpty()) {
            return revertMap(guavaCacheInterface.asMap());
        } else {

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

                guavaCacheInterface.putAll(resultMap);

                localDate = localDate.minusDays(1);
                API_URL = BASE_URL + localDate + BASE_KEY + API_KEY;
            }
            return convertMapToJson(revertMap(resultMap));
        }
    }

    /**
     * Method parseMainObject() calls sub methods to combine Photo object with child classes,
     * filtering by camera.getName && rover.getName and returns passed values as Json Map.
     */
    private Map<String, List<String>> parseMainObject(String responseBody, LocalDate localDate) {

        int index = 0;

        List<Photo> photoList = new ArrayList<>();
        JsonElement fileElement = JsonParser.parseString(responseBody);
        JsonObject fileObject = fileElement.getAsJsonObject();
        JsonArray jsonArrayOfPhotos = fileObject.get("photos").getAsJsonArray();

        for (JsonElement element : jsonArrayOfPhotos) {

            Photo photo = parsePhotoObject(element);
            Camera camera = parseCameraObject(jsonArrayOfPhotos, index);
            Rover rover = parseRoverObject(jsonArrayOfPhotos, index);

            photo.setCamera(camera);
            photo.setRover(rover);

            if (photo.getCamera().getName().equalsIgnoreCase("NAVCAM") &&
                    photo.getRover().getName().equalsIgnoreCase("Curiosity")) {
                photoList.add(photo);
            }
            index++;
        }

        try {
            return createPair(photoList, localDate.toString());
        } catch (IOException e) {
            LOG.error("Error occurred during pair creating", e);
            throw new IllegalArgumentException("Can Not Deserialize Instance", e);
        }
    }


    /**
     * Method parsePhotoObject() converts JsonData to Photo object.
     */
    private Photo parsePhotoObject(JsonElement element) {

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(element.toString(), Photo.class);
        } catch (IOException e) {
            LOG.error("Error occurred in parsing photo", e);
            throw new IllegalArgumentException("Can not parse photo", e);
        }
    }

    /**
     * Method parseCameraObject() converts JsonData to Camera object.
     */
    private Camera parseCameraObject(JsonArray jsonArrayOfPhotos, int index) {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonElement cameraElement = jsonArrayOfPhotos.get(index).getAsJsonObject();
        JsonObject cameraObject = cameraElement.getAsJsonObject();
        JsonObject cameraJson = cameraObject.get("camera").getAsJsonObject();

        try {
            return objectMapper.readValue(cameraJson.toString(), Camera.class);
        } catch (IOException e) {
            LOG.error("Error occurred in parsing camera", e);
            throw new IllegalArgumentException("Can not parse camera", e);
        }
    }

    /**
     * Method parseRoverObject() converts JsonData to Rover object.
     */
    private Rover parseRoverObject(JsonArray jsonArrayOfPhotos, int index) {

        ObjectMapper objectMapper = new ObjectMapper();

        JsonElement roverElement = jsonArrayOfPhotos.get(index).getAsJsonObject();
        JsonObject roverObject = roverElement.getAsJsonObject();
        JsonObject roverJson = roverObject.get("rover").getAsJsonObject();

        try {
            return objectMapper.readValue(roverJson.toString(), Rover.class);
        } catch (IOException e) {
            LOG.error("Error occurred in parsing rover", e);
            throw new IllegalArgumentException("Can not parse rover", e);
        }
    }

    /**
     * Method convertMap() converts Map to Json and returns.
     */

    public Map<String, List<String>> convertMapToJson(Map<String, List<String>> stringListMap) throws JsonProcessingException {

        Map<String, List<String>> convertedMap = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (Map.Entry<String, List<String>> entry : stringListMap.entrySet()) {

            List<String> imageList = entry.getValue();
            List<String> jsonImageList = new ArrayList<>();

            List<String> reducedList = new ArrayList<>();

            if (imageList.size() > 3) {
                reducedList = imageList.subList(0, 2);
            }
            for (String image : reducedList) {
                String  jsonImage = objectMapper.writeValueAsString(image);
                jsonImageList.add(jsonImage);
            }
            convertedMap.put(objectMapper.writeValueAsString(entry.getKey()), jsonImageList);
        }
        return revertMap(convertedMap);
    }


    /**
     * Method returns Map in reversed order.
     */
    private static Map<String, List<String>> revertMap(Map<String, List<String>> stringListMap) {

        Map<String, List<String>> resultMap = new LinkedHashMap<>();

        List<String> set = new ArrayList<>(stringListMap.keySet());
        Collections.sort(set);
        Iterator<String> itr = set.iterator();

        while (itr.hasNext()) {
            String key = itr.next();

            resultMap.put(key, stringListMap.get(key));
        }
        return resultMap;
    }

    /**
     * Method creates pair and returns as Map.
     */
    private Map<String, List<String>> createPair(List<Photo> photoList, String localDate) throws IOException {

        Map<String, List<String>> parseMap = new TreeMap<>();
        List<String> imageList = new ArrayList<>();

        for (Photo photo : photoList) {
            imageList.add(photo.getImgSrc());
        }

        parseMap.put(localDate, imageList);

        return parseMap;
    }
}


