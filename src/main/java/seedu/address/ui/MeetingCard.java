package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.meeting.Meeting;


/**
 * An UI component that displays information of a {@code Client}.
 */
public class MeetingCard extends UiPart<Region> {

    private static final String FXML = "MeetingListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Meeting meeting;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label startDateTime;
    @FXML
    private Label endDateTime;

    /**
     * Creates a {@code MeetingCard} with the given {@code Meeting} and index to display.
     */
    public MeetingCard(Meeting meeting, int displayedIndex) {
        super(FXML);
        this.meeting = meeting;
        if (!meeting.isUpcoming()) {
            name.setStyle("-fx-text-fill: #ADABBC;");
            id.setStyle("-fx-text-fill: #ADABBC;");
            startDateTime.setStyle("-fx-text-fill: #ADABBC;");
            endDateTime.setStyle("-fx-text-fill: #ADABBC;");
        }
        id.setText(displayedIndex + ". ");
        String nameLabel;
        if (meeting.getLabel().isEmpty()) {
            nameLabel = meeting.getName().fullName;
        } else {
            nameLabel = meeting.getName().fullName + " (" + meeting.getLabel() + ")";
        }
        name.setText(nameLabel);
        startDateTime.setText("Start: " + meeting.getStartDateTime().format(Meeting.DATETIME_FORMATTER));
        endDateTime.setText("End: " + meeting.getEndDateTime().format(Meeting.DATETIME_FORMATTER));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MeetingCard)) {
            return false;
        }

        // state check
        MeetingCard card = (MeetingCard) other;
        return id.getText().equals(card.id.getText())
                && meeting.equals(card.meeting);
    }
}
