package presspal.contact.logic.commands;

import static java.util.Objects.requireNonNull;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_INDEX;
import static presspal.contact.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import javax.annotation.processing.Messager;

import presspal.contact.commons.core.index.Index;
import presspal.contact.logic.Messages;
import presspal.contact.logic.commands.exceptions.CommandException;
import presspal.contact.model.Model;
import presspal.contact.model.person.Person;

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

    private final Index targetIndex;

    public ListInterviewCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToViewInterviews = lastShownList.get(targetIndex.getZeroBased());
        
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(personToDelete)));
    }
}
