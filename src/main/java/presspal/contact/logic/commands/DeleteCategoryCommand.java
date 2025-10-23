package presspal.contact.logic.commands;

import static java.util.Objects.requireNonNull;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import presspal.contact.commons.core.index.Index;
import presspal.contact.commons.util.ToStringBuilder;
import presspal.contact.logic.Messages;
import presspal.contact.logic.commands.AddCategoryCommand.EditCategoryDescriptor;
import presspal.contact.logic.commands.exceptions.CommandException;
import presspal.contact.model.Model;
import presspal.contact.model.category.Category;
import presspal.contact.model.person.Email;
import presspal.contact.model.person.InterviewList;
import presspal.contact.model.person.Name;
import presspal.contact.model.person.Organisation;
import presspal.contact.model.person.Person;
import presspal.contact.model.person.Phone;
import presspal.contact.model.person.Role;

/**
 * Delete the categories to an existing person in the contact book.
 */
public class DeleteCategoryCommand extends Command {

    public static final String COMMAND_WORD = "deleteCat";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Delete category(s) from a Person identified "
            + "by the index number used in the displayed person list. \n"
            + "Parameters: " + PREFIX_INDEX + "INDEX "
            + "[" + PREFIX_CATEGORY + "CATEGORY]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_CATEGORY + "friends";

    public static final String MESSAGE_DELETECAT_SUCCESS = "The Category %1$s is successfully deleted from %2$s";
    public static final String MESSAGE_CAT_NOT_FOUND = "This person does not have this category:\n%1$s";

    private final Index index;
    private final EditCategoryDescriptor editCategoryDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editCategoryDescriptor details to edit the person with
     */
    public DeleteCategoryCommand(Index index, EditCategoryDescriptor editCategoryDescriptor) {
        requireNonNull(editCategoryDescriptor);
        requireNonNull(index);
        this.index = index;
        this.editCategoryDescriptor = editCategoryDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDeleteCat = lastShownList.get(index.getZeroBased());

        Set<Category> categoryNotFound = isCategoriesPresent(personToDeleteCat, editCategoryDescriptor);
        if (categoryNotFound.size() > 0) {
            throw new CommandException(String.format(MESSAGE_CAT_NOT_FOUND, categoryNotFound));
        }

        Person editedPerson = createNewPerson(personToDeleteCat, editCategoryDescriptor);

        model.setPerson(personToDeleteCat, editedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_DELETECAT_SUCCESS,
                editCategoryDescriptor.getCategoriesAsString(), editedPerson.getName()));
    }

    private Set<Category> isCategoriesPresent(Person personToDeleteCat, EditCategoryDescriptor editCategoryDescriptor) {
        Set<Category> updatedCategories = new HashSet<>(personToDeleteCat.getCategories());
        Set<Category> categoriesToDelete = editCategoryDescriptor.getCategories();
        Set<Category> notFoundCategories = new HashSet<>();

        for (Category category : categoriesToDelete) {
            if (!updatedCategories.contains(category)) {
                notFoundCategories.add(category);
            }
        }

        return notFoundCategories;
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToAddCat}
     * edited with {@code editCategoryDescriptor}.
     */
    public static Person createNewPerson(Person personToDeleteCat, EditCategoryDescriptor editCategoryDescriptor) {
        assert personToDeleteCat != null;
        Set<Category> updatedCategories = new HashSet<>(personToDeleteCat.getCategories());

        Name name = personToDeleteCat.getName();
        Phone phone = personToDeleteCat.getPhone();
        Email email = personToDeleteCat.getEmail();
        Organisation organisation = personToDeleteCat.getOrganisation();
        Role role = personToDeleteCat.getRole();
        Set<Category> newCategory = editCategoryDescriptor.getCategories();

        if (!newCategory.isEmpty()) {
            for (Category category : newCategory) {
                updatedCategories.remove(category);
            }
        }
        InterviewList interviews = personToDeleteCat.getInterviews();
        return new Person(name, phone, email, organisation, role, updatedCategories, interviews);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCategoryCommand)) {
            return false;
        }

        DeleteCategoryCommand otherCommand = (DeleteCategoryCommand) other;
        return index.equals(otherCommand.index)
                && editCategoryDescriptor.equals(otherCommand.editCategoryDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editCategoryDescriptor", editCategoryDescriptor)
                .toString();
    }
}
