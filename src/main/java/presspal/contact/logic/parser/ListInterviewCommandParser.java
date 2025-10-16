package presspal.contact.logic.parser;

import static presspal.contact.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.stream.Stream;

import presspal.contact.commons.core.index.Index;
import presspal.contact.logic.commands.ListInterviewCommand;
import presspal.contact.logic.parser.exceptions.ParseException;

/**
 * Lists all interviews in the person in the contact book to the user.
 */
public class ListInterviewCommandParser implements Parser<ListInterviewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListInterviewCommand
     * and returns an ListInterviewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListInterviewCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_INDEX);

        //check for correct formatting
        if (!isPrefixPresent(argMultimap, PREFIX_INDEX) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListInterviewCommand.MESSAGE_USAGE));
        }

        //get the index for the person
        Index index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        //return ListInterviewCommand with the index of Person
        return new ListInterviewCommand(index);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return Stream.of(prefix).allMatch(p -> argumentMultimap.getValue(p).isPresent());
    }
}
