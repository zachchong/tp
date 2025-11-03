package presspal.contact.model.person;

import static java.util.Objects.requireNonNull;
import static presspal.contact.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's organisation in the contact book.
 * Guarantees: immutable; is valid as declared in {@link #isValidOrganisation(String)}
 */
public class Organisation {

    public static final String MESSAGE_CONSTRAINTS =
            "Organisation must be 1 to 50 characters, using only letters and digits, "
            + "with single spaces between words (no multiple spaces).";

    /*
     * The first character of the role must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * [A-Za-z0-9] allowed chars, {1,50} length
     */
    public static final String VALIDATION_REGEX = "^(?=.{1,50}$)[A-Za-z0-9]+(?: [A-Za-z0-9]+)*$";

    public final String value;

    /**
     * Constructs an {@code Organisation}.
     *
     * @param organisation A valid organisation.
     */
    public Organisation(String organisation) {
        requireNonNull(organisation);
        checkArgument(isValidOrganisation(organisation), MESSAGE_CONSTRAINTS);
        value = organisation;
    }

    /**
     * Returns true if a given string is a valid organisation.
     */
    public static boolean isValidOrganisation(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Organisation)) {
            return false;
        }

        Organisation otherOrganisation = (Organisation) other;
        return value.equals(otherOrganisation.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
