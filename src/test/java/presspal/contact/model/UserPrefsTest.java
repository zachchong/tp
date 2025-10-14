package presspal.contact.model;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static presspal.contact.testutil.Assert.assertThrows;

import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import presspal.contact.commons.core.GuiSettings;


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
    public void hashCode_equalObjects_sameHashCode() {
        UserPrefs userPrefs1 = new UserPrefs();
        UserPrefs userPrefs2 = new UserPrefs();

        // Same default values should have same hashcode
        assertEquals(userPrefs1.hashCode(), userPrefs2.hashCode());

        // Modified prefs with same values should have same hashcode
        GuiSettings newSettings = new GuiSettings(800, 600, 0, 0);
        userPrefs1.setGuiSettings(newSettings);
        userPrefs2.setGuiSettings(newSettings);
        assertEquals(userPrefs1.hashCode(), userPrefs2.hashCode());

        // Different paths should result in different hashcodes
        userPrefs1.setContactBookFilePath(Paths.get("data", "different.json"));
        assertNotEquals(userPrefs1.hashCode(), userPrefs2.hashCode());
    }
}
