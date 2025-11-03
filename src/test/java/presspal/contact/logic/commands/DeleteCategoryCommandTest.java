package presspal.contact.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static presspal.contact.logic.commands.CommandTestUtil.ADD_CATEGORY_DESC_AMY;
import static presspal.contact.logic.commands.CommandTestUtil.ADD_CATEGORY_DESC_BOB;
import static presspal.contact.logic.commands.CommandTestUtil.VALID_CATEGORY_FRIEND;
import static presspal.contact.logic.commands.CommandTestUtil.assertCommandFailure;
import static presspal.contact.logic.commands.CommandTestUtil.assertCommandSuccess;
import static presspal.contact.logic.commands.CommandTestUtil.showPersonAtIndex;
import static presspal.contact.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static presspal.contact.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static presspal.contact.testutil.TypicalPersons.getTypicalContactBook;

import org.junit.jupiter.api.Test;

import presspal.contact.commons.core.index.Index;
import presspal.contact.logic.Messages;
import presspal.contact.logic.commands.EditCategoryCommand.EditCategoryDescriptor;
import presspal.contact.model.ContactBook;
import presspal.contact.model.Model;
import presspal.contact.model.ModelManager;
import presspal.contact.model.UserPrefs;
import presspal.contact.model.person.Person;
import presspal.contact.testutil.EditCategoryDescriptorBuilder;


public class DeleteCategoryCommandTest {

    private final Model model = new ModelManager(getTypicalContactBook(), new UserPrefs());

    @Test
    public void execute_unfilteredList_success() {
        // person currently at index 0 in the list
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        EditCategoryDescriptor descriptor = new EditCategoryDescriptorBuilder()
                .withCategories("friends").build();
        DeleteCategoryCommand deleteCategoryCommand = new DeleteCategoryCommand(INDEX_FIRST_PERSON, descriptor);

        // build a target person with new category added
        Person editedPerson = DeleteCategoryCommand.createNewPerson(personToEdit, descriptor);

        String expectedMessage = String.format(DeleteCategoryCommand.MESSAGE_DELETECAT_SUCCESS,
                editedPerson.getName(), descriptor.getCategoriesAsString());

        Model expectedModel = new ModelManager(model.getContactBook(), new UserPrefs());
        expectedModel.setPerson(personToEdit, editedPerson);

        assertCommandSuccess(deleteCategoryCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        EditCategoryDescriptor descriptor = new EditCategoryDescriptorBuilder()
                .withCategories("friends").build();
        DeleteCategoryCommand deleteCategoryCommand = new DeleteCategoryCommand(INDEX_FIRST_PERSON, descriptor);

        // build a target person with new category added
        Person editedPerson = DeleteCategoryCommand.createNewPerson(personInFilteredList, descriptor);

        String expectedMessage = String.format(DeleteCategoryCommand.MESSAGE_DELETECAT_SUCCESS,
                editedPerson.getName(), descriptor.getCategoriesAsString());

        Model expectedModel = new ModelManager(new ContactBook(model.getContactBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(deleteCategoryCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditCategoryDescriptor descriptor = new EditCategoryDescriptorBuilder()
                .withCategories(VALID_CATEGORY_FRIEND).build();
        DeleteCategoryCommand deleteCategoryCommand = new DeleteCategoryCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(deleteCategoryCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX
                + " " + model.getValidPersonIndexRange());
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

        DeleteCategoryCommand deleteCategoryCommand = new DeleteCategoryCommand(outOfBoundIndex,
                new EditCategoryDescriptorBuilder().withCategories(VALID_CATEGORY_FRIEND).build());

        assertCommandFailure(deleteCategoryCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX
                + " " + model.getValidPersonIndexRange());
    }

    @Test
    public void execute_categoryNotFound_failure() {
        Person personToEdit = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        EditCategoryDescriptor descriptor = new EditCategoryDescriptorBuilder()
                .withCategories("notPresentCategory").build();
        DeleteCategoryCommand deleteCategoryCommand = new DeleteCategoryCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(DeleteCategoryCommand.MESSAGE_CAT_NOT_FOUND,
                personToEdit.getName(), descriptor.getCategoriesAsString());

        assertCommandFailure(deleteCategoryCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        final DeleteCategoryCommand standardCommand =
                new DeleteCategoryCommand(INDEX_FIRST_PERSON, ADD_CATEGORY_DESC_AMY);

        // same values -> returns true
        EditCategoryDescriptor copyDescriptor = new EditCategoryDescriptor(ADD_CATEGORY_DESC_AMY);
        DeleteCategoryCommand commandWithSameValues =
                new DeleteCategoryCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertEquals(standardCommand, commandWithSameValues);

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new DeleteCategoryCommand(INDEX_SECOND_PERSON, ADD_CATEGORY_DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new DeleteCategoryCommand(INDEX_FIRST_PERSON, ADD_CATEGORY_DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditCategoryDescriptor descriptor = new EditCategoryDescriptor();
        DeleteCategoryCommand command = new DeleteCategoryCommand(index, descriptor);
        String expectedString = DeleteCategoryCommand.class.getCanonicalName()
                + "{index=" + index + ", editCategoryDescriptor=" + descriptor + "}";

        assertEquals(expectedString, command.toString());
    }
}
