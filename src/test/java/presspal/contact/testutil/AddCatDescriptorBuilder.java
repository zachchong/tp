package presspal.contact.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import presspal.contact.logic.commands.AddCategoryCommand.AddCatDescriptor;
import presspal.contact.logic.commands.EditCommand;
import presspal.contact.model.category.Category;
import presspal.contact.model.person.Person;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class AddCatDescriptorBuilder {

    private AddCatDescriptor descriptor;

    public AddCatDescriptorBuilder() {
        descriptor = new AddCatDescriptor();
    }

    public AddCatDescriptorBuilder(AddCatDescriptor addCatDescriptor) {
        descriptor = new AddCatDescriptor(addCatDescriptor);
    }

    /**
     * Returns an {@code AddCatDescriptor} with fields containing {@code person}'s details
     */
    public AddCatDescriptorBuilder(Person person) {
        descriptor = new AddCatDescriptor();
        descriptor.setCategories(person.getCategories());
    }

    /**
     * Parses the {@code categories} into a {@code Set<Category>} and set it to the {@code AddCatDescriptor}
     * that we are building.
     */
    public AddCatDescriptorBuilder withCategories(String... categories) {
        Set<Category> categorySet = Stream.of(categories).map(Category::new).collect(Collectors.toSet());
        descriptor.setCategories(categorySet);
        return this;
    }

    public AddCatDescriptor build() {
        return descriptor;
    }
}
