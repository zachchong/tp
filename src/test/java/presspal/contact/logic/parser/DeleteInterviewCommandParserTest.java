package presspal.contact.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import presspal.contact.logic.parser.exceptions.ParseException;

public class DeleteInterviewCommandParserTest {

    private final DeleteInterviewCommandParser parser = new DeleteInterviewCommandParser();

    @Test
    public void parse_valid_success() {
        assertDoesNotThrow(() -> parser.parse(" i/1 v/2"));
    }

    @Test
    public void parse_missingArgs_failure() {
        assertThrows(ParseException.class, () -> parser.parse(""));
        assertThrows(ParseException.class, () -> parser.parse(" i/1")); // missing v/
        assertThrows(ParseException.class, () -> parser.parse(" v/2")); // missing i/
    }

    @Test
    public void parse_nonInteger_failure() {
        assertThrows(ParseException.class, () -> parser.parse(" i/one v/2"));
        assertThrows(ParseException.class, () -> parser.parse(" i/1 v/two"));
        assertThrows(ParseException.class, () -> parser.parse(" i/-1 v/2")); // negative
        assertThrows(ParseException.class, () -> parser.parse(" i/0 v/2")); // zero
    }

    @Test
    public void parse_withPreamble_failure() {
        assertThrows(ParseException.class, () -> parser.parse(" oops i/1 v/2"));
    }

    @Test
    public void parse_extraPrefixes_failure() {
        // extras before allowed prefixes → preamble → invalid format
        assertThrows(ParseException.class, () -> parser.parse(" n/Alice i/1 v/2"));

        // extras after allowed prefixes → value pollution → parseIndex should fail
        assertThrows(ParseException.class, () -> parser.parse(" i/1 v/2 n/Alice"));
        assertThrows(ParseException.class, () -> parser.parse(" i/1 v/2 p/9999"));
        assertThrows(ParseException.class, () -> parser.parse(" i/1 v/2 e/a@b.com"));
        assertThrows(ParseException.class, () -> parser.parse(" i/1 v/2 o/Org r/Role c/VIP"));
    }

    // duplicate prefixes
    @Test
    public void parse_duplicatePrefixes_failure() {
        assertThrows(ParseException.class, () -> parser.parse(" i/1 i/2 v/1")); // duplicate i/
        assertThrows(ParseException.class, () -> parser.parse(" i/1 v/1 v/2")); // duplicate v/
    }
}
