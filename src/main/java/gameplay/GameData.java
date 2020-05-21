package gameplay;



public class GameData {

    private static GamePlayer[] players;

    private static int turn;

    private String cardName;



    public static void setPLayers(String p1name, String p2name)
    {
        players = new GamePlayer[2];

        players[0] = GamePlayer.builder().name(p1name).healthpoint(10).build();
        players[1] = GamePlayer.builder().name(p2name).healthpoint(10).build();
    }
    public static GamePlayer getGamePlayer(int gamePlayerIndex)
    {
        return players[gamePlayerIndex];
    }

    public static void setTurn(int a)
    {
        turn = a;
    }
    public static int getTurn()
    {
        return turn;
    }


}

