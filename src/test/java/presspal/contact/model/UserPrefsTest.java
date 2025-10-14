package presspal.contact.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static presspal.contact.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import java.util.Objects;

public class UserPrefsTest {

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setContactBookFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setContactBookFilePath(null));
    }

    @Test
    public void testHashCode() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setContactBookFilePath(null);
        userPrefs.setGuiSettings(null);

        assertEquals(userPrefs.hashCode(),
                Objects.hash(userPrefs.getContactBookFilePath(), userPrefs.getGuiSettings()));
    }
}
