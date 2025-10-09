package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Person's list of interviews in the address book.
 */
public class Interviews {

    // list of interview(s) for a person
    private final List<String> interviews; // TO BE UPDATED TO USE ACTUAL INTERVIEW CLASS BY v1.3

    /**
     * Constructs a {@code Interviews}.
     *
     * @param interviews List of initial interviews.
     */
    public Interviews(List<String> interviews) {
        if (interviews != null) {
            this.interviews = interviews;
        } else {
            this.interviews = new ArrayList<>();
        }
    }

    /**
     * Returns the interviews list.
     */
    public List<String> getInterviews() {
        return interviews;
    }

    /**
     * Returns a list of upcoming interviews by comparing current time and time of interview.
     */
    public List<String> getUpcomingInterviews() {
        List<String> upcomingInterviews = new ArrayList<>();
        return upcomingInterviews; // return an empty list for now. TBC in v1.3
    }

    /**
     * Adds an interview to the interview list.
     */
    public void add(String interview) {
        interviews.add(interview);
    }

    /**
     * Removes an interview from the interview list.
     */
    public void remove(String interview) {
        interviews.remove(interview);
    }

    /**
     * Returns true if an interview is contained in interview list.
     */
    public boolean contains(String interview) {
        return interviews.contains(interview);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Interviews)) {
            return false;
        }

        Interviews otherList = (Interviews) other;
        return interviews.equals(otherList.interviews);
    }

    @Override
    public int hashCode() {
        return interviews.hashCode();
    }

    @Override
    public String toString() {
        return interviews.toString();
    }

}
