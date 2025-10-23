package presspal.contact.model.person;

import static presspal.contact.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import presspal.contact.commons.util.ToStringBuilder;
import presspal.contact.model.category.Category;
import presspal.contact.model.interview.Interview;

/**
 * Represents a Person in the contact book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Organisation organisation;
    private final Role role;
    private final Set<Category> categories = new HashSet<>();
    private final InterviewList interviews;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Organisation organisation, Role role,
                  Set<Category> categories, InterviewList interviews) {
        requireAllNonNull(name, organisation, role, categories, interviews);

        // enforce at least one compulsory contact field
        if (phone == null && email == null) {
            throw new IllegalArgumentException("At least one of phone or email must be provided.");
        }

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.organisation = organisation;
        this.role = role;
        this.categories.addAll(categories);
        this.interviews = interviews;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Organisation getOrganisation() {
        return organisation;
    }

    public Role getRole() {
        return role;
    }

    /**
     * Returns an immutable category set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Category> getCategories() {
        return Collections.unmodifiableSet(categories);
    }

    /**
     * Returns an InterviewList.
     */
    public InterviewList getInterviews() {
        return interviews;
    }

    /**
     * Returns an Optional containing the next upcoming interview if it exists.
     * If there are no upcoming interviews, returns an empty Optional.
     */
    public Optional<Interview> getNextUpcomingInterview() {
        return interviews.getNextUpcoming();
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Person)) {
            return false;
        }
        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && Objects.equals(phone, otherPerson.phone)
                && Objects.equals(email, otherPerson.email)
                && organisation.equals(otherPerson.organisation)
                && role.equals(otherPerson.role)
                && categories.equals(otherPerson.categories)
                && interviews.equals(otherPerson.interviews);
    }


    @Override
    public int hashCode() {
        return Objects.hash(name, phone, email, organisation, role, categories, interviews);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("organisation", organisation)
                .add("role", role)
                .add("categories", categories)
                .add("interviews", interviews)
                .toString();
    }

}
