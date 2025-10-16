package presspal.contact.logic.commands;

import presspal.contact.model.Model;

import static java.util.Objects.requireNonNull;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_INDEX;
import static presspal.contact.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Lists all interviews of a person in the contact book to the user.
 */
public class ListInterviewCommand extends Command {

    public static final String COMMAND_WORD = "listInterview";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the contact book. "
            + "Parameters: "
            + PREFIX_INDEX + "INDEX "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 ";

    public static final String MESSAGE_SUCCESS = "Listed all the interview for this person";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
