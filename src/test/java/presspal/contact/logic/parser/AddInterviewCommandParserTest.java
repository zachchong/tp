package presspal.contact.logic.parser;

import static presspal.contact.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static presspal.contact.logic.commands.CommandTestUtil.INTERVIEW_HEADER_DESC_A;
import static presspal.contact.logic.commands.CommandTestUtil.INTERVIEW_HEADER_DESC_B;
import static presspal.contact.logic.commands.CommandTestUtil.INTERVIEW_LOCATION_DESC_A;
import static presspal.contact.logic.commands.CommandTestUtil.INTERVIEW_LOCATION_DESC_B;
import static presspal.contact.logic.commands.CommandTestUtil.INVALID_INTERVIEW_HEADER_DESC;
import static presspal.contact.logic.commands.CommandTestUtil.INVALID_INTERVIEW_LOCATION_DESC;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_DATE;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_HEADER;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_INDEX;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_LOCATION;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_TIME;
import static presspal.contact.logic.parser.CommandParserTestUtil.assertParseFailure;
import static presspal.contact.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import presspal.contact.commons.core.index.Index;
import presspal.contact.logic.Messages;
import presspal.contact.logic.commands.AddInterviewCommand;
import presspal.contact.logic.parser.exceptions.ParseException;
import presspal.contact.model.interview.Interview;
import presspal.contact.testutil.InterviewBuilder;

/** Tests for {@link AddInterviewCommandParser}. */
public class AddInterviewCommandParserTest {

    private AddInterviewCommandParser parser = new AddInterviewCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        Interview expectedInterview = new InterviewBuilder()
                .withHeader("Sample Interview A")
                .withLocation("Sample Location A")
                .withDate(LocalDateTime.parse("2024-10-10T14:00"))
                .build();

        String userInput = " " + PREFIX_INDEX + "1"
                + INTERVIEW_HEADER_DESC_A
                + " " + PREFIX_DATE + "2024-10-10"
                + " " + PREFIX_TIME + "14:00"
                + INTERVIEW_LOCATION_DESC_A;

        assertParseSuccess(parser, userInput, new AddInterviewCommand(expectedInterview, Index.fromOneBased(1)));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddInterviewCommand.MESSAGE_USAGE);

        // missing index
        String missingIndex = INTERVIEW_HEADER_DESC_A
                + " " + PREFIX_DATE + "2024-10-10"
                + " " + PREFIX_TIME + "14:00"
                + INTERVIEW_LOCATION_DESC_A;
        assertParseFailure(parser, missingIndex, expectedMessage);

        // missing header
        String missingHeader = " " + PREFIX_INDEX + "1"
                + " " + PREFIX_DATE + "2024-10-10"
                + " " + PREFIX_TIME + "14:00"
                + INTERVIEW_LOCATION_DESC_A;
        assertParseFailure(parser, missingHeader, expectedMessage);

        // missing date
        String missingDate = " " + PREFIX_INDEX + "1"
                + INTERVIEW_HEADER_DESC_A
                + " " + PREFIX_TIME + "14:00"
                + INTERVIEW_LOCATION_DESC_A;
        assertParseFailure(parser, missingDate, expectedMessage);

        // missing time
        String missingTime = " " + PREFIX_INDEX + "1"
                + INTERVIEW_HEADER_DESC_A
                + " " + PREFIX_DATE + "2024-10-10"
                + INTERVIEW_LOCATION_DESC_A;
        assertParseFailure(parser, missingTime, expectedMessage);

        // missing location
        String missingLocation = " " + PREFIX_INDEX + "1"
                + INTERVIEW_HEADER_DESC_A
                + " " + PREFIX_DATE + "2024-10-10"
                + " " + PREFIX_TIME + "14:00";
        assertParseFailure(parser, missingLocation, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid header
        String invalidHeader = " " + PREFIX_INDEX + "1"
                + INVALID_INTERVIEW_HEADER_DESC
                + " " + PREFIX_DATE + "2024-10-10"
                + " " + PREFIX_TIME + "14:00"
                + INTERVIEW_LOCATION_DESC_A;
        assertParseFailure(parser, invalidHeader, presspal.contact.model.interview.Header.MESSAGE_CONSTRAINTS);

        // invalid location
        String invalidLocation = " " + PREFIX_INDEX + "1"
                + INTERVIEW_HEADER_DESC_A
                + " " + PREFIX_DATE + "2024-10-10"
                + " " + PREFIX_TIME + "14:00"
                + INVALID_INTERVIEW_LOCATION_DESC;
        assertParseFailure(parser, invalidLocation, presspal.contact.model.interview.Location.MESSAGE_CONSTRAINTS);

        // invalid date format
        String invalidDate = " " + PREFIX_INDEX + "1"
                + INTERVIEW_HEADER_DESC_A
                + " " + PREFIX_DATE + "10-10-2024"
                + " " + PREFIX_TIME + "14:00"
                + INTERVIEW_LOCATION_DESC_A;
        assertParseFailure(parser, invalidDate, "Date is not in the correct format! Please use yyyy-MM-dd");

        // invalid time format
        String invalidTime = " " + PREFIX_INDEX + "1"
                + INTERVIEW_HEADER_DESC_A
                + " " + PREFIX_DATE + "2024-10-10"
                + " " + PREFIX_TIME + "2pm"
                + INTERVIEW_LOCATION_DESC_A;
        assertParseFailure(parser, invalidTime, "Time is not in the correct format! Please use HH:mm");
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        String userInput = "NonEmptyPreamble " + " " + PREFIX_INDEX + "1"
                + INTERVIEW_HEADER_DESC_A
                + " " + PREFIX_DATE + "2024-10-10"
                + " " + PREFIX_TIME + "14:00"
                + INTERVIEW_LOCATION_DESC_A;

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddInterviewCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        String duplicateIndex = " " + PREFIX_INDEX + "1"
                + " " + PREFIX_INDEX + "2"
                + INTERVIEW_HEADER_DESC_A
                + " " + PREFIX_DATE + "2024-10-10"
                + " " + PREFIX_TIME + "14:00"
                + INTERVIEW_LOCATION_DESC_A;

        assertParseFailure(parser, duplicateIndex, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_INDEX));

        String duplicateHeader = " " + PREFIX_INDEX + "1"
                + INTERVIEW_HEADER_DESC_A
                + INTERVIEW_HEADER_DESC_B
                + " " + PREFIX_DATE + "2024-10-10"
                + " " + PREFIX_TIME + "14:00"
                + INTERVIEW_LOCATION_DESC_A;

        assertParseFailure(parser, duplicateHeader, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_HEADER));

        String duplicateDate = " " + PREFIX_INDEX + "1"
                + INTERVIEW_HEADER_DESC_A
                + " " + PREFIX_DATE + "2024-10-10"
                + " " + PREFIX_DATE + "2025-10-10"
                + " " + PREFIX_TIME + "14:00"
                + INTERVIEW_LOCATION_DESC_A;

        assertParseFailure(parser, duplicateDate, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        String duplicateTime = " " + PREFIX_INDEX + "1"
                + INTERVIEW_HEADER_DESC_A
                + " " + PREFIX_DATE + "2024-10-10"
                + " " + PREFIX_TIME + "14:00"
                + " " + PREFIX_TIME + "16:00"
                + INTERVIEW_LOCATION_DESC_A;

        assertParseFailure(parser, duplicateTime, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TIME));

        String duplicateLocation = " " + PREFIX_INDEX + "1"
                + INTERVIEW_HEADER_DESC_A
                + " " + PREFIX_DATE + "2024-10-10"
                + " " + PREFIX_TIME + "14:00"
                + INTERVIEW_LOCATION_DESC_A
                + INTERVIEW_LOCATION_DESC_B;

        assertParseFailure(parser, duplicateLocation, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LOCATION));
    }
}
