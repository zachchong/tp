package presspal.contact.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import presspal.contact.logic.parser.exceptions.ParseException;

public class DeleteInterviewCommandParserTest {

    private final DeleteInterviewCommandParser parser = new DeleteInterviewCommandParser();

    @Test
    public void parse_valid_success() {
        assertDoesNotThrow(() -> parser.parse(" 1 2 "));
    }

    @Test
    public void parse_missingArgs_failure() {
        assertThrows(ParseException.class, () -> parser.parse(""));
        assertThrows(ParseException.class, () -> parser.parse("1"));
    }

    @Test
    public void parse_nonInteger_failure() {
        assertThrows(ParseException.class, () -> parser.parse("one 2"));
        assertThrows(ParseException.class, () -> parser.parse("1 two"));
    }
}
