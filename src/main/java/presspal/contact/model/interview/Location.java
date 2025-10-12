package presspal.contact.model.interview;

import java.util.Objects;

/**
 * Represents the location of interview.
 */
public class Location {
    /** The textual representation of the interview location. */
    private String location;

    /** Constructs a Location object */
    public Location(String location) {
        this.location = location;
    }

    /** Returns the interview location as a String */
    public String getLocation() {
        return this.location;
    }

    /** Updates the interview location to a new specified value */
    public void setLocation(String newLocation) {
        this.location = newLocation;
    }

    /**
     * Returns true if both Location objects have the same location string.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Location otherLocation)) {
            return false;
        }

        return Objects.equals(location, otherLocation.location);
    }

    /**
     * Returns the hash code for this Location.
     */
    @Override
    public int hashCode() {
        return Objects.hash(location);
    }
}
