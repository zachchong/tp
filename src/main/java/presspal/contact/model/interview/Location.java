package presspal.contact.model.interview;

import static java.util.Objects.requireNonNull;
import static presspal.contact.commons.util.AppUtil.checkArgument;

import java.util.Objects;

/**
 * Represents the location of interview.
 */
public class Location {
    /** The textual representation of the interview location. */
    public static final String MESSAGE_CONSTRAINTS = "Location cannot be blank";
    private String value;

    /** Constructs a Location object */
    public Location(String location) {
        requireNonNull(location);
        checkArgument(isValidLocation(location), MESSAGE_CONSTRAINTS);
        value = location;
    }

    /**
     * Returns true if a given string is a valid location.
     */
    public static boolean isValidLocation(String loc) {
        // implemented for future refinement use case
        return loc != null && !loc.isBlank();
    }

    /** Updates the interview location to a new specified value */
    public void setLocation(String newLocation) {
        requireNonNull(newLocation);
        checkArgument(isValidLocation(newLocation), MESSAGE_CONSTRAINTS);
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
