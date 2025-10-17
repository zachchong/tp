package presspal.contact.logic.commands;

import static java.util.Objects.requireNonNull;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_INDEX;
import static presspal.contact.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import presspal.contact.commons.core.index.Index;
import presspal.contact.commons.util.CollectionUtil;
import presspal.contact.commons.util.ToStringBuilder;
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
public class AddCategoryCommand extends Command {

    public static final String COMMAND_WORD = "addCat";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds the categories of the person identified "
            + "by the index number used in the displayed person list. \n"
            + "Parameters: " + PREFIX_INDEX + "INDEX "
            + "[" + PREFIX_CATEGORY + "CATEGORY]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_CATEGORY + "friends";

    public static final String MESSAGE_ADDCAT_SUCCESS =
            "Added Successfully. This is the updated Category of %1$s:\n%2$s";
    public static final String MESSAGE_NOT_ADDED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CAT = "This person already has this category";

    private final Index index;
    private final AddCatDescriptor addCatDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param addCatDescriptor details to edit the person with
     */
    public AddCategoryCommand(Index index, AddCatDescriptor addCatDescriptor) {
        requireNonNull(index);
        requireNonNull(addCatDescriptor);

        this.index = index;
        this.addCatDescriptor = addCatDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToAddCat = lastShownList.get(index.getZeroBased());
        Person editedPerson = createNewPerson(personToAddCat, addCatDescriptor);

        if (personToAddCat.getCategories().equals(editedPerson.getCategories())) {
            throw new CommandException(MESSAGE_DUPLICATE_CAT);
        }

        model.setPerson(personToAddCat, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_ADDCAT_SUCCESS,
                editedPerson.getName(), editedPerson.getCategories()));
    }

    public static Person createNewPerson(Person personToAddCat, AddCatDescriptor addCatDescriptor) {
        assert personToAddCat != null;
        Set<Category> updatedCategories = new HashSet<>(personToAddCat.getCategories());

        Name name = personToAddCat.getName();
        Phone phone = personToAddCat.getPhone();
        Email email = personToAddCat.getEmail();
        Organisation organisation = personToAddCat.getOrganisation();
        Role role = personToAddCat.getRole();
        Set<Category> newCategory = addCatDescriptor.getCategories();
        if (!newCategory.isEmpty()) {
            updatedCategories.addAll(newCategory);
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
        return index.equals(otherAddCategoryCommand.index)
                && addCatDescriptor.equals(otherAddCategoryCommand.addCatDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("addCatDescriptor", addCatDescriptor)
                .toString();
    }

    /**
     * Stores the categories to edit the person with.
     */
    public static class AddCatDescriptor {
        private Set<Category> categories;

        public AddCatDescriptor() {
        }

        public AddCatDescriptor(AddCatDescriptor addCatDescriptor) {
            this.categories = addCatDescriptor.categories;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isCategoryEdited() {
            return CollectionUtil.isAnyNonNull(categories);
        }
        /**
         * Sets {@code categories} to this object's {@code categories}.
         * A defensive copy of {@code categories} is used internally.
         */
        public void setCategories(Set<Category> categories) {
            this.categories = (categories != null) ? new HashSet<>(categories) : null;
        }

        /**
         * Returns an unmodifiable category set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code categories} is null.
         */
        public Set<Category> getCategories() {
            return Collections.unmodifiableSet(categories);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof AddCatDescriptor)) {
                return false;
            }

            AddCatDescriptor otherDescriptor = (AddCatDescriptor) other;

            return getCategories().equals(otherDescriptor.getCategories());
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("categories", categories)
                    .toString();
        }
    }
}
