package presspal.contact.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import presspal.contact.model.ContactBook;
import presspal.contact.model.ReadOnlyContactBook;
import presspal.contact.model.category.Category;
import presspal.contact.model.person.Email;
import presspal.contact.model.person.Name;
import presspal.contact.model.person.Organisation;
import presspal.contact.model.person.Person;
import presspal.contact.model.person.Phone;
import presspal.contact.model.person.Role;

/**
 * Contains utility methods for populating {@code ContactBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Organisation("NUS"), new Role("Student"),
                getCategorySet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Organisation("NUS"), new Role("Student"),
                getCategorySet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Organisation("SMU"), new Role("Student"),
                getCategorySet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Organisation("CAPT"), new Role("Student"),
                getCategorySet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Organisation("NUS"), new Role("Student"),
                getCategorySet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                new Organisation("NTU"), new Role("Student"),
                getCategorySet("colleagues"))
        };
    }

    public static ReadOnlyContactBook getSampleContactBook() {
        ContactBook sampleAb = new ContactBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a category set containing the list of strings given.
     */
    public static Set<Category> getCategorySet(String... strings) {
        return Arrays.stream(strings)
                .map(Category::new)
                .collect(Collectors.toSet());
    }

}
