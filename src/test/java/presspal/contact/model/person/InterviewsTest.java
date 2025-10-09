package presspal.contact.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InterviewsTest {
    private Interviews emptyList;
    private Interviews populatedList;
    private List<String> sampleInterviews;

    @BeforeEach
    public void setUp() {
        sampleInterviews = new ArrayList<>(Arrays.asList("Interview at 2359", "Interview at 1600"));
        emptyList = new Interviews(null);
        populatedList = new Interviews(sampleInterviews);
    }

    @Test
    public void constructor_nullList_initializesEmptyList() {
        Interviews list = new Interviews(null);
        assertTrue(list.getInterviews().isEmpty());
    }

    @Test
    public void getInterviewList_returnsCorrectList() {
        assertEquals(sampleInterviews, populatedList.getInterviews());
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
        Interviews differentList = new Interviews(Arrays.asList("Interview at 1800"));
        assertFalse(populatedList.equals(differentList));
    }

    @Test
    public void equals_sameContents_returnsTrue() {
        Interviews sameList = new Interviews(new ArrayList<>(sampleInterviews));
        assertTrue(populatedList.equals(sameList));
    }

    @Test
    public void hashCode_sameContents_sameHashCode() {
        Interviews sameList = new Interviews(new ArrayList<>(sampleInterviews));
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
