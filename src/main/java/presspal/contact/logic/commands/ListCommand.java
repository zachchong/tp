package presspal.contact.logic.commands;

import static java.util.Objects.requireNonNull;
import static presspal.contact.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import presspal.contact.model.Model;

/**
 * Lists all persons in the contact book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons.";
    public static final String MESSAGE_NO_CONTACT = "No contact available.";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        if (model.getFilteredPersonList().isEmpty()) {
            return new CommandResult(MESSAGE_NO_CONTACT);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
