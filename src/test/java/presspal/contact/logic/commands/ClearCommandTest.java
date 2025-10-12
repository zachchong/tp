package presspal.contact.logic.commands;

import static presspal.contact.logic.commands.CommandTestUtil.assertCommandSuccess;
import static presspal.contact.testutil.TypicalPersons.getTypicalContactBook;

import org.junit.jupiter.api.Test;

import presspal.contact.model.ContactBook;
import presspal.contact.model.Model;
import presspal.contact.model.ModelManager;
import presspal.contact.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyContactBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyContactBook_success() {
        Model model = new ModelManager(getTypicalContactBook(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalContactBook(), new UserPrefs());
        expectedModel.setContactBook(new ContactBook());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
