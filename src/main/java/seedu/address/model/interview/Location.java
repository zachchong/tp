package seedu.address.model.interview;

public class Location {
    private String location;

    public Location(String location){
        this.location = location;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String newLocation) {
        this.location = newLocation;
    }
}
