package presspal.contact.model.person;

import static java.util.Objects.requireNonNull;
import static presspal.contact.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import presspal.contact.commons.util.ToStringBuilder;
import presspal.contact.model.category.Category;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Category> categories = new HashSet<>();
    private final Interviews interviews = new Interviews(null);

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Category> categories) {
        requireAllNonNull(name, phone, email, address, categories);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.categories.addAll(categories);
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

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Category> getCategories() {
        return Collections.unmodifiableSet(categories);
    }

    public Interviews getInterviews() {
        return interviews;
    }

    /**
     * Removes an interview from the address book.
     * The interview must exist in the address book.
     */
    public void removeInterview(String interview) {
        interviews.remove(interview);
    }

    /**
     * Adds an interview to the address book.
     * The interview must not already exist in the address book.
     */
    public void addInterview(String interview) {
        interviews.add(interview);
    }

    /**
     * Returns true if an interview with the same identity exists in the address book.
     */
    public boolean hasInterview(String interview) {
        requireNonNull(interview);
        return interviews.contains(interview);
    }

    /**
     * Returns the list of interviews in the address book.
     */
    public List<String> getInterviewList() {
        return interviews.getInterviews();
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

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && categories.equals(otherPerson.categories);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, categories);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("categories", categories)
                .toString();
    }

}
