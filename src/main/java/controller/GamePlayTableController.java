package controller;

import gameplay.GameData;
import java.io.IOException;
import javafx.fxml.FXML;


public class GamePlayTableController {

    private GameData gameData;
    private String p1Name;
    private String p2Name;

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




    @FXML
    private void switchToPrimary() throws IOException {
    }

}

