package presspal.contact.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static presspal.contact.logic.commands.CommandTestUtil.assertCommandFailure;
import static presspal.contact.logic.commands.CommandTestUtil.showPersonAtIndex;
import static presspal.contact.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static presspal.contact.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static presspal.contact.testutil.TypicalPersons.getTypicalContactBook;

import org.junit.jupiter.api.Test;

import presspal.contact.commons.core.index.Index;
import presspal.contact.logic.Messages;
import presspal.contact.model.Model;
import presspal.contact.model.ModelManager;
import presspal.contact.model.UserPrefs;
import presspal.contact.model.person.InterviewList;
import presspal.contact.model.person.Person;
import presspal.contact.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteInterviewCommand}.
 */
public class DeleteInterviewCommandTest {

    private Model model = new ModelManager(getTypicalContactBook(), new UserPrefs());

    @Test
    public void execute_invalidPersonIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundPerson = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteInterviewCommand cmd = new DeleteInterviewCommand(outOfBoundPerson, Index.fromOneBased(1));

        assertCommandFailure(cmd, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidInterviewIndexUnfilteredList_throwsCommandException() {
        Index personIndex = INDEX_FIRST_PERSON;
        Person p = model.getFilteredPersonList().get(personIndex.getZeroBased());
        int size = p.getInterviews().getInterviews().size();

        // pick an interview index larger than the list size
        DeleteInterviewCommand cmd = new DeleteInterviewCommand(personIndex, Index.fromOneBased(size + 1));

        // if no interviews, we expect the name-based "no interviews" message.
        // Otherwise, expect the range "1..N" where N = size of interview list.
        String expected = (size == 0)
                ? String.format(DeleteInterviewCommand.MESSAGE_NO_INTERVIEWS, p.getName())
                : String.format(DeleteInterviewCommand.MESSAGE_INVALID_INTERVIEW_DISPLAYED_INDEX, "1.." + size);

        assertCommandFailure(cmd, model, expected);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundPerson = INDEX_SECOND_PERSON;

        assertTrue(outOfBoundPerson.getZeroBased() < model.getContactBook().getPersonList().size());

        DeleteInterviewCommand cmd = new DeleteInterviewCommand(outOfBoundPerson, Index.fromOneBased(1));
        assertCommandFailure(cmd, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteInterviewCommand a = new DeleteInterviewCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1));
        DeleteInterviewCommand b = new DeleteInterviewCommand(INDEX_FIRST_PERSON, Index.fromOneBased(1));
        DeleteInterviewCommand c = new DeleteInterviewCommand(INDEX_SECOND_PERSON, Index.fromOneBased(1));
        DeleteInterviewCommand d = new DeleteInterviewCommand(INDEX_FIRST_PERSON, Index.fromOneBased(2));

        // same object
        assertTrue(a.equals(a));
        // same values
        assertTrue(a.equals(b));
        // different types
        assertFalse(a.equals(1));
        // null
        assertFalse(a.equals(null));
        // different person index
        assertFalse(a.equals(c));
        // different interview index
        assertFalse(a.equals(d));
    }

    @Test
    public void toStringMethod() {
        Index person = Index.fromOneBased(1);
        Index interview = Index.fromOneBased(2);
        DeleteInterviewCommand cmd = new DeleteInterviewCommand(person, interview);
        String expected = DeleteInterviewCommand.class.getCanonicalName()
                + "{personIndex=" + person + ", interviewIndex=" + interview + "}";
        assertEquals(expected, cmd.toString());
    }

    @Test
    public void execute_noInterviews_failureWithPersonName() {
        Model model = new ModelManager(getTypicalContactBook(), new UserPrefs());

        // build a person with no interviews
        Person empty =
                new PersonBuilder()
                        .withName("Empty Person")
                        .build();

        model.addPerson(empty);

        Index personIdx = Index.fromOneBased(model.getFilteredPersonList().size());
        DeleteInterviewCommand cmd = new DeleteInterviewCommand(personIdx, Index.fromOneBased(1));

        String expected = String.format(DeleteInterviewCommand.MESSAGE_NO_INTERVIEWS, empty.getName());
        assertCommandFailure(cmd, model, expected);
    }
}
