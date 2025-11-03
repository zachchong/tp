package presspal.contact.logic.commands;

import static java.util.Objects.requireNonNull;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import presspal.contact.commons.core.index.Index;
import presspal.contact.logic.Messages;
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
public class DeleteCategoryCommand extends EditCategoryCommand {

    public static final String COMMAND_WORD = "deleteCat";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Delete category(s) from a Person identified "
            + "by the index number used in the displayed person list. \n"
            + "Parameters: " + PREFIX_INDEX + "PERSON_INDEX "
            + PREFIX_CATEGORY + "CATEGORY...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_INDEX + "1 "
            + PREFIX_CATEGORY + "friends";

    public static final String MESSAGE_DELETECAT_SUCCESS = "Category(s) successfully deleted from %1$s:\n%2$s\n\n%3$s";
    public static final String MESSAGE_CAT_NOTFOUND_SUCCESS = "However, these category(s) were not found:\n%1$s";
    public static final String MESSAGE_CAT_NOT_FOUND = "Failed to delete category(s) from %1$s:\n%2$s";

    /**
     * @param index of the person in the filtered person list to edit
     * @param editCategoryDescriptor details to edit the person with
     */
    public DeleteCategoryCommand(Index index, EditCategoryDescriptor editCategoryDescriptor) {
        super(index, editCategoryDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        Index index = super.getIndex();
        EditCategoryDescriptor editCategoryDescriptor = super.getEditCategoryDescriptor();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDeleteCat = lastShownList.get(index.getZeroBased());

        Set<Category> categoryNotFound = categoriesToDelete(personToDeleteCat, editCategoryDescriptor);
        Set<Category> categoriesToDelete = getCategoriesPresent(personToDeleteCat, editCategoryDescriptor);
        if (categoriesToDelete.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_CAT_NOT_FOUND, personToDeleteCat.getName(),
                    editCategoryDescriptor.getCategoriesAsString()));
        }
        String resultMessage = "";
        if (!categoryNotFound.isEmpty()) {
            EditCategoryDescriptor duplicateCatDescriptor = new EditCategoryDescriptor();
            duplicateCatDescriptor.setCategories(categoryNotFound);
            resultMessage = String.format(MESSAGE_CAT_NOTFOUND_SUCCESS, duplicateCatDescriptor.getCategoriesAsString());
        }
        Person editedPerson = createNewPerson(personToDeleteCat, editCategoryDescriptor);

        model.setPerson(personToDeleteCat, editedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        editCategoryDescriptor.setCategories(categoriesToDelete);
        return new CommandResult(String.format(MESSAGE_DELETECAT_SUCCESS, editedPerson.getName(),
                editCategoryDescriptor.getCategoriesAsString(), resultMessage));
    }

    private Set<Category> categoriesToDelete(Person personToDeleteCat, EditCategoryDescriptor editCategoryDescriptor) {
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

    private Set<Category> getCategoriesPresent(Person personToAddCat, EditCategoryDescriptor editCategoryDescriptor) {
        Set<Category> updatedCategories = new HashSet<>(personToAddCat.getCategories());
        Set<Category> categoriesToAdd = editCategoryDescriptor.getCategories();
        Set<Category> presentCategories = new HashSet<>();
        for (Category category : categoriesToAdd) {
            if (updatedCategories.contains(category)) {
                presentCategories.add(category);
            }
        }
        return presentCategories;
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
        Set<Category> categoriesToDelete = editCategoryDescriptor.getCategories();

        if (!categoriesToDelete.isEmpty()) {
            for (Category category : categoriesToDelete) {
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
        return getIndex().equals(otherCommand.getIndex())
                && getEditCategoryDescriptor().equals(otherCommand.getEditCategoryDescriptor());
    }
}
