package presspal.contact.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static presspal.contact.logic.commands.CommandTestUtil.ADD_CATEGORY_DESC_AMY;
import static presspal.contact.logic.commands.CommandTestUtil.ADD_CATEGORY_DESC_BOB;

import org.junit.jupiter.api.Test;

import presspal.contact.logic.commands.AddCategoryCommand.EditCategoryDescriptor;

public class EditCategoryDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCategoryDescriptor descriptorWithSameValues = new EditCategoryDescriptor(ADD_CATEGORY_DESC_AMY);
        assertTrue(ADD_CATEGORY_DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(ADD_CATEGORY_DESC_AMY.equals(ADD_CATEGORY_DESC_AMY));

        // null -> returns false
        assertTrue(!ADD_CATEGORY_DESC_AMY.equals(null));

        // different types -> returns false
        assertTrue(!ADD_CATEGORY_DESC_AMY.equals(5));

        // different values -> returns false
        EditCategoryDescriptor differentDescriptor = new EditCategoryDescriptor(ADD_CATEGORY_DESC_BOB);
        assertFalse(ADD_CATEGORY_DESC_AMY.equals(differentDescriptor));
    }

    @Test
    public void toStringMethod() {
        EditCategoryDescriptor editCategoryDescriptor = new EditCategoryDescriptor(ADD_CATEGORY_DESC_AMY);
        String expectedString = EditCategoryDescriptor.class.getCanonicalName() + "{categories="
                + ADD_CATEGORY_DESC_AMY.getCategories() + "}";
        assertTrue(editCategoryDescriptor.toString().equals(expectedString));
    }
}
