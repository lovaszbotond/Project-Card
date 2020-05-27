package controller;

import gameplay.GameData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lombok.Data;
import org.tinylog.Logger;
import java.io.IOException;

/**
 * This Class checks if player1 gave up the game.
 */
@Data
public class PlayerOneGiveUpBoxController {

    @FXML
    private Label p1guptext;

    /**
     * Set player 1 name to the scene in the right field.
     */
    @FXML
    public void initialize()
    {
        p1guptext.setText(GameData.getGamePlayer(0).getName() + " gave up.");
    }

    /**
     * User can choose the Accept button.
     * @param actionEvent Player will return to the main menu.
     * @throws IOException If the ".fxml" file does not exist or misspelled.
     */
    public void acceptHandler(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/mainmenu/mainmenu.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Project--card");
        stage.setResizable(false);
        stage.show();
        Logger.info("Player one: {} gave up.",GameData.getGamePlayer(0).getName());
    }
}
