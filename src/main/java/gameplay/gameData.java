package gameplay;


import javafx.fxml.FXML;
import lombok.Builder;
import lombok.Data;


@Data
public class gameData {

    private gamePlayer[] players;

    public gameData()
    {
        players = new gamePlayer[2];

        players[0] = gamePlayer.builder().name("p1Name").build();
        players[1] = gamePlayer.builder().name("p2Name").build();
    }
    public gamePlayer getGamePlayer(int gamePlayerIndex)
    {
        return players[gamePlayerIndex];
    }

}
