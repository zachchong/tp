package presspal.contact.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import presspal.contact.testutil.PersonBuilder;

public class PersonContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PersonContainsKeywordsPredicate firstPredicate = new PersonContainsKeywordsPredicate(firstPredicateKeywordList);
        PersonContainsKeywordsPredicate secondPredicate = new PersonContainsKeywordsPredicate(
                secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonContainsKeywordsPredicate firstPredicateCopy = new PersonContainsKeywordsPredicate(
                firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_matchesAnyField_returnsTrue() {
        // Name: one keyword
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Name: multiple keywords
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Name: only one matching keyword
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Name: mixed case
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Organisation
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("CAPT"));
        assertTrue(predicate.test(new PersonBuilder().withOrganisation("CAPT").build()));

        // Organisation: mixed case
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("cApT"));
        assertTrue(predicate.test(new PersonBuilder().withOrganisation("CAPT").build()));

        // Role
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("Student"));
        assertTrue(predicate.test(new PersonBuilder().withRole("Student").build()));

        // Role: mixed case
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("sTuDeNt"));
        assertTrue(predicate.test(new PersonBuilder().withRole("Student").build()));

        // Categories (assuming PersonBuilder supports withCategories(String...))
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("politics"));
        assertTrue(predicate.test(new PersonBuilder().withCategories("politics").build()));

        // Categories: mixed case
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("PoLiTiCs"));
        assertTrue(predicate.test(new PersonBuilder().withCategories("politics").build()));

        // Categories: match among multiple categories
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("sports"));
        assertTrue(predicate.test(new PersonBuilder().withCategories("politics", "sports").build()));
    }

    @Test
    public void test_doesNotMatchAnySearchedField_returnsFalse() {
        // Zero keywords
        PersonContainsKeywordsPredicate predicate =
                new PersonContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Completely non-matching keyword
        predicate = new PersonContainsKeywordsPredicate(Collections.singletonList("Carol"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice Bob")
                .withOrganisation("CAPT")
                .withRole("Student")
                .withCategories("politics")
                .build()));

        // Keywords match phone/email only (not searched)
        predicate = new PersonContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withPhone("12345")
                .withEmail("alice@email.com")
                // Ensure searched fields don't match
                .withOrganisation("NUS")
                .withRole("Reporter")
                .withCategories("science")
                .build()));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("keyword1", "keyword2");
        PersonContainsKeywordsPredicate predicate = new PersonContainsKeywordsPredicate(keywords);

        String expected = PersonContainsKeywordsPredicate.class.getCanonicalName() + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
