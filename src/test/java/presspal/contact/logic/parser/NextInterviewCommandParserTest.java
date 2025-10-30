package presspal.contact.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static presspal.contact.logic.commands.NextInterviewCommand.COMMAND_WORD;

import org.junit.jupiter.api.Test;

import presspal.contact.logic.commands.NextInterviewCommand;
import presspal.contact.logic.parser.exceptions.ParseException;

public class NextInterviewCommandParserTest {

    private final NextInterviewCommandParser parser = new NextInterviewCommandParser();

    @Test
    public void parse_noArguments_returnsNextInterviewCommand() throws ParseException {
        NextInterviewCommand command = parser.parse("");
        assertEquals(NextInterviewCommand.class, command.getClass());

        // Also test whitespace only
        command = parser.parse("   ");
        assertEquals(NextInterviewCommand.class, command.getClass());
    }

    @Test
    public void parse_withArguments_success() throws ParseException {
        NextInterviewCommand command = parser.parse("123");
        assertEquals(NextInterviewCommand.class, command.getClass());
    }
}
