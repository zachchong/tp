package presspal.contact.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import presspal.contact.commons.core.index.Index;
import presspal.contact.commons.util.ToStringBuilder;
import presspal.contact.model.category.Category;

/**
 * Represents an editCategoryCommand with hidden internal logic and the ability to be executed.
 */
public abstract class EditCategoryCommand extends Command {

    private final Index index;
    private final EditCategoryDescriptor editCategoryDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editCategoryDescriptor details to edit the person with
     */
    public EditCategoryCommand(Index index, EditCategoryDescriptor editCategoryDescriptor) {
        requireNonNull(editCategoryDescriptor);
        requireNonNull(index);
        this.index = index;
        this.editCategoryDescriptor = editCategoryDescriptor;
    }

    public Index getIndex() {
        return this.index;
    }

    public EditCategoryDescriptor getEditCategoryDescriptor() {
        return this.editCategoryDescriptor;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editCategoryDescriptor", editCategoryDescriptor)
                .toString();
    }

    /**
     * Stores the categories to edit the person with.
     */
    public static class EditCategoryDescriptor {
        private Set<Category> categories;

        public EditCategoryDescriptor() {
        }

        public EditCategoryDescriptor(EditCategoryDescriptor editCategoryDescriptor) {
            this.categories = editCategoryDescriptor.categories;
        }

        /**
         * Sets {@code categories} to this object's {@code categories}.
         * A defensive copy of {@code categories} is used internally.
         */
        public void setCategories(Set<Category> categories) {
            this.categories = new HashSet<>(categories);
        }

        /**
         * Returns an unmodifiable category set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code categories} is non-null.
         */
        public Set<Category> getCategories() {
            return Collections.unmodifiableSet(categories);
        }

        public String getCategoriesAsString() {
            StringBuilder sb = new StringBuilder();
            java.util.Iterator<Category> iterator = categories.iterator();
            int i = 1;
            while (iterator.hasNext()) {
                Category category = iterator.next();
                sb.append(i).append(". ").append(category);
                if (iterator.hasNext()) {
                    sb.append("\n");
                }
                i++;
            }
            return sb.toString();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof EditCategoryDescriptor)) {
                return false;
            }

            EditCategoryDescriptor otherDescriptor = (EditCategoryDescriptor) other;

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
