package presspal.contact.model.interview;

/**
 * Represents the title and/or description of interview.
 */
public class Header {
    /** The textual representation of the interview header. */
    private final String header;

    /** Constructs a Header object */
    public Header(String header) {
        this.header = header;
    }

    /** Returns the header as a String */
    public String getHeader() {
        return this.header;
    }
}
