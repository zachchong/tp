package presspal.contact.logic.parser;

import static presspal.contact.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_DATE;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_HEADER;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_INDEX;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_LOCATION;
import static presspal.contact.logic.parser.CliSyntax.PREFIX_TIME;

import java.time.LocalDateTime;
import java.util.Set;

import presspal.contact.commons.core.index.Index;
import presspal.contact.logic.commands.AddInterviewCommand;
import presspal.contact.logic.parser.exceptions.ParseException;
import presspal.contact.model.interview.Header;
import presspal.contact.model.interview.Interview;
import presspal.contact.model.interview.Location;

/**
 * Parses input arguments and creates a new AddInterviewCommand object.
 */
public class AddInterviewCommandParser implements Parser<AddInterviewCommand> {

    // all prefix required to parse correctly
    private static final Set<Prefix> REQUIRED = Set.of(
            PREFIX_INDEX, PREFIX_HEADER, PREFIX_DATE, PREFIX_TIME, PREFIX_LOCATION
    );

    /**
     * Parses the given {@code String} of arguments in the context of the AddInterviewCommand
     * and returns an AddInterviewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddInterviewCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args, CliSyntax.ALL_PREFIXES);

        if (!map.getPreamble().isEmpty()) {
            throw formatError();
        }
        if (!hasAll(map, REQUIRED)) {
            throw formatError();
        }
        if (hasUnexpected(map, REQUIRED, CliSyntax.ALL_PREFIXES)) {
            throw formatError();
        }

        map.verifyNoDuplicatePrefixesFor(REQUIRED.toArray(new Prefix[0]));

        Index index = ParserUtil.parseIndex(req(map, PREFIX_INDEX));
        Header header = ParserUtil.parseHeader(req(map, PREFIX_HEADER));
        LocalDateTime dateTime = ParserUtil.parseLocalDateTime(
                req(map, PREFIX_DATE), req(map, PREFIX_TIME));
        Location location = ParserUtil.parseLocation(req(map, PREFIX_LOCATION));

        return new AddInterviewCommand(new Interview(header, location, dateTime), index);
    }

    /** Helper method to check if map contains all given prefixes */
    private static boolean hasAll(ArgumentMultimap map, Set<Prefix> prefixes) {
        for (Prefix p : prefixes) {
            if (map.getValue(p).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /** Any present prefix not in REQUIRED prefixes is unexpected. */
    private static boolean hasUnexpected(ArgumentMultimap map, Set<Prefix> allowedPrefixes, Prefix[] universe) {
        for (Prefix p : universe) {
            if (map.getValue(p).isPresent() && !allowedPrefixes.contains(p)) {
                return true;
            }
        }
        return false;
    }

    private static String req(ArgumentMultimap map, Prefix p) {
        return map.getValue(p).orElseThrow();
    }

    private static ParseException formatError() {
        return new ParseException(String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, AddInterviewCommand.MESSAGE_USAGE));
    }
}
