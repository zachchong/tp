package presspal.contact.logic.parser;

import static presspal.contact.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static presspal.contact.logic.commands.CommandTestUtil.CATEGORY_DESC_FRIEND;
import static presspal.contact.logic.commands.CommandTestUtil.CATEGORY_DESC_HUSBAND;
import static presspal.contact.logic.commands.CommandTestUtil.INDEX_DESC_A;
import static presspal.contact.logic.commands.CommandTestUtil.INDEX_DESC_B;
import static presspal.contact.logic.commands.CommandTestUtil.INVALID_CATEGORY_DESC;
import static presspal.contact.logic.commands.CommandTestUtil.INVALID_INDEX_DESC;
import static presspal.contact.logic.commands.CommandTestUtil.VALID_CATEGORY_FRIEND;
import static presspal.contact.logic.commands.CommandTestUtil.VALID_CATEGORY_HUSBAND;
import static presspal.contact.logic.parser.CommandParserTestUtil.assertParseFailure;
import static presspal.contact.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static presspal.contact.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import presspal.contact.logic.Messages;
import presspal.contact.logic.commands.AddCategoryCommand;
import presspal.contact.logic.commands.EditCategoryCommand.EditCategoryDescriptor;
import presspal.contact.model.category.Category;
import presspal.contact.testutil.EditCategoryDescriptorBuilder;

public class AddCategoryCommandParserTest {

    private String errorMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            AddCategoryCommand.MESSAGE_USAGE);
    private AddCategoryCommandParser parser = new AddCategoryCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_CATEGORY_FRIEND, errorMessage);

        // no category specified
        assertParseFailure(parser, INDEX_DESC_A, errorMessage);

        // no index and no category specified
        assertParseFailure(parser, "", errorMessage);
    }

    @Test
    public void parse_invalidIndex_failure() {
        // negative index
        assertParseFailure(parser, INVALID_INDEX_DESC + " " + VALID_CATEGORY_FRIEND, errorMessage);

        // zero index
        assertParseFailure(parser, "i/0 " + VALID_CATEGORY_FRIEND, errorMessage);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "i/1 some random string " + VALID_CATEGORY_FRIEND, errorMessage);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "i/1 r/string " + VALID_CATEGORY_FRIEND, errorMessage);

        //duplicate index prefix
        assertParseFailure(parser, INDEX_DESC_A + INDEX_DESC_B + CATEGORY_DESC_FRIEND,
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_INDEX));
    }

    @Test
    public void parse_invalidCategory_failure() {
        // invalid category
        assertParseFailure(parser, INDEX_DESC_A + INVALID_CATEGORY_DESC, Category.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = INDEX_DESC_A + CATEGORY_DESC_FRIEND + CATEGORY_DESC_HUSBAND;
        EditCategoryDescriptor descriptor = new EditCategoryDescriptorBuilder()
                .withCategories(VALID_CATEGORY_FRIEND, VALID_CATEGORY_HUSBAND).build();

        AddCategoryCommand expectedCommand = new AddCategoryCommand(INDEX_FIRST_PERSON, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
