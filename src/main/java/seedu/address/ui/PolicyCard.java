package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.policy.Policy;


/**
 * A UI component that displays information of a {@code Policy}.
 */
public class PolicyCard extends UiPart<Region> {

    private static final String FXML = "PolicyListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Policy policy;

    @FXML
    private HBox cardPane;
    @FXML
    private Label policyName;
    @FXML
    private Label id;
    @FXML
    private Label company;
    @FXML
    private Label policyManager;
    @FXML
    private Label premium;

    /**
     * Creates a {@code PolicyCard} with the given {@code Policy} and index to display.
     */
    public PolicyCard(Policy policy, int displayedIndex) {
        super(FXML);
        this.policy = policy;
        id.setText(displayedIndex + ". ");
        policyName.setText(policy.getName().fullName);
        company.setText(policy.getCompany().fullName);
        policyManager.setText("Managed by " + policy.getPolicyManager().fullName);
        premium.setText("$" + policy.getPremium());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PolicyCard)) {
            return false;
        }

        // state check
        PolicyCard card = (PolicyCard) other;
        return id.getText().equals(card.id.getText())
                && policy.equals(card.policy);
    }
}
