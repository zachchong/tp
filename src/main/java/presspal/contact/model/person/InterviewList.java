package presspal.contact.model.person;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import presspal.contact.model.interview.Interview;

/**
 * Represents a Person's list of interviews in the contact book.
 */
public class InterviewList {
    // newest to oldest invariant
    private static final Comparator<Interview> NEWEST_FIRST =
            Comparator.comparing(Interview::getDateTime).reversed();

    // list of interview(s) for a person
    private final List<Interview> interviews;

    /**
     * Constructs a {@code Interviews}.
     *
     * @param interviews List of initial interviews.
     */
    public InterviewList(List<Interview> interviews) {
        this.interviews = new ArrayList<>(Objects.requireNonNullElseGet(interviews, ArrayList::new));
        this.interviews.sort(NEWEST_FIRST);
    }

    /**
     * Returns an unmodifiable interviews list.
     */
    public List<Interview> getInterviews() {
        return Collections.unmodifiableList(interviews);
    }

    /**
     * Returns the next upcoming interview by comparing current time and time of interview.
     * If there are no upcoming interviews, returns an empty Optional.
     */
    public Optional<Interview> getNextUpcoming() {
        LocalDateTime now = LocalDateTime.now();
        return interviews.stream()
                .filter(iv -> iv.getDateTime().isAfter(now))
                .min(Comparator.comparing(Interview::getDateTime));
    }

    /**
     * Adds an interview to the interview list.
     */
    public void add(Interview interview) {
        requireNonNull(interview, "Interview cannot be null");
        int pos = Collections.binarySearch(interviews, interview, NEWEST_FIRST);
        if (pos < 0) {
            pos = -pos - 1; // insertion point
        }
        interviews.add(pos, interview);
    }

    /**
     * Removes an interview from the interview list.
     */
    public void remove(Interview interview) {
        requireNonNull(interview, "Interview cannot be null");
        interviews.remove(interview);
        // no need to resort, as order is maintained
    }

    /**
     * Returns true if an interview is contained in interview list.
     */
    public boolean contains(Interview interview) {
        return interviews.contains(interview);
    }

    /**
     * Returns a numbered list of interviews as a string.
     */
    public String getNumberedInterviews() {
        if (interviews.isEmpty()) {
            return "No interviews scheduled.";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < interviews.size(); i++) {
            sb.append(i + 1)
                    .append(". ")
                    .append(interviews.get(i).getDisplayString())
                    .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof InterviewList)) {
            return false;
        }

        InterviewList otherList = (InterviewList) other;
        return interviews.equals(otherList.interviews);
    }

    @Override
    public int hashCode() {
        return interviews.hashCode();
    }

    @Override
    public String toString() {
        return interviews.stream()
                .map(Interview::toString)
                .collect(java.util.stream.Collectors.joining(","));
    }
}
