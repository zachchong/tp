package presspal.contact.logic.parser;

import static presspal.contact.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_INDEX;
import static presspal.contact.logic.parser.CommandParserTestUtil.assertParseFailure;
import static presspal.contact.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static presspal.contact.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static presspal.contact.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import presspal.contact.logic.commands.ListInterviewCommand;

public class ListInterviewCommandParserTest {
    private ListInterviewCommandParser parser = new ListInterviewCommandParser();

    @Test
    public void parse_validField_returnsListInterviewCommand() {
        assertParseSuccess(parser, " " + PREFIX_INDEX + "1",
                new ListInterviewCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidIndex_returnsListInterviewCommand() {
        assertParseFailure(parser, " " + PREFIX_INDEX + "a",
                String.format(MESSAGE_INVALID_INDEX));

        assertParseFailure(parser, " " + PREFIX_INDEX,
                String.format(MESSAGE_INVALID_INDEX));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListInterviewCommand.MESSAGE_USAGE));
    }
}
