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
        String invalidRole = "";
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidRole));
    }

    @Test
    public void isValidRole() {
        // null role
        assertThrows(NullPointerException.class, () -> Role.isValidRole(null));

        // invalid roles
        assertFalse(Role.isValidRole("")); // empty string
        assertFalse(Role.isValidRole(" ")); // spaces only

        // valid roles
        assertTrue(Role.isValidRole("Software Engineer"));
        assertTrue(Role.isValidRole("-")); // one character
        assertTrue(Role.isValidRole("Senior President of Engineering")); // long role, < 50 characters
    }

    @Test
    public void isValidRole_lengthLimits() {
        // EP: less than 50 characters -> valid
        String valid49 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"; // 49 a's
        assertTrue(Role.isValidRole(valid49));

        // EP: exactly 50 characters -> valid
        String valid50 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"; // 50 a's
        assertTrue(Role.isValidRole(valid50));

        // EP: 51 characters (too long) -> invalid
        String invalid51 = valid50 + "a";
        assertFalse(Role.isValidRole(invalid51));

        // constructor should reject too-long category names
        assertThrows(IllegalArgumentException.class, () -> new Role(invalid51));
    }

    @Test
    public void equals() {
        Role role = new Role("Valid Role");

        // same values -> returns true
        assertTrue(role.equals(new Role("Valid Role")));

        // same object -> returns true
        assertTrue(role.equals(role));

        // null -> returns false
        assertFalse(role.equals(null));

        // different types -> returns false
        assertFalse(role.equals(5.0f));

        // different values -> returns false
        assertFalse(role.equals(new Role("Other Valid Role")));
    }
}
