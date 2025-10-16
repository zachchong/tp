package presspal.contact.testutil;

import java.time.LocalDateTime;

import presspal.contact.model.interview.Header;
import presspal.contact.model.interview.Interview;
import presspal.contact.model.interview.Location;

/**
 * A utility class to help with building Interview objects.
 */
public class InterviewBuilder {
    private String header = "Interview";
    private String location = "HQ";
    private LocalDateTime date = LocalDateTime.parse("2050-12-12T12:00");

    /**
     * Sets the {@code Header} of the {@code Interview} that we are building.
     */
    public InterviewBuilder withHeader(String header) {
        this.header = header;
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code Interview} that we are building.
     */
    public InterviewBuilder withLocation(String location) {
        this.location = location;
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Interview} that we are building.
     */
    public InterviewBuilder withDate(LocalDateTime date) {
        this.date = date;
        return this;
    }

    public Interview build() {
        return new Interview(new Header(header), new Location(location), date);
    }
}
