package presspal.contact.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static presspal.contact.logic.commands.CommandTestUtil.ADD_CATEGORY_DESC_AMY;
import static presspal.contact.logic.commands.CommandTestUtil.ADD_CATEGORY_DESC_BOB;
import static presspal.contact.logic.commands.CommandTestUtil.VALID_CATEGORY_FRIEND;
import static presspal.contact.logic.commands.CommandTestUtil.VALID_CATEGORY_HUSBAND;
import static presspal.contact.logic.commands.CommandTestUtil.assertCommandFailure;
import static presspal.contact.logic.commands.CommandTestUtil.assertCommandSuccess;
import static presspal.contact.logic.commands.CommandTestUtil.showPersonAtIndex;
import static presspal.contact.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static presspal.contact.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static presspal.contact.testutil.TypicalPersons.getTypicalContactBook;

import org.junit.jupiter.api.Test;

import presspal.contact.commons.core.index.Index;
import presspal.contact.logic.Messages;
import presspal.contact.logic.commands.AddCategoryCommand.AddCatDescriptor;
import presspal.contact.model.ContactBook;
import presspal.contact.model.Model;
import presspal.contact.model.ModelManager;
import presspal.contact.model.UserPrefs;
import presspal.contact.model.person.Person;
import presspal.contact.testutil.AddCatDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddCategoryCommand.
 */
public class AddCategoryCommandTest {

    private final Model model = new ModelManager(getTypicalContactBook(), new UserPrefs());

    @Test
    public void execute_unfilteredList_success() {
        // person currently at index 0 in the list
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        AddCatDescriptor descriptor = new AddCatDescriptorBuilder().withCategories(VALID_CATEGORY_HUSBAND).build();
        AddCategoryCommand addCategoryCommand = new AddCategoryCommand(INDEX_FIRST_PERSON, descriptor);

        // build a target person with new category added
        Person editedPerson = AddCategoryCommand.createNewPerson(personToEdit, descriptor);

        String expectedMessage = String.format(AddCategoryCommand.MESSAGE_ADDCAT_SUCCESS,
                descriptor.getCategoriesAsString(), editedPerson.getName());

        Model expectedModel = new ModelManager(model.getContactBook(), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(addCategoryCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        AddCatDescriptor descriptor = new AddCatDescriptorBuilder().withCategories(VALID_CATEGORY_HUSBAND).build();
        AddCategoryCommand addCategoryCommand = new AddCategoryCommand(INDEX_FIRST_PERSON, descriptor);

        // build a target person with new category added
        Person editedPerson = AddCategoryCommand.createNewPerson(personInFilteredList, descriptor);

        String expectedMessage = String.format(AddCategoryCommand.MESSAGE_ADDCAT_SUCCESS,
                descriptor.getCategoriesAsString(), editedPerson.getName());

        Model expectedModel = new ModelManager(new ContactBook(model.getContactBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(addCategoryCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateCategory_failure() {
        // person currently at index 0 in the list
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        AddCatDescriptor descriptor = new AddCatDescriptorBuilder()
                .withCategories(VALID_CATEGORY_FRIEND).build();
        AddCategoryCommand addCategoryCommand = new AddCategoryCommand(INDEX_FIRST_PERSON, descriptor);

        // First, add the category successfully
        Person editedPerson = AddCategoryCommand.createNewPerson(personToEdit, descriptor);
        model.setPerson(personToEdit, editedPerson);

        // Attempt to add the same category again
        assertCommandFailure(addCategoryCommand, model, AddCategoryCommand.MESSAGE_DUPLICATE_CAT);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddCatDescriptor descriptor = new AddCatDescriptorBuilder().withCategories(VALID_CATEGORY_FRIEND).build();
        AddCategoryCommand addCategoryCommand = new AddCategoryCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(addCategoryCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of contact book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of contact book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getContactBook().getPersonList().size());

        AddCategoryCommand addCategoryCommand = new AddCategoryCommand(outOfBoundIndex,
                new AddCatDescriptorBuilder().withCategories(VALID_CATEGORY_FRIEND).build());

        assertCommandFailure(addCategoryCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final AddCategoryCommand standardCommand =
                new AddCategoryCommand(INDEX_FIRST_PERSON, ADD_CATEGORY_DESC_AMY);

        // same values -> returns true
        AddCatDescriptor copyDescriptor = new AddCatDescriptor(ADD_CATEGORY_DESC_AMY);
        AddCategoryCommand commandWithSameValues =
                new AddCategoryCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddCategoryCommand(INDEX_SECOND_PERSON, ADD_CATEGORY_DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new AddCategoryCommand(INDEX_FIRST_PERSON, ADD_CATEGORY_DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        AddCatDescriptor descriptor = new AddCatDescriptor();
        AddCategoryCommand command = new AddCategoryCommand(index, descriptor);
        String expectedString = AddCategoryCommand.class.getCanonicalName()
                + "{index=" + index + ", addCatDescriptor=" + descriptor + "}";

        assertEquals(expectedString, command.toString());
    }
}
