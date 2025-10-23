package presspal.contact.logic.commands;

import static java.util.Objects.requireNonNull;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_DATE;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_HEADER;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_INDEX;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_LOCATION;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.List;

import presspal.contact.commons.core.index.Index;
import presspal.contact.commons.util.ToStringBuilder;
import presspal.contact.logic.Messages;
import presspal.contact.logic.commands.exceptions.CommandException;
import presspal.contact.model.Model;
import presspal.contact.model.interview.Interview;
import presspal.contact.model.person.InterviewList;
import presspal.contact.model.person.Person;

/**
 * Adds an interview to the address book.
 */
public class AddInterviewCommand extends Command {

    public static final String COMMAND_WORD = "addInterview";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an interview. "
            + "Parameters: "
            + PREFIX_INDEX + "INDEX "
            + PREFIX_HEADER + "HEADER "
            + PREFIX_DATE + "DATE "
            + PREFIX_TIME + "TIME "
            + PREFIX_LOCATION + "LOCATION\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_INDEX + "1 "
            + PREFIX_HEADER + "Interview with ABC Corp "
            + PREFIX_DATE + "2024-10-10 "
            + PREFIX_TIME + "14:00 "
            + PREFIX_LOCATION + "123, Business St, #02-25 ";



    public static final String MESSAGE_ADD_INTERVIEW_SUCCESS = "The following interview has been added to %1$s:\n%2$s";
    public static final String MESSAGE_DUPLICATE_INTERVIEW = "Failed to add interview. This interview already exists.";

    private final Interview toAdd;
    private final Index personIndex;

    /**
     * Creates an AddInterviewCommand to add the specified interview.
     * @param interview the interview to add
     */
    public AddInterviewCommand(Interview interview, Index personIndex) {
        requireNonNull(interview);
        requireNonNull(personIndex);
        this.toAdd = interview;
        this.personIndex = personIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person targetPerson = lastShownList.get(personIndex.getZeroBased());
        InterviewList interviewList = targetPerson.getInterviews();

        if (interviewList.contains(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_INTERVIEW);
        }

        interviewList.add(toAdd);
        Person edited = new Person(
            targetPerson.getName(),
            targetPerson.getPhone(),
            targetPerson.getEmail(),
            targetPerson.getOrganisation(),
            targetPerson.getRole(),
            targetPerson.getCategories(),
            interviewList
        );

        model.setPerson(targetPerson, edited);

        return new CommandResult(String.format(MESSAGE_ADD_INTERVIEW_SUCCESS,
                targetPerson.getName(), toAdd.getDisplayString()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AddInterviewCommand)) {
            return false;
        }

        AddInterviewCommand otherCommand = (AddInterviewCommand) other;
        return toAdd.equals(otherCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
