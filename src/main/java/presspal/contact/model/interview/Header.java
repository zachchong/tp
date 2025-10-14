package presspal.contact.model.interview;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents the title and/or description of interview.
 */
public class Header {
    /** The textual representation of the interview header. */
    private final String value;

    /** Constructs a Header object */
    public Header(String header) {
        requireNonNull(header);
        value = header;
    }

    /**
     * Returns true if a given string is a valid header.
     * Adjust this logic according to your actual rules.
     */
    public static boolean isValidHeader(String test) {
        return !test.isBlank(); // simple example: header cannot be blank
    }

    /**
     * Returns true if both Header objects are the same string.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Header h)) {
            return false;
        }

        return Objects.equals(value, h.value);
    }

    /**
     * Returns the hash code for this Header.
     */
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    /**
     * Returns the header string value.
     */
    @Override
    public String toString() {
        return value;
    }

}
