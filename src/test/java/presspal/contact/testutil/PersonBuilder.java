package presspal.contact.testutil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import presspal.contact.model.category.Category;
import presspal.contact.model.interview.Interview;
import presspal.contact.model.person.Email;
import presspal.contact.model.person.InterviewList;
import presspal.contact.model.person.Name;
import presspal.contact.model.person.Organisation;
import presspal.contact.model.person.Person;
import presspal.contact.model.person.Phone;
import presspal.contact.model.person.Role;
import presspal.contact.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ORGANISATION = "NUS";
    public static final String DEFAULT_ROLE = "student";
    public static final InterviewList DEFAULT_INTERVIEWS = new InterviewList(null);

    private Name name;
    private Phone phone;
    private Email email;
    private Organisation organisation;
    private Role role;
    private Set<Category> categories;
    private InterviewList interviews;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        organisation = new Organisation(DEFAULT_ORGANISATION);
        role = new Role(DEFAULT_ROLE);
        categories = new HashSet<>();
        interviews = DEFAULT_INTERVIEWS;
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        organisation = personToCopy.getOrganisation();
        role = personToCopy.getRole();
        categories = new HashSet<>(personToCopy.getCategories());
        interviews = personToCopy.getInterviews();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code categories} into a {@code Set<Category>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withCategories(String ... categories) {
        this.categories = SampleDataUtil.getCategorySet(categories);
        return this;
    }

    /**
     * Allows test setup with specific Interview objects.
     */
    public PersonBuilder withInterviews(Interview... interviews) {
        this.interviews = new InterviewList(Arrays.asList(interviews));
        return this;
    }

    /**
     * Convenience overload for simple string calls.
     */
    public PersonBuilder withInterviews(String header, String location) {
        this.interviews = SampleDataUtil.getInterviewList(header, location);
        return this;
    }

    /**
     * Sets the {@code Organisation} of the {@code Person} that we are building.
     */
    public PersonBuilder withOrganisation(String organisation) {
        this.organisation = new Organisation(organisation);
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code Person} that we are building.
     */
    public PersonBuilder withRole(String role) {
        this.role = new Role(role);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, organisation, role, categories, interviews);
    }

}
