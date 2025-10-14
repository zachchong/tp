package presspal.contact.model.util;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import presspal.contact.model.ReadOnlyContactBook;
import presspal.contact.model.category.Category;
import presspal.contact.model.person.Person;

class SampleDataUtilTest {
    @Test
    public void testGetSamplePersons() {
        // Add tests to verify the correctness of getSamplePersons method
        assertNotNull(SampleDataUtil.getSamplePersons());
        assertTrue(SampleDataUtil.getSamplePersons().length == 6);
    }

    @Test
    public void getSampleContactBook_returnsValidContactBook() {
        ReadOnlyContactBook sampleBook = SampleDataUtil.getSampleContactBook();

        // Test that the book is not null
        assertNotNull(sampleBook, "Sample contact book should not be null");

        // Test that the book contains contacts
        assertFalse(sampleBook.getPersonList().isEmpty(), "Sample contact book should not be empty");

        // Test that all contacts in the book are valid
        for (Person contact : sampleBook.getPersonList()) {
            assertNotNull(contact.getName(), "Contact name should not be null");
            assertNotNull(contact.getPhone(), "Contact phone should not be null");
            assertNotNull(contact.getEmail(), "Contact email should not be null");

            // Verify phone number format (assuming 8 digits)
            assertTrue(contact.getPhone().toString().matches("\\d{8}"),
                "Phone number should be 8 digits");

            // Verify email format (basic check)
            assertTrue(contact.getEmail().toString().contains("@"),
                "Email should contain @");
        }

        // Test that the number of contacts matches expected sample size
        assertEquals(6, sampleBook.getPersonList().size(),
            "Sample book should contain exactly 6 contacts");
    }

    @Test
    public void getCategorySet_validInput_returnsCorrectSet() {
        // Test single category
        Set<Category> singleSet = SampleDataUtil.getCategorySet("friends");
        assertNotNull(singleSet, "Category set should not be null");
        assertEquals(1, singleSet.size(), "Single category set should have size 1");
        assertTrue(singleSet.stream()
                .anyMatch(category -> category.categoryName.equals("friends")),
                "Set should contain the 'friends' category");

        // Test multiple categories
        Set<Category> multipleSet = SampleDataUtil.getCategorySet("friends", "colleagues", "family");
        assertNotNull(multipleSet, "Category set should not be null");
        assertEquals(3, multipleSet.size(), "Multiple category set should have size 3");
        assertTrue(multipleSet.stream()
                .anyMatch(category -> category.categoryName.equals("friends")),
                "Set should contain 'friends' category");
        assertTrue(multipleSet.stream()
                .anyMatch(category -> category.categoryName.equals("colleagues")),
                "Set should contain 'colleagues' category");
        assertTrue(multipleSet.stream()
                .anyMatch(category -> category.categoryName.equals("family")),
                "Set should contain 'family' category");

        // Test empty input
        Set<Category> emptySet = SampleDataUtil.getCategorySet();
        assertNotNull(emptySet, "Empty category set should not be null");
        assertTrue(emptySet.isEmpty(), "Set should be empty when no categories are provided");
    }
}
