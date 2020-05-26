package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;
/**
 * This Class is an handler if the user/users would like to quit from the game or not.
 **/
public class ExitBoxController {
    /**
     * @param actionEvent If the player choose yes, we return to the actual background of our computer monitor scene.
     */
    public void yesexitHandler(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
        Logger.info("Thank you for the game , i hope you liked it and come back as soon as you can :).");
    }

    /**
     * @param actionEvent If the player choose no , we return to the main menu.
     * @throws IOException if the ".xml" file does not exist or miss spelled.
     */
    public void noexithandler(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/mainmenu/mainmenu.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Project--card");
        stage.show();
        Logger.info("Thank you for not leaving the game, start a new one ;).");
    }
}
