package presspal.contact.logic.commands;

import static java.util.Objects.requireNonNull;

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
 * Deletes an interview identified using its displayed index from a person's interview list in the contact book.
 */
public class DeleteInterviewCommand extends Command {

    public static final String COMMAND_WORD = "deleteInterview";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an interview from the specified person.\n"
            + "Parameters: i/PERSON_INDEX v/INTERVIEW_INDEX (both must be positive integers)\n"
            + "Example: " + COMMAND_WORD + " i/1 v/2";

    public static final String MESSAGE_DELETE_INTERVIEW_SUCCESS =
            "The following interview has been deleted from %1$s:\n%2$s";

    public static final String MESSAGE_INVALID_INTERVIEW_DISPLAYED_INDEX =
            "Invalid interview index. Valid range: %s.";

    public static final String MESSAGE_UNEXPECTED_PREFIXES =
            "Only i/ (person) and v/ (interview) are allowed.\nRemove: %s";

    public static final String MESSAGE_NO_INTERVIEWS =
            "%s has no scheduled interviews.";

    private final Index personIndex;
    private final Index interviewIndex;

    /**
     * Creates an DeleteInterviewCommand to delete a specified {@code Interview} from
     * a specified {@code Person}.
     */
    public DeleteInterviewCommand(Index personIndex, Index interviewIndex) {
        requireNonNull(personIndex);
        requireNonNull(interviewIndex);
        this.personIndex = personIndex;
        this.interviewIndex = interviewIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();
        if (personIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person targetPerson = lastShownList.get(personIndex.getZeroBased());

        // obtain the InterviewList directly from the person
        InterviewList interviewList = targetPerson.getInterviews();
        List<Interview> interviews = interviewList.getInterviews();

        if (interviews.isEmpty()) {
            throw new CommandException(String.format(
                    MESSAGE_NO_INTERVIEWS, targetPerson.getName()));
        }

        int maxIndexInclusive = interviews.size();
        if (interviewIndex.getZeroBased() >= maxIndexInclusive) {
            String range = String.format("1..%d", maxIndexInclusive);
            throw new CommandException(String.format(MESSAGE_INVALID_INTERVIEW_DISPLAYED_INDEX, range));
        }

        // remove the interview from the InterviewList
        Interview toDelete = interviews.get(interviewIndex.getZeroBased());
        interviewList.remove(toDelete);

        // create a new Person instance with the updated InterviewList
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

        return new CommandResult(String.format(
                MESSAGE_DELETE_INTERVIEW_SUCCESS,
                edited.getName(),
                toDelete.getDisplayString()));
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DeleteInterviewCommand)) {
            return false;
        }
        DeleteInterviewCommand o = (DeleteInterviewCommand) other;
        return personIndex.equals(o.personIndex) && interviewIndex.equals(o.interviewIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("personIndex", personIndex)
                .add("interviewIndex", interviewIndex)
                .toString();
    }
}
