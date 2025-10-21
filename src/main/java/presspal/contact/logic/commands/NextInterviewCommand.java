package presspal.contact.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import presspal.contact.commons.util.ToStringBuilder;
import presspal.contact.logic.Messages;
import presspal.contact.logic.commands.exceptions.CommandException;
import presspal.contact.model.Model;
import presspal.contact.model.interview.Interview;
import presspal.contact.model.person.Person;

/**
 * Shows the next upcoming interview of the first person in the filtered list.
 */
public class NextInterviewCommand extends Command {

    public static final String COMMAND_WORD = "nextInterview";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the next upcoming interview of the first person in the contact book.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Next upcoming interview for %1$s:\n%2$s";
    public static final String MESSAGE_NO_UPCOMING = "%1$s has no upcoming interviews.";

    public NextInterviewCommand() {
        // No index needed
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (lastShownList.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_NO_PERSONS);
        }

        // Get first person in filtered list
        Person personToView = lastShownList.get(0);
        Optional<Interview> nextInterview = personToView.getNextUpcomingInterview();

        if (nextInterview.isEmpty()) {
            return new CommandResult(String.format(MESSAGE_NO_UPCOMING, personToView.getName()));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                personToView.getName(), nextInterview.get()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this || other instanceof NextInterviewCommand;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
