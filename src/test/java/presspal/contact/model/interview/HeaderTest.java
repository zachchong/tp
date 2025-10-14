package presspal.contact.model.interview;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link Header}.
 */
class HeaderTest {

    @Test
    void testEqualsAndHashCode() {
        Header h1 = new Header("A");
        Header h2 = new Header("A");
        Header h3 = new Header("B");

        assertEquals(h1, h2);
        assertNotEquals(h1, h3);
        assertEquals(h1.hashCode(), h2.hashCode());
        assertNotEquals(h1.hashCode(), h3.hashCode());
    }

    @Test
    void testEqualsWithDifferentTypeAndNull() {
        Header header = new Header("A");
        assertNotEquals(header, null);
        assertNotEquals(header, "A String");
    }

    @Test
    @DisplayName("Constructor should throw NullPointerException for null input")
    void constructorNull_throwsException() {
        assertThrows(NullPointerException.class, () -> new Header(null));
    }

    @Test
    @DisplayName("Constructor should throw IllegalArgumentException for invalid input")
    void constructorInvalid_throwsException() {
        // Example: empty string is invalid in our isValidHeader()
        assertThrows(IllegalArgumentException.class, () -> new Header(""));
    }


    @Test
    void testToString() {
        Header header = new Header("Interview with Alice");
        assertEquals("Interview with Alice", header.toString());
    }
}
