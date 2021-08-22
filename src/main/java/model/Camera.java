package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Camera {

    private String id;

    private String name;

    @JsonProperty("rover_id")
    private String roverId;

    @JsonProperty("full_name")
    private String fullName;

    public Camera(){
        super();
    }

    public Camera(String id, String name, String roverId, String fullName) {
        this.id = id;
        this.name = name;
        this.roverId = roverId;
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRoverId() {
        return roverId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoverId(String roverId) {
        this.roverId = roverId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "Camera{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", roverId='" + roverId + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
