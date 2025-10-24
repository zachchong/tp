package presspal.contact.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import presspal.contact.logic.parser.exceptions.ParseException;

public class DeleteInterviewCommandParserTest {

    private final DeleteInterviewCommandParser parser = new DeleteInterviewCommandParser();

    @Test
    public void parse_valid_success() {
        // matches expected format: "i/<personIndex> i/<interviewIndex>"
        assertDoesNotThrow(() -> parser.parse(" i/1 i/2 "));
    }

    @Test
    public void parse_missingArgs_failure() {
        assertThrows(ParseException.class, () -> parser.parse(""));
        assertThrows(ParseException.class, () -> parser.parse("i/1"));
    }

    @Test
    public void parse_nonInteger_failure() {
        assertThrows(ParseException.class, () -> parser.parse("i/one i/2"));
        assertThrows(ParseException.class, () -> parser.parse("i/1 i/two"));
    }
}
