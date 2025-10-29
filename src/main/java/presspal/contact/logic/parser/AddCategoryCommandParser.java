package presspal.contact.logic.parser;

import static java.util.Objects.requireNonNull;
import static presspal.contact.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Stream;

import presspal.contact.commons.core.index.Index;
import presspal.contact.logic.commands.AddCategoryCommand;
import presspal.contact.logic.commands.EditCategoryCommand.EditCategoryDescriptor;
import presspal.contact.logic.parser.exceptions.ParseException;
import presspal.contact.model.category.Category;

/**
 * Parses input arguments and creates a new AddCategoryCommand object
 */
public class AddCategoryCommandParser implements Parser<AddCategoryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCatergoryCommand
     * and returns an AddCatergoryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCategoryCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_CATEGORY);

        Collection<String> rawCategories = argMultimap.getAllValues(PREFIX_CATEGORY);
        boolean onlyEmptyCategory = rawCategories.size() == 1 && rawCategories.contains("");

        if (!arePrefixesPresent(argMultimap, PREFIX_INDEX, PREFIX_CATEGORY)
                || !argMultimap.getPreamble().isEmpty()
                || onlyEmptyCategory) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCategoryCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_INDEX);

        EditCategoryDescriptor editCategoryDescriptor = new EditCategoryDescriptor();

        Set<Category> catToBeAdded = parseCategoriesForEdit(argMultimap.getAllValues(PREFIX_CATEGORY));
        editCategoryDescriptor.setCategories(catToBeAdded);

        return new AddCategoryCommand(index, editCategoryDescriptor);
    }

    /**
     * Parses {@code Collection<String> categories} into a {@code Set<Category>} if {@code categories} is non-empty.
     * If {@code categories} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Category>} containing zero categories.
     */
    private Set<Category> parseCategoriesForEdit(Collection<String> categories) throws ParseException {
        assert categories != null;
        Collection<String> categorySet = categories.size() == 1 && categories.contains("")
                ? Collections.emptySet()
                : categories;
        return ParserUtil.parseCategories(categorySet);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
