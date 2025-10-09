package presspal.contact.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import presspal.contact.commons.exceptions.IllegalValueException;
import presspal.contact.model.category.Category;

/**
 * Jackson-friendly version of {@link Category}.
 */
class JsonAdaptedCategory {

    private final String categoryName;

    /**
     * Constructs a {@code JsonAdaptedTag} with the given {@code tagName}.
     */
    @JsonCreator
    public JsonAdaptedCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Converts a given {@code Tag} into this class for Jackson use.
     */
    public JsonAdaptedCategory(Category source) {
        categoryName = source.categoryName;
    }

    @JsonValue
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Tag} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted tag.
     */
    public Category toModelType() throws IllegalValueException {
        if (!Category.isValidCategoryName(categoryName)) {
            throw new IllegalValueException(Category.MESSAGE_CONSTRAINTS);
        }
        return new Category(categoryName);
    }

}
