package presspal.contact.logic.parser;

import static presspal.contact.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.stream.Stream;

import presspal.contact.commons.core.index.Index;
import presspal.contact.logic.commands.NextInterviewCommand;
import presspal.contact.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new NextInterviewCommand object.
 */
public class NextInterviewCommandParser implements Parser<NextInterviewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the NextInterviewCommand
     * and returns a NextInterviewCommand object for execution.
     * @throws ParseException if the user input is not empty
     */
    public NextInterviewCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (!trimmedArgs.isEmpty()) {
            throw new ParseException(
                    "The command '" + NextInterviewCommand.COMMAND_WORD + "' does not take any arguments."
            );
        }
        return new NextInterviewCommand();
    }

    /**
     * Returns true if the given prefix has a non-empty value in the argument map.
     */
    private static boolean isPrefixPresent(ArgumentMultimap argumentMultimap, Prefix prefix) {
        return Stream.of(prefix).allMatch(p -> argumentMultimap.getValue(p).isPresent());
    }
}


