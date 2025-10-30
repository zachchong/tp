package presspal.contact.model.person;

import static java.util.Objects.requireNonNull;
import static presspal.contact.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's role in the contact book.
 * Guarantees: immutable; is valid as declared in {@link #isValidRole(String)}
 */
public class Role {

    public static final String MESSAGE_CONSTRAINTS = "Role must be 1 to 50 characters, using only letters and digits"
            + ", with single spaces between words (no leading/trailing or multiple spaces).";

    /*
     * The first character of the role must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     * [A-Za-z0-9] allowed chars, {1,50} length
     */
    public static final String VALIDATION_REGEX = "^(?=.{1,50}$)[A-Za-z0-9]+(?: [A-Za-z0-9]+)*$";

    public final String value;

    /**
     * Constructs a {@code Role}.
     *
     * @param role A valid role.
     */
    public Role(String role) {
        requireNonNull(role);
        checkArgument(isValidRole(role), MESSAGE_CONSTRAINTS);
        value = role;
    }

    /**
     * Returns true if a given string is a valid role.
     */
    public static boolean isValidRole(String test) {
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
        if (!(other instanceof Role)) {
            return false;
        }

        Role otherRole = (Role) other;
        return value.equals(otherRole.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
