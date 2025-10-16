package presspal.contact.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import presspal.contact.commons.exceptions.IllegalValueException;
import presspal.contact.model.interview.Header;
import presspal.contact.model.interview.Interview;
import presspal.contact.model.interview.Location;

/**
 * Jackson-friendly version of {@link Interview}.
 */
class JsonAdaptedInterview {

    private static final String MISSING_FIELD_MESSAGE_FORMAT = "Interview's %s field is missing!";
    private static final String INVALID_DATETIME_MESSAGE = "Interview date/time must be in ISO_LOCAL_DATE_TIME format.";

    private final String header;
    private final String dateTime; // stored as ISO string, e.g. "2025-10-20T14:00"
    private final String location;

    /**
     * Constructs a {@code JsonAdaptedInterview} with the given fields for Jackson.
     */
    @JsonCreator
    public JsonAdaptedInterview(@JsonProperty("header") String header,
                                @JsonProperty("dateTime") String dateTime,
                                @JsonProperty("location") String location) {
        this.header = header;
        this.dateTime = dateTime;
        this.location = location;
    }

    /**
     * Converts a given {@code Interview} into this class for Jackson use.
     */
    public JsonAdaptedInterview(Interview source) {
        this.header = source.getHeader().toString();
        this.dateTime = source.getDateTime().toString();
        this.location = source.getLocation().toString();
    }

    public String getHeader() {
        return header;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getLocation() {
        return location;
    }

    /**
     * Converts this Jackson-friendly adapted interview object into the model's {@code Interview} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted interview.
     */
    public Interview toModelType() throws IllegalValueException {
        if (header == null || header.isBlank()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "header"));
        }
        if (dateTime == null || dateTime.isBlank()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "dateTime"));
        }
        if (location == null || location.isBlank()) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "location"));
        }

        LocalDateTime parsedDateTime;
        try {
            parsedDateTime = LocalDateTime.parse(dateTime);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException(INVALID_DATETIME_MESSAGE);
        }

        return new Interview(new Header(header), new Location(location), parsedDateTime);
    }
}
