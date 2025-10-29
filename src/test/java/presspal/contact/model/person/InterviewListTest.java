package presspal.contact.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        // expect newest to oldest
        List<Interview> expected = sampleInterviews.stream()
                .sorted(Comparator.comparing(Interview::getDateTime).reversed())
                .collect(Collectors.toList());

        assertEquals(expected, populatedList.getInterviews());

        assertEquals("Interview at 2359", sampleInterviews.get(0).getHeader().toString());
        assertEquals("Interview at 1600", sampleInterviews.get(1).getHeader().toString());
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
        assertFalse(populatedList.equals("not an InterviewList"));
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
    public void toString_returnsCommaSeparatedList() {
        String expected = populatedList.getInterviews().stream()
                .map(Interview::toString)
                .collect(Collectors.joining(","));
        assertEquals(expected, populatedList.toString());
    }

    @Test
    public void toString_emptyList_returnsEmptyString() {
        InterviewList empty = new InterviewList(null);
        assertEquals("", empty.toString());
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

    @Test
    public void getNumberedInterviews_returnsNumberedList() {
        // numbered in newest to oldest order
        String expected = "1. " + populatedList.getInterviews().get(0).getDisplayString()
                + System.lineSeparator()
                + "2. " + populatedList.getInterviews().get(1).getDisplayString();
        assertEquals(expected, populatedList.getNumberedInterviews());
    }

    @Test
    public void getNumberedInterviews_emptyList_returnsNoInterviewsMessage() {
        InterviewList empty = new InterviewList(null);
        assertEquals("No interviews scheduled.", empty.getNumberedInterviews());
    }

    @Test
    public void getNextUpcoming_mixedPastAndFuture_returnsSoonestFuture() {
        Interview past = interview("Past", "Room P", LocalDateTime.now().minusDays(1));
        Interview soon = interview("Soon", "Room S", LocalDateTime.now().plusDays(1));
        Interview later = interview("Later", "Room L", LocalDateTime.now().plusDays(10));

        InterviewList list = new InterviewList(Arrays.asList(past, later, soon));
        Optional<Interview> next = list.getNextUpcoming();

        assertTrue(next.isPresent());
        assertEquals("Soon", next.get().getHeader().toString());
    }

    @Test
    public void getNextUpcoming_noFuture_returnsEmptyOptional() {
        Interview past1 = interview("Past1", "Room P1", LocalDateTime.now().minusDays(2));
        Interview past2 = interview("Past2", "Room P2", LocalDateTime.now().minusHours(1));

        InterviewList list = new InterviewList(Arrays.asList(past1, past2));
        assertTrue(list.getNextUpcoming().isEmpty());
    }

    @Test
    public void constructor_unsortedInput_becomesSortedNewestFirst() {
        List<Interview> unsorted = Arrays.asList(
                interview("Older", "L1", LocalDateTime.of(2025, 1, 1, 10, 0)),
                interview("Newest", "L2", LocalDateTime.of(2025, 1, 3, 10, 0)),
                interview("Middle", "L3", LocalDateTime.of(2025, 1, 2, 10, 0))
        );
        InterviewList list = new InterviewList(unsorted);
        List<Interview> got = list.getInterviews();
        assertEquals("Newest", got.get(0).getHeader().toString());
        assertEquals("Middle", got.get(1).getHeader().toString());
        assertEquals("Older", got.get(2).getHeader().toString());
    }

    @Test
    public void add_insertsMaintainingOrder_newestMiddleOldest() {
        Interview a = interview("Jan02 1600", "A", LocalDateTime.of(2025, 1, 2, 16, 0));
        InterviewList list = new InterviewList(Arrays.asList(a));

        // Add a *newest* one, goes to index 0
        Interview newest = interview("Jan03 0900", "N", LocalDateTime.of(2025, 1, 3, 9, 0));
        list.add(newest);
        assertEquals("Jan03 0900", list.getInterviews().get(0).getHeader().toString());

        // Add an *oldest* one, goes to end
        Interview oldest = interview("Jan01 0800", "O", LocalDateTime.of(2025, 1, 1, 8, 0));
        list.add(oldest);
        int last = list.getInterviews().size() - 1;
        assertEquals("Jan01 0800", list.getInterviews().get(last).getHeader().toString());

        // Add a *middle* one
        Interview middle = interview("Jan02 2000", "M", LocalDateTime.of(2025, 1, 2, 20, 0));
        list.add(middle);

        // verify full order: newest to oldest
        List<String> headers = list.getInterviews().stream()
                .map(iv -> iv.getHeader().toString())
                .collect(Collectors.toList());
        assertEquals(Arrays.asList("Jan03 0900", "Jan02 2000", "Jan02 1600", "Jan01 0800"), headers);
    }
}
