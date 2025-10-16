package presspal.contact.logic.commands;

import static java.util.Objects.requireNonNull;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.List;

import presspal.contact.commons.core.index.Index;
import presspal.contact.commons.util.ToStringBuilder;
import presspal.contact.logic.Messages;
import presspal.contact.logic.commands.exceptions.CommandException;
import presspal.contact.model.Model;
import presspal.contact.model.person.Person;

/**
 * Lists all interviews of a person in the contact book to the user.
 */
public class ListInterviewCommand extends Command {

    public static final String COMMAND_WORD = "listInterview";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows all the interviews of the person in the contact book. "
            + "Parameters: "
            + PREFIX_INDEX + "INDEX "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 ";

    public static final String MESSAGE_SUCCESS = "Showing Interviews for %1$s:\n %2$s";

    private final Index targetIndex;

    public ListInterviewCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        //get updated list
        List<Person> lastShownList = model.getFilteredPersonList();

        //check if the index is within the list
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        //get the list of person
        Person personToViewInterviews = lastShownList.get(targetIndex.getZeroBased());
        return new CommandResult(String.format(MESSAGE_SUCCESS,
                personToViewInterviews.getName(), personToViewInterviews.getInterviews().getNumberedInterviews()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ListInterviewCommand)) {
            return false;
        }

        ListInterviewCommand listInterviewCommand = (ListInterviewCommand) other;
        return this.targetIndex.equals(listInterviewCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
