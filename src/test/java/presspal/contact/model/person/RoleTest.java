package presspal.contact.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static presspal.contact.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RoleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Role(null));
    }

    @Test
    public void constructor_invalidRole_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Role("")); // empty
        assertThrows(IllegalArgumentException.class, () -> new Role(" Manager")); // leading space
    }

    @Test
    public void isValidRole() {
        // EP (null): invalid
        assertThrows(NullPointerException.class, () -> Role.isValidRole(null));

        // EP (invalid): empty / whitespace-only
        assertFalse(Role.isValidRole(""));
        assertFalse(Role.isValidRole(" "));

        // EP (invalid): non-alphanumeric tokens or separators
        assertFalse(Role.isValidRole("-")); // single non-alnum char
        assertFalse(Role.isValidRole("Editor-in-chief")); // hyphen
        assertFalse(Role.isValidRole("Lead_Engineer")); // underscore
        assertFalse(Role.isValidRole("Data  Scientist")); // double space
        assertFalse(Role.isValidRole(" Manager")); // leading space
        assertFalse(Role.isValidRole("Manager ")); // trailing space
        assertFalse(Role.isValidRole("工程师")); // non-ASCII

        // EP (valid): single alphanumeric word
        assertTrue(Role.isValidRole("Reporter"));
        assertTrue(Role.isValidRole("A")); // minimal length

        // EP (valid): multiple words separated by single spaces, alnum only
        assertTrue(Role.isValidRole("Software Engineer"));
        assertTrue(Role.isValidRole("Data Scientist 2")); // digits allowed
        assertTrue(Role.isValidRole("Senior 2 Analyst")); // digits inside words
    }

    @Test
    public void isValidRole_lengthLimits() {
        // EP/BVA (valid): < 50 characters
        String valid49 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"; // 49 a's
        assertTrue(Role.isValidRole(valid49));

        // BVA (valid): exactly 50 characters
        String valid50 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"; // 50 a's
        assertTrue(Role.isValidRole(valid50));

        // BVA (invalid): 51 characters
        String invalid51 = valid50 + "a";
        assertFalse(Role.isValidRole(invalid51));

        // Constructor should reject too-long role names
        assertThrows(IllegalArgumentException.class, () -> new Role(invalid51));
    }

    @Test
    public void equals() {
        Role role = new Role("Valid Role");

        // EP (equal values)
        assertTrue(role.equals(new Role("Valid Role")));

        // Reflexive
        assertTrue(role.equals(role));

        // Null
        assertFalse(role.equals(null));

        // Different type
        assertFalse(role.equals(5.0f));

        // Different value
        assertFalse(role.equals(new Role("Other Valid Role")));
    }
}
