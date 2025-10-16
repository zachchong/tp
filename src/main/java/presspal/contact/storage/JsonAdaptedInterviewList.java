package presspal.contact.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import presspal.contact.commons.exceptions.IllegalValueException;
import presspal.contact.model.interview.Interview;
import presspal.contact.model.person.InterviewList;

/**
 * Jackson-friendly version of a list of {@link Interview} objects.
 */
public class JsonAdaptedInterviewList {

    private final List<JsonAdaptedInterview> interviews = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedInterviewList} for Jackson.
     */
    @JsonCreator
    public JsonAdaptedInterviewList(@JsonProperty("interviews") List<JsonAdaptedInterview> interviews) {
        if (interviews != null) {
            this.interviews.addAll(interviews);
        }
    }

    /**
     * Converts a model {@code InterviewList} into this Jackson-friendly adapted list.
     */
    public JsonAdaptedInterviewList(InterviewList source) {
        if (source != null) {
            for (Interview interview : source.getInterviews()) {
                this.interviews.add(new JsonAdaptedInterview(interview));
            }
        }
    }

    /**
     * Returns an unmodifiable copy of the adapted interviews list for serialization.
     */
    public List<JsonAdaptedInterview> getInterviews() {
        return List.copyOf(interviews);
    }

    /**
     * Converts this Jackson-friendly adapted interview list into the model's {@code InterviewList}.
     *
     * @throws IllegalValueException if any adapted interview contains invalid data.
     */
    public InterviewList toModelType() throws IllegalValueException {
        InterviewList modelList = new InterviewList(null);
        for (JsonAdaptedInterview adapted : interviews) {
            Interview modelInterview = adapted.toModelType();
            modelList.add(modelInterview);
        }
        return modelList;
    }
}
