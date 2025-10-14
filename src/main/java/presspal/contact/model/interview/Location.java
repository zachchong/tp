package presspal.contact.model.interview;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Represents the location of interview.
 */
public class Location {
    /** The textual representation of the interview location. */
    private String value;

    /** Constructs a Location object */
    public Location(String location) {
        requireNonNull(location);
        value = location;
    }

    /** Updates the interview location to a new specified value */
    public void setLocation(String newLocation) {
        requireNonNull(newLocation);
        value = newLocation;
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

        return Objects.equals(value, otherLocation.value);
    }

    /**
     * Returns the hash code for this Location.
     */
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
