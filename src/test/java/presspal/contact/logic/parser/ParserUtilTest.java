package presspal.contact.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static presspal.contact.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static presspal.contact.testutil.Assert.assertThrows;
import static presspal.contact.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import presspal.contact.logic.parser.exceptions.ParseException;
import presspal.contact.model.category.Category;
import presspal.contact.model.interview.Header;
import presspal.contact.model.interview.Location;
import presspal.contact.model.person.Email;
import presspal.contact.model.person.Name;
import presspal.contact.model.person.Organisation;
import presspal.contact.model.person.Phone;
import presspal.contact.model.person.Role;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ORGANISATION = " ";
    private static final String INVALID_ROLE = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_CATEGORY = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ORGANISATION = "OnlyFriends";
    private static final String VALID_ROLE = "Student";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_CATEGORY_1 = "friend";
    private static final String VALID_CATEGORY_2 = "neighbour";
    private static final String VALID_HEADER = "Interview with ABC Corp";
    private static final String INVALID_HEADER = " ";
    private static final String VALID_LOCATION = "123 Business St, #02-25";
    private static final String INVALID_LOCATION = " ";
    private static final String VALID_DATE = "2025-09-12";
    private static final String VALID_TIME = "15:30";
    private static final String INVALID_DATE = "12-09-2025"; // wrong format
    private static final String INVALID_TIME = "3pm"; // wrong format

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseOrganisation_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseOrganisation((String) null));
    }

    @Test
    public void parseOrganisation_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseOrganisation(INVALID_ORGANISATION));
    }

    @Test
    public void parseOrganisation_validValueWithoutWhitespace_returnsOrganisation() throws Exception {
        Organisation expectedOrganisation = new Organisation(VALID_ORGANISATION);
        assertEquals(expectedOrganisation, ParserUtil.parseOrganisation(VALID_ORGANISATION));
    }

    @Test
    public void parseOrganisation_validValueWithWhitespace_returnsTrimmedOrganisation() throws Exception {
        String organisationWithWhitespace = WHITESPACE + VALID_ORGANISATION + WHITESPACE;
        Organisation expectedOrganisation = new Organisation(VALID_ORGANISATION);
        assertEquals(expectedOrganisation, ParserUtil.parseOrganisation(organisationWithWhitespace));
    }

    @Test
    public void parseRole_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRole((String) null));
    }

    @Test
    public void parseRole_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRole(INVALID_ROLE));
    }

    @Test
    public void parseRole_validValueWithoutWhitespace_returnsRole() throws Exception {
        Role expectedRole = new Role(VALID_ROLE);
        assertEquals(expectedRole, ParserUtil.parseRole(VALID_ROLE));
    }

    @Test
    public void parseRole_validValueWithWhitespace_returnsTrimmedRole() throws Exception {
        String roleWithWhitespace = WHITESPACE + VALID_ROLE + WHITESPACE;
        Role expectedRole = new Role(VALID_ROLE);
        assertEquals(expectedRole, ParserUtil.parseRole(roleWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseCategory_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCategory(null));
    }

    @Test
    public void parseCategory_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseCategory(INVALID_CATEGORY));
    }

    @Test
    public void parseCategory_validValueWithoutWhitespace_returnsCategory() throws Exception {
        Category expectedCategory = new Category(VALID_CATEGORY_1);
        assertEquals(expectedCategory, ParserUtil.parseCategory(VALID_CATEGORY_1));
    }

    @Test
    public void parseCategory_validValueWithWhitespace_returnsTrimmedCategory() throws Exception {
        String categoryWithWhitespace = WHITESPACE + VALID_CATEGORY_1 + WHITESPACE;
        Category expectedCategory = new Category(VALID_CATEGORY_1);
        assertEquals(expectedCategory, ParserUtil.parseCategory(categoryWithWhitespace));
    }

    @Test
    public void parseCategories_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCategories(null));
    }

    @Test
    public void parseCategories_collectionWithInvalidCategories_throwsParseException() {
        assertThrows(ParseException.class, () ->
                ParserUtil.parseCategories(Arrays.asList(VALID_CATEGORY_1, INVALID_CATEGORY)));
    }

    @Test
    public void parseCategories_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseCategories(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTCategories_collectionWithValidCategories_returnsCategorySet() throws Exception {
        Set<Category> actualCategorySet = ParserUtil.parseCategories(Arrays.asList(VALID_CATEGORY_1, VALID_CATEGORY_2));
        Set<Category> expectedCategorySet =
                new HashSet<Category>(Arrays.asList(new Category(VALID_CATEGORY_1), new Category(VALID_CATEGORY_2)));

        assertEquals(expectedCategorySet, actualCategorySet);
    }

    @Test
    public void parseHeader_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseHeader((String) null));
    }

    @Test
    public void parseHeader_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseHeader(INVALID_HEADER));
    }

    @Test
    public void parseHeader_validValue_returnsHeader() throws Exception {
        Header expected = new Header(VALID_HEADER);
        assertEquals(expected, ParserUtil.parseHeader(VALID_HEADER));
    }

    @Test
    public void parseLocation_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseLocation((String) null));
    }

    @Test
    public void parseLocation_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseLocation(INVALID_LOCATION));
    }

    @Test
    public void parseLocation_validValue_returnsLocation() throws Exception {
        Location expected = new Location(VALID_LOCATION);
        assertEquals(expected, ParserUtil.parseLocation(VALID_LOCATION));
    }

    @Test
    public void parseLocalDateTime_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseLocalDateTime(null, VALID_TIME));
        assertThrows(NullPointerException.class, () -> ParserUtil.parseLocalDateTime(VALID_DATE, null));
    }

    @Test
    public void parseLocalDateTime_invalidDateOrTime_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseLocalDateTime(INVALID_DATE, VALID_TIME));
        assertThrows(ParseException.class, () -> ParserUtil.parseLocalDateTime(VALID_DATE, INVALID_TIME));
    }

    @Test
    public void parseLocalDateTime_validValue_returnsLocalDateTime() throws Exception {
        LocalDateTime expected = LocalDateTime.parse(VALID_DATE + "T" + VALID_TIME);
        assertEquals(expected, ParserUtil.parseLocalDateTime(VALID_DATE, VALID_TIME));
    }
}
