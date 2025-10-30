package presspal.contact.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_CATEGORY = new Prefix("c/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_ORGANISATION = new Prefix("o/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_ROLE = new Prefix("r/");

    /* Prefix definitions for Interview*/
    public static final Prefix PREFIX_HEADER = new Prefix("h/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_TIME = new Prefix("t/");
    public static final Prefix PREFIX_LOCATION = new Prefix("l/");

    /* Prefix definitions for Index */
    public static final Prefix PREFIX_INDEX = new Prefix("i/");
    public static final Prefix PREFIX_INTERVIEW_INDEX = new Prefix("v/");

    // canonical list of prefixes
    public static final Prefix[] ALL_PREFIXES = {
        PREFIX_CATEGORY, PREFIX_EMAIL, PREFIX_NAME, PREFIX_ORGANISATION, PREFIX_PHONE, PREFIX_ROLE,
        PREFIX_HEADER, PREFIX_DATE, PREFIX_TIME, PREFIX_LOCATION,
        PREFIX_INDEX, PREFIX_INTERVIEW_INDEX
    };
}
