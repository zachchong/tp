package presspal.contact.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Locale;

import org.junit.jupiter.api.Test;

public class ConfigTest {

    @Test
    public void toStringMethod() {
        Config config = new Config();
        String expected = Config.class.getCanonicalName() + "{logLevel=" + config.getLogLevel()
                + ", userPrefsFilePath=" + config.getUserPrefsFilePath() + "}";
        assertEquals(expected, config.toString());
    }

    @Test
    public void equalsMethod() {
        Config defaultConfig = new Config();
        assertNotNull(defaultConfig);
        assertTrue(defaultConfig.equals(defaultConfig));
    }

    @Test
    public void displayDateTimeConstants() {
        // Verify the pattern constant
        assertEquals("dd MMM yyyy h:mma", Config.DISPLAY_DATE_TIME_PATTERN);

        // Verify the formatter formats a known date/time as expected (use Locale.ENGLISH)
        LocalDateTime dt = LocalDateTime.of(2024, 1, 1, 15, 30);
        String formatted = Config.DISPLAY_DATE_TIME_FORMATTER.withLocale(Locale.ENGLISH).format(dt);
        assertEquals("01 Jan 2024 3:30PM", formatted);
    }

}
