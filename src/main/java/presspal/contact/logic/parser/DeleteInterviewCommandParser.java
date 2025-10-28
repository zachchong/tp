package presspal.contact.logic.parser;

import static presspal.contact.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_INDEX;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_INTERVIEW_INDEX;

import java.util.Arrays;

import presspal.contact.commons.core.index.Index;
import presspal.contact.logic.commands.DeleteInterviewCommand;
import presspal.contact.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteInterviewCommand object
 */
public class DeleteInterviewCommandParser implements Parser<DeleteInterviewCommand> {

    private static final Prefix[] ALLOWED = { PREFIX_INDEX, PREFIX_INTERVIEW_INDEX };

    @Override
    public DeleteInterviewCommand parse(String args) throws ParseException {

        ArgumentMultimap mm = ArgumentTokenizer.tokenize(
                args, PREFIX_INDEX, PREFIX_INTERVIEW_INDEX
        );

        // Require i/ and v/, and no preamble
        if (!arePresent(mm, PREFIX_INDEX, PREFIX_INTERVIEW_INDEX)
                || !mm.getPreamble().isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, DeleteInterviewCommand.MESSAGE_USAGE));
        }

        // exactly one i/ and v/
        mm.verifyNoDuplicatePrefixesFor(PREFIX_INDEX, PREFIX_INTERVIEW_INDEX);

        Index personIndex = ParserUtil.parseIndex(mm.getValue(PREFIX_INDEX).orElse(""));
        Index interviewIndex = ParserUtil.parseIndex(mm.getValue(PREFIX_INTERVIEW_INDEX).orElse(""));
        return new DeleteInterviewCommand(personIndex, interviewIndex);
    }

    private static boolean arePresent(ArgumentMultimap mm, Prefix... ps) {
        return Arrays.stream(ps).allMatch(p -> mm.getValue(p).isPresent());
    }
}
