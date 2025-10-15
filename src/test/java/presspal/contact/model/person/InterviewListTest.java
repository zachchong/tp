package presspal.contact.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import presspal.contact.model.interview.Header;
import presspal.contact.model.interview.Interview;
import presspal.contact.model.interview.Location;

public class InterviewListTest {
    private InterviewList emptyList;
    private InterviewList populatedList;
    private List<Interview> sampleInterviews;

    private static Interview interview(String header, String location, LocalDateTime dateTime) {
        return new Interview(new Header(header), new Location(location), dateTime);
    }

    @BeforeEach
    public void setUp() {
        sampleInterviews = new ArrayList<>(Arrays.asList(
                interview("Interview at 2359", "Room A", LocalDateTime.of(2025, 1, 1, 23, 59)),
                interview("Interview at 1600", "Room B", LocalDateTime.of(2025, 1, 2, 16, 0))
        ));
        emptyList = new InterviewList(null);
        populatedList = new InterviewList(sampleInterviews);
    }

    @Test
    public void constructor_nullList_initializesEmptyList() {
        InterviewList list = new InterviewList(null);
        assertTrue(list.getInterviews().isEmpty());
    }

    @Test
    public void getInterviewList_returnsCorrectList() {
        assertEquals(sampleInterviews, populatedList.getInterviews());
    }

    @Test
    public void add_addsInterviewSuccessfully() {
        Interview toAdd = interview("Interview at 0000", "Room C",
                LocalDateTime.of(2025, 1, 3, 0, 0));
        emptyList.add(toAdd);
        assertTrue(emptyList.contains(toAdd));
    }

    @Test
    public void remove_removesInterviewSuccessfully() {
        Interview first = sampleInterviews.get(0);
        populatedList.remove(first);
        assertFalse(populatedList.contains(first));
    }

    @Test
    public void contains_returnsTrueIfInterviewExists() {
        Interview existing = sampleInterviews.get(1);
        Interview nonExisting = interview("Interview at 1200", "Room X",
                LocalDateTime.of(2025, 1, 4, 12, 0));

        assertTrue(populatedList.contains(existing));
        assertFalse(populatedList.contains(nonExisting));
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        assertTrue(populatedList.equals(populatedList));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        assertFalse(populatedList.equals("not an Interviews"));
    }

    @Test
    public void equals_differentContents_returnsFalse() {
        InterviewList differentList = new InterviewList(Arrays.asList(
                interview("Interview at 1800", "Room Z", LocalDateTime.of(2025, 1, 5, 18, 0))
        ));
        assertFalse(populatedList.equals(differentList));
    }

    @Test
    public void equals_sameContents_returnsTrue() {
        // recreate interviews with the same values (different instances)
        List<Interview> sameValues = Arrays.asList(
                interview("Interview at 2359", "Room A", LocalDateTime.of(2025, 1, 1, 23, 59)),
                interview("Interview at 1600", "Room B", LocalDateTime.of(2025, 1, 2, 16, 0))
        );
        InterviewList sameList = new InterviewList(new ArrayList<>(sameValues));
        assertTrue(populatedList.equals(sameList));
    }

    @Test
    public void hashCode_sameContents_sameHashCode() {
        List<Interview> sameValues = Arrays.asList(
                interview("Interview at 2359", "Room A", LocalDateTime.of(2025, 1, 1, 23, 59)),
                interview("Interview at 1600", "Room B", LocalDateTime.of(2025, 1, 2, 16, 0))
        );
        InterviewList sameList = new InterviewList(new ArrayList<>(sameValues));
        assertEquals(populatedList.hashCode(), sameList.hashCode());
    }

    @Test
    public void toString_returnsNumberedList() {
        String expected = "1. " + sampleInterviews.get(0)
                + System.lineSeparator()
                + "2. " + sampleInterviews.get(1);
        assertEquals(expected, populatedList.toString());
    }

    @Test
    public void getUpcomingInterviews_returnsEmptyListForNow() {
        assertTrue(populatedList.getUpcomingInterviews().isEmpty());
    }

    @Test
    public void toString_emptyList_returnsNoInterviewsScheduledMessage() {
        InterviewList empty = new InterviewList(null);
        String expected = "No interviews scheduled.";
        assertEquals(expected, empty.toString());
    }

    @Test
    public void add_nullInterview_throwsNullPointerException() {
        InterviewList list = new InterviewList(null);
        assertThrows(NullPointerException.class, () -> list.add(null));
    }

    @Test
    public void remove_nullInterview_throwsNullPointerException() {
        InterviewList list = new InterviewList(null);
        assertThrows(NullPointerException.class, () -> list.remove(null));
    }

}
