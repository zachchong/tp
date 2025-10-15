package presspal.contact.model.interview;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the {@link Location} class.
 */
class LocationTest {

    private Location location;

    @BeforeEach
    void setUp() {
        location = new Location("NUS Enterprise");
    }

    @Test
    @DisplayName("Constructor should correctly initialize location")
    void testConstructorInitializesLocation() {
        assertEquals("NUS Enterprise", location.toString(),
                "Constructor should set the correct location value");
    }

    @Test
    @DisplayName("Getter should return the current location value")
    void testGetLocation() {
        assertEquals("NUS Enterprise", location.toString(),
                "getLocation() should return the stored location string");
    }

    @Test
    @DisplayName("Setter should update the location value")
    void testSetLocation() {
        location.setLocation("Zoom Meeting");
        assertEquals("Zoom Meeting", location.toString(),
                "setLocation() should update the location value correctly");
    }

    @Test
    void isValidLocation() {
        // null location
        assertFalse(Location.isValidLocation(null));

        // blank location
        assertFalse(Location.isValidLocation(""));
        assertFalse(Location.isValidLocation("   "));

        // valid locations
        assertTrue(Location.isValidLocation("NUS Enterprise"));
        assertTrue(Location.isValidLocation("Zoom Meeting"));
    }

    @Test
    void testEqualsAndHashCode() {
        Location loc1 = new Location("Zoom");
        Location loc2 = new Location("Zoom");
        Location loc3 = new Location("Google Meet");

        // equals
        assertEquals(loc1, loc2);
        assertNotEquals(loc1, loc3);

        // hashCode
        assertEquals(loc1.hashCode(), loc2.hashCode());
        assertNotEquals(loc1.hashCode(), loc3.hashCode());
    }

    @Test
    @DisplayName("Equals with null and different type")
    void testEqualsWithNullAndDifferentType() {
        Location loc = new Location("Zoom");
        assertNotEquals(loc, null);
        assertNotEquals(loc, "Zoom String");
    }

    @Test
    @DisplayName("Constructor should throw NullPointerException for null input")
    void constructorNull_throwsException() {
        assertThrows(NullPointerException.class, () -> new Location(null));
    }

    @Test
    @DisplayName("Constructor should throw IllegalArgumentException for invalid input")
    void constructorInvalid_throwsException() {
        // Example: blank location string is invalid
        assertThrows(IllegalArgumentException.class, () -> new Location(""));
    }

}
