package presspal.contact.logic.parser;

import static presspal.contact.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static presspal.contact.logic.commands.CommandTestUtil.CATEGORY_DESC_FRIEND;
import static presspal.contact.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static presspal.contact.logic.commands.CommandTestUtil.INTERVIEW_HEADER_DESC_A;
import static presspal.contact.logic.commands.CommandTestUtil.INTERVIEW_HEADER_DESC_B;
import static presspal.contact.logic.commands.CommandTestUtil.INTERVIEW_LOCATION_DESC_A;
import static presspal.contact.logic.commands.CommandTestUtil.INTERVIEW_LOCATION_DESC_B;
import static presspal.contact.logic.commands.CommandTestUtil.INVALID_INTERVIEW_HEADER_DESC;
import static presspal.contact.logic.commands.CommandTestUtil.INVALID_INTERVIEW_LOCATION_DESC;
import static presspal.contact.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static presspal.contact.logic.commands.CommandTestUtil.ORGANISATION_DESC_AMY;
import static presspal.contact.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static presspal.contact.logic.commands.CommandTestUtil.ROLE_DESC_AMY;
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

        // EP: valid input, all required prefixes present with valid values

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

        // EP: invalid, missing index
        String missingIndex = INTERVIEW_HEADER_DESC_A
                + " " + PREFIX_DATE + "2024-10-10"
                + " " + PREFIX_TIME + "14:00"
                + INTERVIEW_LOCATION_DESC_A;
        assertParseFailure(parser, missingIndex, expectedMessage);

        // EP: invalid, missing header
        String missingHeader = " " + PREFIX_INDEX + "1"
                + " " + PREFIX_DATE + "2024-10-10"
                + " " + PREFIX_TIME + "14:00"
                + INTERVIEW_LOCATION_DESC_A;
        assertParseFailure(parser, missingHeader, expectedMessage);

        // EP: invalid, missing date
        String missingDate = " " + PREFIX_INDEX + "1"
                + INTERVIEW_HEADER_DESC_A
                + " " + PREFIX_TIME + "14:00"
                + INTERVIEW_LOCATION_DESC_A;
        assertParseFailure(parser, missingDate, expectedMessage);

        // EP: invalid, missing time
        String missingTime = " " + PREFIX_INDEX + "1"
                + INTERVIEW_HEADER_DESC_A
                + " " + PREFIX_DATE + "2024-10-10"
                + INTERVIEW_LOCATION_DESC_A;
        assertParseFailure(parser, missingTime, expectedMessage);

        // EP: invalid, missing location
        String missingLocation = " " + PREFIX_INDEX + "1"
                + INTERVIEW_HEADER_DESC_A
                + " " + PREFIX_DATE + "2024-10-10"
                + " " + PREFIX_TIME + "14:00";
        assertParseFailure(parser, missingLocation, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // EP: invalid, header value violates constraints
        String invalidHeader = " " + PREFIX_INDEX + "1"
                + INVALID_INTERVIEW_HEADER_DESC
                + " " + PREFIX_DATE + "2024-10-10"
                + " " + PREFIX_TIME + "14:00"
                + INTERVIEW_LOCATION_DESC_A;
        assertParseFailure(parser, invalidHeader, presspal.contact.model.interview.Header.MESSAGE_CONSTRAINTS);

        // EP: invalid, location value violates constraints
        String invalidLocation = " " + PREFIX_INDEX + "1"
                + INTERVIEW_HEADER_DESC_A
                + " " + PREFIX_DATE + "2024-10-10"
                + " " + PREFIX_TIME + "14:00"
                + INVALID_INTERVIEW_LOCATION_DESC;
        assertParseFailure(parser, invalidLocation, presspal.contact.model.interview.Location.MESSAGE_CONSTRAINTS);

        // EP: invalid, date format is wrong
        String invalidDate = " " + PREFIX_INDEX + "1"
                + INTERVIEW_HEADER_DESC_A
                + " " + PREFIX_DATE + "10-10-2024"
                + " " + PREFIX_TIME + "14:00"
                + INTERVIEW_LOCATION_DESC_A;

        String expectedMessage = "Invalid date input detected, it is either:\n"
                + "1. Incorrect format, use yyyy-MM-dd instead.\n2. Illegal date.";
        assertParseFailure(parser, invalidDate, expectedMessage);

        // EP: invalid, time format is wrong
        String invalidTime = " " + PREFIX_INDEX + "1"
                + INTERVIEW_HEADER_DESC_A
                + " " + PREFIX_DATE + "2024-10-10"
                + " " + PREFIX_TIME + "2pm"
                + INTERVIEW_LOCATION_DESC_A;
        expectedMessage = "Invalid time input detected, it is either:\n"
                + "1. Incorrect format, use HH:mm instead.\n2. Illegal time.";
        assertParseFailure(parser, invalidTime, expectedMessage);
    }

    @Test
    public void parse_nonEmptyPreamble_failure() {
        // EP: non-empty preamble not allowed
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
        // EP: invalid, duplicate INDEX
        String duplicateIndex = " " + PREFIX_INDEX + "1"
                + " " + PREFIX_INDEX + "2"
                + INTERVIEW_HEADER_DESC_A
                + " " + PREFIX_DATE + "2024-10-10"
                + " " + PREFIX_TIME + "14:00"
                + INTERVIEW_LOCATION_DESC_A;

        assertParseFailure(parser, duplicateIndex, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_INDEX));

        // EP: invalid, duplicate HEADER
        String duplicateHeader = " " + PREFIX_INDEX + "1"
                + INTERVIEW_HEADER_DESC_A
                + INTERVIEW_HEADER_DESC_B
                + " " + PREFIX_DATE + "2024-10-10"
                + " " + PREFIX_TIME + "14:00"
                + INTERVIEW_LOCATION_DESC_A;

        assertParseFailure(parser, duplicateHeader, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_HEADER));

        // EP: invalid, duplicate DATE
        String duplicateDate = " " + PREFIX_INDEX + "1"
                + INTERVIEW_HEADER_DESC_A
                + " " + PREFIX_DATE + "2024-10-10"
                + " " + PREFIX_DATE + "2025-10-10"
                + " " + PREFIX_TIME + "14:00"
                + INTERVIEW_LOCATION_DESC_A;

        assertParseFailure(parser, duplicateDate, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // EP: invalid, duplicate TIME
        String duplicateTime = " " + PREFIX_INDEX + "1"
                + INTERVIEW_HEADER_DESC_A
                + " " + PREFIX_DATE + "2024-10-10"
                + " " + PREFIX_TIME + "14:00"
                + " " + PREFIX_TIME + "16:00"
                + INTERVIEW_LOCATION_DESC_A;

        assertParseFailure(parser, duplicateTime, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TIME));

        // EP: invalid, duplicate LOCATION
        String duplicateLocation = " " + PREFIX_INDEX + "1"
                + INTERVIEW_HEADER_DESC_A
                + " " + PREFIX_DATE + "2024-10-10"
                + " " + PREFIX_TIME + "14:00"
                + INTERVIEW_LOCATION_DESC_A
                + INTERVIEW_LOCATION_DESC_B;

        assertParseFailure(parser, duplicateLocation, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LOCATION));
    }

    @Test
    public void parse_invalidPrefixes_failure() {
        // EP: invalid, unexpected/forbidden prefixes present
        String errorMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddInterviewCommand.MESSAGE_USAGE);

        // Representative of “extra unrelated prefix appended at end”.
        String categoryPrefix = " " + PREFIX_INDEX + "1"
                + INTERVIEW_HEADER_DESC_A
                + " " + PREFIX_DATE + "2024-10-10"
                + " " + PREFIX_TIME + "14:00"
                + INTERVIEW_LOCATION_DESC_A
                + CATEGORY_DESC_FRIEND;

        assertParseFailure(parser, categoryPrefix, errorMessage);

        // Representative of “extra unrelated prefix injected in middle”.
        String emailPrefix = " " + PREFIX_INDEX + "1"
                + INTERVIEW_HEADER_DESC_A
                + EMAIL_DESC_AMY
                + " " + PREFIX_DATE + "2024-10-10"
                + " " + PREFIX_TIME + "14:00"
                + INTERVIEW_LOCATION_DESC_A;

        assertParseFailure(parser, emailPrefix, errorMessage);

        // Representative of “unexpected prefix adjacent to required ones”.
        String namePrefix = " " + PREFIX_INDEX + "1"
                + INTERVIEW_HEADER_DESC_A
                + " " + PREFIX_DATE + "2024-10-10"
                + NAME_DESC_AMY
                + " " + PREFIX_TIME + "14:00"
                + INTERVIEW_LOCATION_DESC_A;

        assertParseFailure(parser, namePrefix, errorMessage);

        String organisationPrefix = " " + PREFIX_INDEX + "1"
                + INTERVIEW_HEADER_DESC_A
                + " " + PREFIX_DATE + "2024-10-10"
                + " " + PREFIX_TIME + "14:00"
                + ORGANISATION_DESC_AMY
                + INTERVIEW_LOCATION_DESC_A;

        assertParseFailure(parser, organisationPrefix, errorMessage);

        // Representative of “unexpected prefix before first required one”.
        String phonePrefix = " " + PHONE_DESC_AMY
                + PREFIX_INDEX + "1"
                + INTERVIEW_HEADER_DESC_A
                + " " + PREFIX_DATE + "2024-10-10"
                + " " + PREFIX_TIME + "14:00"
                + INTERVIEW_LOCATION_DESC_A;

        assertParseFailure(parser, phonePrefix, errorMessage);

        // Representative of “unexpected prefix appended at end”.
        String rolePrefix = " " + PREFIX_INDEX + "1"
                + INTERVIEW_HEADER_DESC_A
                + " " + PREFIX_DATE + "2024-10-10"
                + " " + PREFIX_TIME + "14:00"
                + INTERVIEW_LOCATION_DESC_A
                + ROLE_DESC_AMY;

        assertParseFailure(parser, rolePrefix, errorMessage);
    }
}
