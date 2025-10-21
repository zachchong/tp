package presspal.contact.logic.commands;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static presspal.contact.logic.commands.CommandTestUtil.assertCommandSuccess;
import static presspal.contact.testutil.TypicalPersons.ALICE;
import static presspal.contact.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import presspal.contact.logic.commands.exceptions.CommandException;
import presspal.contact.model.Model;
import presspal.contact.model.ModelManager;
import presspal.contact.model.UserPrefs;
import presspal.contact.model.person.Person;
import presspal.contact.testutil.PersonBuilder;

/**
 * Contains integration tests and unit tests for {@code NextInterviewCommand}.
 */
public class NextInterviewCommandTest {

    private Model model;
    private Person aliceWithUpcomingInterview;
    private Person bobWithLaterInterview;

    @BeforeEach
    public void setUp() {
        aliceWithUpcomingInterview = new PersonBuilder(ALICE)
                .withInterviews("Google Interview", "Google HQ", LocalDateTime.now().plusDays(1))
                .build();
        bobWithLaterInterview = new PersonBuilder(BOB)
                .withInterviews("Meta Interview", "Meta HQ", LocalDateTime.now().plusDays(3))
                .build();

        model = new ModelManager();
        model.addPerson(aliceWithUpcomingInterview);
        model.addPerson(bobWithLaterInterview);
    }

    @Test
    public void execute_emptyModel_throwsCommandException() {
        model = new ModelManager(); // empty contact book
        NextInterviewCommand command = new NextInterviewCommand();
        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void execute_showsNextUpcomingInterview_success() throws Exception {
        NextInterviewCommand command = new NextInterviewCommand();

        CommandResult result = command.execute(model);

        String expectedMessage = String.format(
                NextInterviewCommand.MESSAGE_SUCCESS,
                aliceWithUpcomingInterview.getName(),
                aliceWithUpcomingInterview.getNextUpcomingInterview().get()
        );

        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    @Test
    public void execute_firstPersonNoUpcomingInterview_returnsNoUpcomingMessage() throws Exception {
        // Replace first person with someone who has no interviews
        Person personWithoutInterview = new PersonBuilder().withName("Charlie").build();
        model.setPerson(model.getFilteredPersonList().get(0), personWithoutInterview);

        NextInterviewCommand command = new NextInterviewCommand();
        String expectedMessage = String.format(
                NextInterviewCommand.MESSAGE_NO_UPCOMING,
                personWithoutInterview.getName()
        );

        Model expectedModel = new ModelManager(model.getContactBook(), new UserPrefs());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        NextInterviewCommand command1 = new NextInterviewCommand();
        NextInterviewCommand command2 = new NextInterviewCommand();

        // same object -> returns true
        assertEquals(true, command1.equals(command1));

        // different instances -> still considered equal
        assertEquals(true, command1.equals(command2));

        // different type -> returns false
        assertEquals(false, command1.equals(1));

        // null -> returns false
        assertEquals(false, command1.equals(null));
    }
}
