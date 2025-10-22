package presspal.contact.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import presspal.contact.logic.commands.AddCategoryCommand.EditCategoryDescriptor;
import presspal.contact.model.category.Category;
import presspal.contact.model.person.Person;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditCategoryDescriptorBuilder {

    private EditCategoryDescriptor descriptor;

    public EditCategoryDescriptorBuilder() {
        descriptor = new EditCategoryDescriptor();
    }

    public EditCategoryDescriptorBuilder(EditCategoryDescriptor editCategoryDescriptor) {
        descriptor = new EditCategoryDescriptor(editCategoryDescriptor);
    }

    /**
     * Returns an {@code EditCategoryDescriptor} with fields containing {@code person}'s details
     */
    public EditCategoryDescriptorBuilder(Person person) {
        descriptor = new EditCategoryDescriptor();
        descriptor.setCategories(person.getCategories());
    }

    /**
     * Parses the {@code categories} into a {@code Set<Category>} and set it to the {@code EditCategoryDescriptor}
     * that we are building.
     */
    public EditCategoryDescriptorBuilder withCategories(String... categories) {
        Set<Category> categorySet = Stream.of(categories).map(Category::new).collect(Collectors.toSet());
        descriptor.setCategories(categorySet);
        return this;
    }

    public EditCategoryDescriptor build() {
        return descriptor;
    }
}
