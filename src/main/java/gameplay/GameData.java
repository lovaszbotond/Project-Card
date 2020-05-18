package gameplay;


import lombok.Data;


@Data
public class GameData {

    private GamePlayer[] players;

    public GameData()
    {
        players = new GamePlayer[2];

        players[0] = GamePlayer.builder().name("p1Name").build();
        players[1] = GamePlayer.builder().name("p2Name").build();
    }
    public GamePlayer getGamePlayer(int gamePlayerIndex)
    {
        return players[gamePlayerIndex];
    }

}
