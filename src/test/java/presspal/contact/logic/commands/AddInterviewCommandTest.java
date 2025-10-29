package presspal.contact.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static presspal.contact.commons.core.Config.DISPLAY_DATE_TIME_FORMATTER;
import static presspal.contact.logic.commands.CommandTestUtil.assertCommandFailure;
import static presspal.contact.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static presspal.contact.testutil.TypicalPersons.getTypicalContactBook;

import org.junit.jupiter.api.Test;

import presspal.contact.commons.core.index.Index;
import presspal.contact.logic.Messages;
import presspal.contact.logic.commands.exceptions.CommandException;
import presspal.contact.model.Model;
import presspal.contact.model.ModelManager;
import presspal.contact.model.UserPrefs;
import presspal.contact.model.interview.Interview;
import presspal.contact.model.person.Person;
import presspal.contact.testutil.InterviewBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code AddInterviewCommand}.
 */
public class AddInterviewCommandTest {

    private Model model = new ModelManager(getTypicalContactBook(), new UserPrefs());

    @Test
    public void execute_addInterviewSuccessful() {

        // EP: valid command for existing person
        InterviewBuilder builder = new InterviewBuilder().withHeader("Sample Interview A");
        AddInterviewCommand cmd = new AddInterviewCommand(builder.build(), INDEX_FIRST_PERSON);

        Person target = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Model expectedModel = new ModelManager(model.getContactBook(), new UserPrefs());

        try {
            CommandResult result = cmd.execute(expectedModel);

            // EP: Success message formatting correctness
            String expectedMessage = String.format(AddInterviewCommand.MESSAGE_ADD_INTERVIEW_SUCCESS,
                    target.getName(), builder.build().getDisplayString());
            assertEquals(expectedMessage, result.getFeedbackToUser());

            // EP: Post-conditions, state mutation on success
            // now delete the interview we just added (it should be the last interview)
            Person targetAfterAdd = expectedModel.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
            int lastIndex = targetAfterAdd.getInterviews().getInterviews().size();

            Interview toDelete = targetAfterAdd.getInterviews().getInterviews().get(lastIndex - 1);

            // Cleanup path also verifies the delete success partition.
            DeleteInterviewCommand deleteCmd = new DeleteInterviewCommand(
                INDEX_FIRST_PERSON, Index.fromOneBased(lastIndex));
            CommandResult deleteResult = deleteCmd.execute(expectedModel);
            String expectedDeleteMessage = String.format(
                    DeleteInterviewCommand.MESSAGE_DELETE_INTERVIEW_SUCCESS,
                    targetAfterAdd.getName(),
                    toDelete.getDisplayString());
            assertEquals(expectedDeleteMessage, deleteResult.getFeedbackToUser());
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    @Test
    public void execute_duplicateInterview_throwsCommandException() {
        // EP: invalid, duplicate-by-equality (same header/location/datetime) for same person
        // build an interview equal to an existing one on the first typical person
        Person p = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        // assume sample data has at least one interview; pick first
        presspal.contact.model.interview.Interview existing = p.getInterviews().getInterviews().get(0);

        AddInterviewCommand cmd = new AddInterviewCommand(existing, INDEX_FIRST_PERSON);
        assertCommandFailure(cmd, model, AddInterviewCommand.MESSAGE_DUPLICATE_INTERVIEW);
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() {
        // EP: Invalid index
        Index outOfBound = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddInterviewCommand cmd = new AddInterviewCommand(new InterviewBuilder().build(), outOfBound);

        assertCommandFailure(cmd, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        // EP: Equality semantics of command objects
        InterviewBuilder builder = new InterviewBuilder().withHeader("X");
        AddInterviewCommand a = new AddInterviewCommand(builder.build(), INDEX_FIRST_PERSON);
        AddInterviewCommand b = new AddInterviewCommand(builder.build(), INDEX_FIRST_PERSON);
        AddInterviewCommand c = new AddInterviewCommand(
            new InterviewBuilder().withHeader("Y").build(), INDEX_FIRST_PERSON);

        // same object
        assertTrue(a.equals(a));
        // same values
        assertTrue(a.equals(b));
        // different types
        assertFalse(a.equals(1));
        // null
        assertFalse(a.equals(null));
        // different interview
        assertFalse(a.equals(c));
    }

    @Test
    public void toStringMethod() {
        // EP: String representation contains canonical prefix
        AddInterviewCommand cmd = new AddInterviewCommand(
            new InterviewBuilder().withHeader("Z").build(), INDEX_FIRST_PERSON);
        String expected = AddInterviewCommand.class.getCanonicalName() + "{toAdd=" + cmd.toString().split("\\{", 2)[1];
        // toString contains dynamic content; ensure it starts with canonical name
        assertTrue(cmd.toString().startsWith(AddInterviewCommand.class.getCanonicalName()));
        assertEquals(cmd.toString(), cmd.toString());
    }

    @Test
    public void execute_duplicateDateTimeSamePerson_failure() {
        // EP: invalid, duplicate-by-datetime only (different header/location) on same person
        Person p = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Interview existing = p.getInterviews().getInterviews().get(0);

        Interview clash = new InterviewBuilder()
                .withHeader("Different Topic")
                .withLocation("Different Place")
                .withDate(existing.getDateTime())
                .build();

        AddInterviewCommand cmd = new AddInterviewCommand(clash, INDEX_FIRST_PERSON);

        // EP: Error message formatting (date/time rendering) uses Config formatter.
        // build expected message with the SAME formatter as the command:
        String when = existing.getDateTime().format(DISPLAY_DATE_TIME_FORMATTER);
        String expected = String.format(AddInterviewCommand.MESSAGE_DUPLICATE_DATETIME, when);

        assertCommandFailure(cmd, model, expected);
    }

    @Test
    public void execute_sameDateTimeDifferentPerson_success() throws Exception {
        // EP: valid, same datetime across different contacts

        // Base fixture
        Person p1 = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Index second = Index.fromOneBased(2); // assumes TypicalPersons has >= 2
        Person p2 = model.getFilteredPersonList().get(second.getZeroBased());

        // Build sets of existing datetimes
        java.util.Set<java.time.LocalDateTime> p1Times = p1.getInterviews().getInterviews()
                .stream().map(Interview::getDateTime).collect(java.util.stream.Collectors.toSet());
        java.util.Set<java.time.LocalDateTime> p2Times = p2.getInterviews().getInterviews()
                .stream().map(Interview::getDateTime).collect(java.util.stream.Collectors.toSet());

        // try to find a datetime that P1 already has and P2 does not
        java.time.LocalDateTime base = p1Times.stream()
                .filter(dt -> !p2Times.contains(dt))
                .findFirst()
                .orElse(null);

        Model working = new ModelManager(model.getContactBook(), new UserPrefs());

        // if all P1 times clash with P2, create a fresh datetime by adding minutes
        if (base == null) {
            // start from any P1 time and increment until free for P2
            java.time.LocalDateTime candidate = p1.getInterviews().getInterviews().get(0).getDateTime();
            while (p2Times.contains(candidate) || p1Times.contains(candidate)) {
                candidate = candidate.plusMinutes(1);
            }
            // add to P1 in the working model so P1 now has this exact datetime
            Interview p1New = new InterviewBuilder()
                    .withHeader("Seeding P1")
                    .withLocation("Room 101")
                    .withDate(candidate)
                    .build();
            new AddInterviewCommand(p1New, INDEX_FIRST_PERSON).execute(working);
            base = candidate;
        }

        // add the SAME datetime to Person 2, should succeed
        Interview forP2 = new InterviewBuilder()
                .withHeader("Parallel Slot")
                .withLocation("Cafe")
                .withDate(base)
                .build();

        CommandResult result = new AddInterviewCommand(forP2, second).execute(working);

        String expected = String.format(AddInterviewCommand.MESSAGE_ADD_INTERVIEW_SUCCESS,
                p2.getName(), forP2.getDisplayString());
        assertEquals(expected, result.getFeedbackToUser());
    }
}
