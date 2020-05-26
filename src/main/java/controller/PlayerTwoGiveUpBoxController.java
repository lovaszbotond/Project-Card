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
 * This Class checks if player 2 give up the game.
 */
@Data
public class PlayerTwoGiveUpBoxController {


        @FXML
        private Label p2guptext;

    /**
     * Set player 2 name to the scene in the right field.
     */
    @FXML
    public void initialize()
        {
            p2guptext.setText(GameData.getGamePlayer(1).getName() + " gave up.");
        }

    /**
     * User can choose the Accept button.
     * @param actionEvent Player will return to the main menu.
     * @throws IOException If the ".fxml" file does not exist or miss spelled.
     */
    public void acceptHandler(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/mainmenu/mainmenu.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Project--card");
        stage.setResizable(false);
        stage.show();
        Logger.info("Player two: {} gave up.",GameData.getGamePlayer(1).getName());
        }
    }

