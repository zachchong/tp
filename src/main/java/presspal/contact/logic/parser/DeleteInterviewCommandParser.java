package presspal.contact.logic.parser;

import static presspal.contact.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_EMAIL;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_INDEX;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_INTERVIEW_INDEX;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_NAME;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_ORGANISATION;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_PHONE;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import presspal.contact.commons.core.index.Index;
import presspal.contact.logic.commands.DeleteInterviewCommand;
import presspal.contact.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteInterviewCommand object
 */
public class DeleteInterviewCommandParser implements Parser<DeleteInterviewCommand> {

    @Override
    public DeleteInterviewCommand parse(String args) throws ParseException {
        // Tokenize with ALL known prefixes so we can detect (and reject) extras explicitly
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args,
                PREFIX_INDEX,
                PREFIX_INTERVIEW_INDEX,
                // illegal prefixes below
                PREFIX_NAME,
                PREFIX_PHONE,
                PREFIX_EMAIL,
                PREFIX_ORGANISATION,
                PREFIX_ROLE,
                PREFIX_CATEGORY
        );

        // no preamble allowed
        if (!argMultimap.getPreamble().isBlank()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, DeleteInterviewCommand.MESSAGE_USAGE));
        }

        // exactly one i/ and one v/ required
        boolean hasPerson = argMultimap.getValue(PREFIX_INDEX).isPresent();
        boolean hasInterview = argMultimap.getValue(PREFIX_INTERVIEW_INDEX).isPresent();
        boolean onePerson = argMultimap.getAllValues(PREFIX_INDEX).size() == 1;
        boolean oneInterview = argMultimap.getAllValues(PREFIX_INTERVIEW_INDEX).size() == 1;

        if (!(hasPerson && hasInterview && onePerson && oneInterview)) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, DeleteInterviewCommand.MESSAGE_USAGE));
        }

        // any extra prefixes beyond i/ and v/, throws error
        List<Prefix> forbiddenPrefixes = Stream.of(
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ORGANISATION, PREFIX_ROLE, PREFIX_CATEGORY
        ).collect(Collectors.toList());

        List<String> extras = new ArrayList<>();
        for (Prefix p : forbiddenPrefixes) {
            if (!argMultimap.getAllValues(p).isEmpty()) {
                extras.add(p.getPrefix());
            }
        }
        if (!extras.isEmpty()) {
            String extraMsg = String.format(DeleteInterviewCommand.MESSAGE_UNEXPECTED_PREFIXES,
                    String.join(", ", extras));
            throw new ParseException(extraMsg);
        }

        Index personIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).get());
        Index interviewIndex = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INTERVIEW_INDEX).get());

        return new DeleteInterviewCommand(personIndex, interviewIndex);
    }
}
