package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Photo {

    private String id;

    private String sol;

    private Camera camera;

    @JsonProperty("img_src")
    private String imgSrc;

    @JsonProperty("earth_date")
    private String earthDate;

    private Rover rover;

    public Photo(String sol) {
        this.sol = sol;
    }

    public Photo(){
        super();
    }

    public Photo(String id, String sol, Camera camera, String imgSrc, String earthDate, Rover rover) {
        this.id = id;
        this.sol = sol;
        this.camera = camera;
        this.imgSrc = imgSrc;
        this.earthDate = earthDate;
        this.rover = rover;
    }

    public String getId() {
        return id;
    }

    public String getSol() {
        return sol;
    }

    public Camera getCamera() {
        return camera;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public String getEarthDate() {
        return earthDate;
    }

    public Rover getRover() {
        return rover;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSol(String sol) {
        this.sol = sol;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public void setEarthDate(String earthDate) {
        this.earthDate = earthDate;
    }

    public void setRover(Rover rover) {
        this.rover = rover;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id='" + id + '\'' +
                ", sol='" + sol + '\'' +
                ", camera=" + camera +
                ", imgSrc='" + imgSrc + '\'' +
                ", earthDate='" + earthDate + '\'' +
                ", rover=" + rover +
                '}';
    }
}
