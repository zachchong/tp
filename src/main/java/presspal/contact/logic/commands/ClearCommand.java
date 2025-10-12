package presspal.contact.logic.commands;

import static java.util.Objects.requireNonNull;

import presspal.contact.model.ContactBook;
import presspal.contact.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setContactBook(new ContactBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
