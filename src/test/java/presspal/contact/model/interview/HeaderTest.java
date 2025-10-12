package presspal.contact.model.interview;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link Header}.
 */
class HeaderTest {

    @Test
    void testConstructorAndGetter() {
        Header header = new Header("Interview with Alice");
        assertEquals("Interview with Alice", header.getHeader());
    }

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
    void testToString() {
        Header header = new Header("Interview with Alice");
        // Since Header does not override toString, it defaults to Object.toString()
        // We can only check that getHeader() returns the value
        assertEquals("Interview with Alice", header.getHeader());
    }
}
