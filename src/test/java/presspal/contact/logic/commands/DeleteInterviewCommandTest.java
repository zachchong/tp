package presspal.contact.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static presspal.contact.logic.commands.CommandTestUtil.assertCommandFailure;
import static presspal.contact.logic.commands.CommandTestUtil.assertCommandSuccess;
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
import presspal.contact.model.person.Person;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteInterviewCommand}.
 */
public class DeleteInterviewCommandTest {

    private Model model = new ModelManager(getTypicalContactBook(), new UserPrefs());

    @Test
    public void execute_validIndicesUnfilteredList_success() throws Exception {
        // Assumes first person has at least one interview in TypicalPersons
        Index personIndex = INDEX_FIRST_PERSON;
        Index interviewIndex = Index.fromOneBased(1);

        Person before = model.getFilteredPersonList().get(personIndex.getZeroBased());
        int beforeCount = before.getInterviews().getInterviews().size();

        DeleteInterviewCommand cmd = new DeleteInterviewCommand(personIndex, interviewIndex);

        String expectedMessage = String.format(DeleteInterviewCommand.MESSAGE_DELETE_INTERVIEW_SUCCESS,
                interviewIndex.getOneBased(), Messages.format(before));

        Model expectedModel = new ModelManager(model.getContactBook(), new UserPrefs());
        // mirror expected mutation by deleting the first interview
        Person expectedBefore = expectedModel.getFilteredPersonList().get(personIndex.getZeroBased());
        expectedBefore.getInterviews().remove(
                expectedBefore.getInterviews().getInterviews().get(interviewIndex.getZeroBased()));
        // setPerson to trigger listeners and maintain immutability pattern
        expectedModel.setPerson(expectedBefore, new Person(
                expectedBefore.getName(),
                expectedBefore.getPhone(),
                expectedBefore.getEmail(),
                expectedBefore.getOrganisation(),
                expectedBefore.getRole(),
                expectedBefore.getCategories(),
                expectedBefore.getInterviews()));

        assertCommandSuccess(cmd, model, expectedMessage, expectedModel);

        Person after = model.getFilteredPersonList().get(personIndex.getZeroBased());
        int afterCount = after.getInterviews().getInterviews().size();
        assertEquals(beforeCount - 1, afterCount);
    }

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

        assertCommandFailure(cmd, model, DeleteInterviewCommand.MESSAGE_INVALID_INTERVIEW_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndicesFilteredList_success() {
        // Filter to show only first person, then delete an interview from that person
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index personIndex = INDEX_FIRST_PERSON; // within filtered list
        Index interviewIndex = Index.fromOneBased(1);

        Person before = model.getFilteredPersonList().get(personIndex.getZeroBased());
        String expectedMessage = String.format(DeleteInterviewCommand.MESSAGE_DELETE_INTERVIEW_SUCCESS,
                interviewIndex.getOneBased(), Messages.format(before));

        Model expectedModel = new ModelManager(model.getContactBook(), new UserPrefs());
        Person expectedBefore = expectedModel.getFilteredPersonList().get(personIndex.getZeroBased());
        expectedBefore.getInterviews().remove(
                expectedBefore.getInterviews().getInterviews().get(interviewIndex.getZeroBased()));
        expectedModel.setPerson(expectedBefore, new Person(
                expectedBefore.getName(),
                expectedBefore.getPhone(),
                expectedBefore.getEmail(),
                expectedBefore.getOrganisation(),
                expectedBefore.getRole(),
                expectedBefore.getCategories(),
                expectedBefore.getInterviews()));
        // optional: if you expect to hide persons after mutation, update filter, else omit
        // showNoPerson(expectedModel);

        DeleteInterviewCommand cmd = new DeleteInterviewCommand(personIndex, interviewIndex);
        assertCommandSuccess(cmd, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_throwsCommandException() {
        // Filter to only first person
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundPerson = INDEX_SECOND_PERSON;
        // Ensure it's still in bounds of the full list
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

    /** Optionally mirror the helper from your reference to empty the list. */
    @SuppressWarnings("unused")
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);
        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
