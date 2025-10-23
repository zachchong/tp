package presspal.contact.logic.parser;

import static presspal.contact.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_INDEX;

import java.util.List;

import presspal.contact.commons.core.index.Index;
import presspal.contact.logic.commands.DeleteInterviewCommand;
import presspal.contact.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteInterviewCommand object
 */
public class DeleteInterviewCommandParser implements Parser<DeleteInterviewCommand> {

    @Override
    public DeleteInterviewCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX);

        if (!argMultimap.getPreamble().isBlank()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, DeleteInterviewCommand.MESSAGE_USAGE));
        }

        List<String> indices = argMultimap.getAllValues(PREFIX_INDEX);
        if (indices.size() != 2) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, DeleteInterviewCommand.MESSAGE_USAGE));
        }

        Index personIndex = ParserUtil.parseIndex(indices.get(0));
        Index interviewIndex = ParserUtil.parseIndex(indices.get(1));

        return new DeleteInterviewCommand(personIndex, interviewIndex);
    }
}
