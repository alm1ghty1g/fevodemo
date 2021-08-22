package model;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Rover {

    private String id;

    private String name;

    @JsonProperty("landing_date")
    private String landingDate;

    @JsonProperty("launch_date")
    private String launchDate;

    private String status;

    public Rover(){
        super();
    }

    public Rover(String id, String name, String landingDate, String launchDate, String status) {
        this.id = id;
        this.name = name;
        this.landingDate = landingDate;
        this.launchDate = launchDate;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLandingDate() {
        return landingDate;
    }

    public String getLaunchDate() {
        return launchDate;
    }

    public String getStatus() {
        return status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLandingDate(String landingDate) {
        this.landingDate = landingDate;
    }

    public void setLaunchDate(String launchDate) {
        this.launchDate = launchDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Rover{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", landingDate='" + landingDate + '\'' +
                ", launchDate='" + launchDate + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
