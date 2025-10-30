package presspal.contact.model.category;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static presspal.contact.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Category(null));
    }

    @Test
    public void constructor_invalidCategoryName_throwsIllegalArgumentException() {
        String invalidCategoryName = "";
        assertThrows(IllegalArgumentException.class, () -> new Category(invalidCategoryName));
    }

    @Test
    public void isValidCategoryName() {
        // null category name
        assertThrows(NullPointerException.class, () -> Category.isValidCategoryName(null));
    }

    @Test
    public void isValidCategoryName_lengthLimits() {

        // EP: less than 20 characters -> valid
        String valid19 = "aaaaaaaaaaaaaaaaaaa"; // 19 a's
        assertTrue(Category.isValidCategoryName(valid19));

        // EP: exactly 20 characters -> valid
        String valid20 = "aaaaaaaaaaaaaaaaaaaa"; // 20 a's
        assertTrue(Category.isValidCategoryName(valid20));

        // EP: 21 characters (too long) -> invalid
        String invalid21 = valid20 + "a";
        assertFalse(Category.isValidCategoryName(invalid21));

        // constructor should reject too-long category names
        assertThrows(IllegalArgumentException.class, () -> new Category(invalid21));
    }

}
