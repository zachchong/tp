package presspal.contact.model.person;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import presspal.contact.model.interview.Interview;

/**
 * Represents a Person's list of interviews in the contact book.
 */
public class InterviewList {

    // list of interview(s) for a person
    private final List<Interview> interviews;

    /**
     * Constructs a {@code Interviews}.
     *
     * @param interviews List of initial interviews.
     */
    public InterviewList(List<Interview> interviews) {
        this.interviews = Objects.requireNonNullElseGet(interviews, ArrayList::new);
    }

    /**
     * Returns the interviews list.
     */
    public List<Interview> getInterviews() {
        return interviews;
    }

    /**
     * Returns a list of upcoming interviews by comparing current time and time of interview.
     */
    public List<Interview> getUpcomingInterviews() {
        List<Interview> upcomingInterviews = new ArrayList<>();
        return upcomingInterviews; // return an empty list for now. TBC in v1.4
    }

    /**
     * Adds an interview to the interview list.
     */
    public void add(Interview interview) {
        requireNonNull(interview, "Interview cannot be null");
        interviews.add(interview);
    }

    /**
     * Removes an interview from the interview list.
     */
    public void remove(Interview interview) {
        requireNonNull(interview, "Interview cannot be null");
        interviews.remove(interview);
    }

    /**
     * Returns true if an interview is contained in interview list.
     */
    public boolean contains(Interview interview) {
        return interviews.contains(interview);
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
        if (interviews.isEmpty()) {
            return "No interviews scheduled.";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < interviews.size(); i++) {
            sb.append(i + 1)
                    .append(". ")
                    .append(interviews.get(i))
                    .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }

}
