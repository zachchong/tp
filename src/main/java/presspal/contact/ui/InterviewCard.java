package presspal.contact.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import presspal.contact.model.interview.Interview;

/**
 * An UI component that displays information of an {@code Interview}.
 */
public class InterviewCard extends UiPart<Region> {

    private static final String FXML = "InterviewListCard.fxml";

    public final Interview interview;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label date;
    @FXML
    private Label location;
    @FXML
    private Label notes;

    /**
     * Creates an {@code InterviewCard} with the given {@code Interview} and index to display.
     */
    public InterviewCard(Interview interview, int displayedIndex) {
        super(FXML);
        this.interview = interview;
        id.setText(displayedIndex + ". ");
        date.setText(interview.getDateTime().toString());
        location.setText(interview.getLocation().toString());
        notes.setText(interview.getHeader().toString());
    }
}