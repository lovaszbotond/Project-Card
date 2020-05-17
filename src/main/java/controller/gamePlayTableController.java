package controller;

import gameplay.gameData;
import java.io.IOException;
import javafx.fxml.FXML;


public class gamePlayTableController {

    private gameData gameData;
    private String p1Name;
    private String p2Name;

    public void fieldNames( String p1Name, String p2Name)
    {
        this.p1Name = p1Name; this.p2Name = p2Name;

        gameData.getGamePlayer(0).setName(p1Name);
        gameData.getGamePlayer(1).setName(p2Name);
    }





    @FXML
    private void switchToPrimary() throws IOException {
    }

}
