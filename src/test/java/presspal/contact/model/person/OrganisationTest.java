package presspal.contact.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static presspal.contact.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class OrganisationTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Organisation(null));
    }

    @Test
    public void constructor_invalidOrganisation_throwsIllegalArgumentException() {
        // Keep just representative cases to avoid duplicating EP coverage
        assertThrows(IllegalArgumentException.class, () -> new Organisation("")); // empty
        assertThrows(IllegalArgumentException.class, () -> new Organisation(" NUS")); // leading space
    }

    @Test
    public void isValidOrganisation() {
        // EP (null): invalid
        assertThrows(NullPointerException.class, () -> Organisation.isValidOrganisation(null));

        // EP (invalid): empty / whitespace-only
        assertFalse(Organisation.isValidOrganisation(""));
        assertFalse(Organisation.isValidOrganisation(" "));

        // EP (invalid): non-alphanumeric or bad spacing
        assertFalse(Organisation.isValidOrganisation("Alice & Peter")); // ampersand
        assertFalse(Organisation.isValidOrganisation("Alice-Peter")); // hyphen
        assertFalse(Organisation.isValidOrganisation("Alice_Peter")); // underscore
        assertFalse(Organisation.isValidOrganisation("National  University")); // double space
        assertFalse(Organisation.isValidOrganisation(" NUS")); // leading space
        assertFalse(Organisation.isValidOrganisation("NUS ")); // trailing space
        assertFalse(Organisation.isValidOrganisation("東京大学")); // non-ASCII

        // EP (valid): single alphanumeric word
        assertTrue(Organisation.isValidOrganisation("CAPT"));
        assertTrue(Organisation.isValidOrganisation("A")); // minimal length

        // EP (valid): multiple words separated by single spaces; digits allowed
        assertTrue(Organisation.isValidOrganisation("National University"));
        assertTrue(Organisation.isValidOrganisation("Channel 8 News"));
        assertTrue(Organisation.isValidOrganisation("Team 2 Alpha"));
    }

    @Test
    public void isValidOrganisation_lengthLimits() {
        // EP/BVA (valid): < 50 characters
        String valid49 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"; // 49 a's
        assertTrue(Organisation.isValidOrganisation(valid49));

        // BVA (valid): exactly 50 characters
        String valid50 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"; // 50 a's
        assertTrue(Organisation.isValidOrganisation(valid50));

        // BVA (invalid): 51 characters
        String invalid51 = valid50 + "a";
        assertFalse(Organisation.isValidOrganisation(invalid51));

        // Constructor should reject too-long organisation names
        assertThrows(IllegalArgumentException.class, () -> new Organisation(invalid51));
    }

    @Test
    public void equals() {
        Organisation organisation = new Organisation("Valid Organisation");

        // same values -> returns true
        assertTrue(organisation.equals(new Organisation("Valid Organisation")));

        // same object -> returns true
        assertTrue(organisation.equals(organisation));

        // null -> returns false
        assertFalse(organisation.equals(null));

        // different types -> returns false
        assertFalse(organisation.equals(5.0f));

        // different values -> returns false
        assertFalse(organisation.equals(new Organisation("Other Valid Organisation")));
    }
}
