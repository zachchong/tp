package presspal.contact.logic.parser;

import static presspal.contact.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static presspal.contact.logic.commands.CommandTestUtil.CATEGORY_DESC_FRIEND;
import static presspal.contact.logic.commands.CommandTestUtil.INDEX_DESC_A;
import static presspal.contact.logic.parser.CommandParserTestUtil.assertParseFailure;
import static presspal.contact.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static presspal.contact.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import presspal.contact.logic.commands.AddCategoryCommand;
import presspal.contact.logic.commands.DeleteCategoryCommand;
import presspal.contact.testutil.EditCategoryDescriptorBuilder;

public class DeleteCategoryCommandParserTest {

    private String errorMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            DeleteCategoryCommand.MESSAGE_USAGE);
    private DeleteCategoryCommandParser parser = new DeleteCategoryCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, "friends", errorMessage);

        // no category specified
        assertParseFailure(parser, "i/1", errorMessage);

        // no index and no category specified
        assertParseFailure(parser, "", errorMessage);
    }

    @Test
    public void parse_invalidIndex_failure() {
        // negative index
        assertParseFailure(parser, "i/-1 friends", errorMessage);

        // zero index
        assertParseFailure(parser, "i/0 friends", errorMessage);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "i/1 some random string friends", errorMessage);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "i/1 r/string friends", errorMessage);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = INDEX_DESC_A + " c/friends";
        AddCategoryCommand.EditCategoryDescriptor descriptor = new EditCategoryDescriptorBuilder()
                .withCategories("friends").build();

        DeleteCategoryCommand expectedCommand =
                new DeleteCategoryCommand(INDEX_FIRST_PERSON, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_emptyCategoryValue_failure() {
        // triggers onlyEmptyCategory == true
        String userInput = INDEX_DESC_A + " c/";
        assertParseFailure(parser, userInput, errorMessage);
    }

    @Test
    public void parse_nonEmptyPreambleAtStart_failure() {
        // triggers non-empty preamble branch
        String userInput = "preamble " + INDEX_DESC_A + CATEGORY_DESC_FRIEND;
        assertParseFailure(parser, userInput, errorMessage);
    }
}
