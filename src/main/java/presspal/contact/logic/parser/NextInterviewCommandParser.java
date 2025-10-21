package presspal.contact.logic.parser;

import java.util.stream.Stream;

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

}


