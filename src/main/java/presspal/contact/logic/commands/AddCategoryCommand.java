package presspal.contact.logic.commands;

import static java.util.Objects.requireNonNull;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_INDEX;
import static presspal.contact.model.Model.PREDICATE_SHOW_ALL_PERSONS;

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
 * Add the categories to an existing person in the contact book.
 */
public class AddCategoryCommand extends EditCategoryCommand {

    public static final String COMMAND_WORD = "addCat";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds the categories of the person identified "
            + "by the index number used in the displayed person list. \n"
            + "Parameters: " + PREFIX_INDEX + "PERSON_INDEX "
            + PREFIX_CATEGORY + "CATEGORY...\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_INDEX + "1 "
            + PREFIX_CATEGORY + "friends";

    public static final String MESSAGE_ADDCAT_SUCCESS = "Category(s) successfully added to %1$s:\n%2$s\n\n%3$s";
    public static final String MESSAGE_ADDCAT_DUP_SUCCESS = "However, these category(s) were already added:\n%1$s";
    public static final String MESSAGE_DUPLICATE_CAT = "This person already has this category";

    /**
     * @param index of the person in the filtered person list to edit
     * @param editCategoryDescriptor details to edit the person with
     */
    public AddCategoryCommand(Index index, EditCategoryDescriptor editCategoryDescriptor) {
        super(index, editCategoryDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        Index index = getIndex();
        EditCategoryDescriptor editCategoryDescriptor = getEditCategoryDescriptor();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX + " "
                    + model.getValidPersonIndexRange());
        }

        Person personToAddCat = lastShownList.get(index.getZeroBased());
        Person editedPerson = createNewPerson(personToAddCat, editCategoryDescriptor);

        Set<Category> categoriesToAdd = getCategoriesToAdd(personToAddCat, editCategoryDescriptor);
        Set<Category> categoriesNotAdded = getCategoriesAlreadyPresent(personToAddCat, editCategoryDescriptor);
        if (categoriesToAdd.isEmpty()) {
            throw new CommandException(MESSAGE_DUPLICATE_CAT);
        }

        String duplicateCatMessage = "";
        if (!categoriesNotAdded.isEmpty()) {
            EditCategoryDescriptor duplicateCatDescriptor = new EditCategoryDescriptor();
            duplicateCatDescriptor.setCategories(categoriesNotAdded);
            duplicateCatMessage = String.format(MESSAGE_ADDCAT_DUP_SUCCESS,
                    duplicateCatDescriptor.getCategoriesAsString());
        }

        model.setPerson(personToAddCat, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        editCategoryDescriptor.setCategories(categoriesToAdd);
        return new CommandResult(String.format(MESSAGE_ADDCAT_SUCCESS, editedPerson.getName(),
                editCategoryDescriptor.getCategoriesAsString(), duplicateCatMessage));
    }

    private Set<Category> getCategoriesToAdd(Person personToAddCat, EditCategoryDescriptor editCategoryDescriptor) {
        Set<Category> updatedCategories = new HashSet<>(personToAddCat.getCategories());
        Set<Category> categoriesToAdd = editCategoryDescriptor.getCategories();
        Set<Category> notFoundCategories = new HashSet<>();

        for (Category category : categoriesToAdd) {
            if (!updatedCategories.contains(category)) {
                notFoundCategories.add(category);
            }
        }

        return notFoundCategories;
    }

    private Set<Category> getCategoriesAlreadyPresent(Person personToAddCat,
                                                      EditCategoryDescriptor editCategoryDescriptor) {
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
    public static Person createNewPerson(Person personToAddCat, EditCategoryDescriptor editCategoryDescriptor) {
        assert personToAddCat != null;
        Set<Category> updatedCategories = new HashSet<>(personToAddCat.getCategories());

        Name name = personToAddCat.getName();
        Phone phone = personToAddCat.getPhone();
        Email email = personToAddCat.getEmail();
        Organisation organisation = personToAddCat.getOrganisation();
        Role role = personToAddCat.getRole();
        Set<Category> catToAdd = editCategoryDescriptor.getCategories();
        if (!catToAdd.isEmpty()) {
            updatedCategories.addAll(catToAdd);
        }
        InterviewList interviews = personToAddCat.getInterviews();
        return new Person(name, phone, email, organisation, role, updatedCategories, interviews);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCategoryCommand)) {
            return false;
        }

        AddCategoryCommand otherAddCategoryCommand = (AddCategoryCommand) other;
        return getIndex().equals(otherAddCategoryCommand.getIndex())
                && getEditCategoryDescriptor().equals(otherAddCategoryCommand.getEditCategoryDescriptor());
    }
}
