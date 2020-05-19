package controller;

import gameplay.GameData;


import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.tinylog.Logger;


public class GamePlayTableController {


    private GameData gameData;
    public static String p1Name;
    public static String p2Name;

    public void fieldNames( String p1Name, String p2Name)
    {
        this.p1Name = p1Name;
        this.p2Name = p2Name;

        gameData.getGamePlayer(0).setName(p1Name);
        gameData.getGamePlayer(1).setName(p2Name);
    }

    @FXML
    public void initialize()
    {
        gameData = new GameData();
    }



    public void PlayerOneGupHandler(ActionEvent actionEvent) throws IOException {

        // TODO -> only player 1 turn can use this

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/gameplay/p1gup.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Never Give Up");
        stage.show();
        Logger.info("Player one : {} want to give up.",p1Name);
    }
}

