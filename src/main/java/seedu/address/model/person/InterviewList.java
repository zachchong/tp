package seedu.address.model.person;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Person's list of interviews in the address book.
 */
public class InterviewList {

    private final List<String> interviewList; // TO BE UPDATED TO USE ACTUAL INTERVIEW CLASS BY v1.3

    /**
     * Constructs a {@code InterviewList}.
     *
     * @param interviewList List of initial interviews.
     */
    public InterviewList(List<String> interviewList) {
        if (interviewList != null) {
            this.interviewList = interviewList;
        } else {
            this.interviewList = new ArrayList<>();
        }
    }

    /**
     * Returns the interview list.
     */
    public List<String> getInterviewList() {
        return interviewList;
    }

    /**
     * Adds an interview to the interview list.
     */
    public void add(String interview) {
        interviewList.add(interview);
    }

    /**
     * Removes an interview from the interview list.
     */
    public void remove(String interview) {
        interviewList.remove(interview);
    }

    /**
     * Returns true if an interview is contained in interview list.
     */
    public boolean contains(String interview) {
        return interviewList.contains(interview);
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
        return interviewList.equals(otherList.interviewList);
    }

    @Override
    public int hashCode() {
        return interviewList.hashCode();
    }

    @Override
    public String toString() {
        return interviewList.toString();
    }

}
