package presspal.contact.logic.parser;

import static presspal.contact.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import presspal.contact.commons.core.index.Index;
import presspal.contact.logic.commands.DeleteInterviewCommand;
import presspal.contact.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteInterviewCommand object
 */
public class DeleteInterviewCommandParser implements Parser<DeleteInterviewCommand> {

    @Override
    public DeleteInterviewCommand parse(String args) throws ParseException {
        String trimmed = args == null ? "" : args.trim();
        if (trimmed.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteInterviewCommand.MESSAGE_USAGE));
        }

        String[] parts = trimmed.split("\\s+");
        if (parts.length != 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteInterviewCommand.MESSAGE_USAGE));
        }

        Index personIndex = ParserUtil.parseIndex(parts[0]);
        Index interviewIndex = ParserUtil.parseIndex(parts[1]);
        return new DeleteInterviewCommand(personIndex, interviewIndex);
    }
}
