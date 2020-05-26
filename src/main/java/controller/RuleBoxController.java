package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Data;
import org.tinylog.Logger;

import java.io.IOException;

/**
 * This Class is shows us the scene where we can read the rules.
 */
@Data
public class RuleBoxController {
    /**
     * The user can check the rules if he push the rule button.
     * @param actionEvent User get back the main menu scene.
     * @throws IOException if the ".fxml" file does not exist or miss spelled.
     */
    public void acceptHandler(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/mainmenu/mainmenu.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Project--card");
        stage.show();
        Logger.info("If you do not understand something , just send me a message on email : botond.lovasz@gmail.com");
    }
}
