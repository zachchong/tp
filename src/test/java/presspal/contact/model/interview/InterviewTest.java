package presspal.contact.model.interview;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link Interview}.
 */
class InterviewTest {

    private Interview interview;
    private Header header;
    private Location location;
    private LocalDateTime dateTime;

    @BeforeEach
    void setUp() {
        // Create Header and Location
        header = new Header("Interview with Alice");
        location = new Location("NUS Enterprise");
        dateTime = LocalDateTime.of(2025, 10, 15, 14, 30);

        // Construct Interview
        interview = new Interview(header, location, dateTime);
    }

    @Test
    @DisplayName("Constructor should correctly initialize all fields")
    void testConstructorInitializesFields() {
        assertEquals(header, interview.getHeader(),
                "Constructor should correctly set the header");
        assertEquals(location, interview.getLocation(),
                "Constructor should correctly set the location");
        assertEquals(dateTime, interview.getDateTime(),
                "Constructor should correctly set the dateTime");
    }

    @Test
    @DisplayName("Getter should return the header value")
    void testGetHeader() {
        assertEquals(header, interview.getHeader(),
                "getHeader() should return the same Header passed to the constructor");
    }

    @Test
    @DisplayName("Getter should return the location value")
    void testGetLocation() {
        assertEquals(location, interview.getLocation(),
                "getLocation() should return the same Location passed to the constructor");
    }

    @Test
    @DisplayName("Getter should return the date and time value")
    void testGetDateTime() {
        assertEquals(dateTime, interview.getDateTime(),
                "getDateTime() should return the same LocalDateTime passed to the constructor");
    }

    @Test
    @DisplayName("toString() should include all key fields")
    void testToStringIncludesAllFields() {
        String s = interview.toString();
        assertTrue(s.contains("Interview scheduled for"), "Should start with Interview");
        assertTrue(s.contains("on"), "Should include datetime field");
        assertTrue(s.contains("at"), "Should include location field");
    }

    @Test
    @DisplayName("Constructor should allow null header or location")
    void testConstructorAllowsNulls() {
        Interview interviewWithNulls = new Interview(null, null, dateTime);
        assertNull(interviewWithNulls.getHeader(),
                "Header should be allowed to be null");
        assertNull(interviewWithNulls.getLocation(),
                "Location should be allowed to be null");
        assertEquals(dateTime, interviewWithNulls.getDateTime(),
                "DateTime should still be correctly set");
    }

    @Test
    @DisplayName("equals() and hashCode() should work correctly")
    void testEqualsAndHashCode() {
        Interview sameInterview = new Interview(header, location, dateTime);
        Interview diffHeader = new Interview(new Header("Different"), location, dateTime);
        Interview diffLocation = new Interview(header, new Location("Different"), dateTime);
        Interview diffDateTime = new Interview(header, location, dateTime.plusDays(1));

        // Equal and unequal comparisons
        assertEquals(interview, interview);
        assertEquals(interview, sameInterview);
        assertNotEquals(interview, diffHeader);
        assertNotEquals(interview, diffLocation);
        assertNotEquals(interview, diffDateTime);
        assertEquals(interview.hashCode(), sameInterview.hashCode());

        assertNotEquals(interview, null);
        assertNotEquals(interview, "NotAnInterview");

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
