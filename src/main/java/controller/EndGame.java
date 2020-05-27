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
 * This Class handle the endgame , which shows us who won the game.
 **/
public class EndGame {
    /**
     * Handle the gameState if it is over.
     * @param actionEvent Responsible for the player that he understand , the game is over.
     * @throws IOException if the ".fxml" file does not exist or misspelled.
     */
    public void endGameHandler(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/mainmenu/mainmenu.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Project--card");
        stage.setResizable(false);
        stage.show();
        Logger.info("The Game is over.");
    }
}
