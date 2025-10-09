package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InterviewListTest {
    private InterviewList emptyList;
    private InterviewList populatedList;
    private List<String> sampleInterviews;

    @BeforeEach
    public void setUp() {
        sampleInterviews = new ArrayList<>(Arrays.asList("Interview at 2359", "Interview at 1600"));
        emptyList = new InterviewList(null);
        populatedList = new InterviewList(sampleInterviews);
    }

    @Test
    public void constructor_nullList_initializesEmptyList() {
        InterviewList list = new InterviewList(null);
        assertTrue(list.getInterviewList().isEmpty());
    }

    @Test
    public void getInterviewList_returnsCorrectList() {
        assertEquals(sampleInterviews, populatedList.getInterviewList());
    }

    @Test
    public void add_addsInterviewSuccessfully() {
        emptyList.add("Interview at 0000");
        assertTrue(emptyList.contains("Interview at 0000"));
    }

    @Test
    public void remove_removesInterviewSuccessfully() {
        populatedList.remove("Interview at 2359");
        assertFalse(populatedList.contains("Interview at 2359"));
    }

    @Test
    public void contains_returnsTrueIfInterviewExists() {
        assertTrue(populatedList.contains("Interview at 1600"));
        assertFalse(populatedList.contains("Interview at 1200"));
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
        InterviewList differentList = new InterviewList(Arrays.asList("Interview at 1800"));
        assertFalse(populatedList.equals(differentList));
    }

    @Test
    public void equals_sameContents_returnsTrue() {
        InterviewList sameList = new InterviewList(new ArrayList<>(sampleInterviews));
        assertTrue(populatedList.equals(sameList));
    }

    @Test
    public void hashCode_sameContents_sameHashCode() {
        InterviewList sameList = new InterviewList(new ArrayList<>(sampleInterviews));
        assertEquals(populatedList.hashCode(), sameList.hashCode());
    }

    @Test
    public void toString_returnsCorrectFormat() {
        String expected = sampleInterviews.toString();
        assertEquals(expected, populatedList.toString());
    }

    @Test
    public void getUpcomingInterviews_returnsEmptyListForNow() {
        assertTrue(populatedList.getUpcomingInterviews().isEmpty());
    }
}
