package gameplay;


import gameplay.gamecards.CardDeck;

import java.util.Random;

public class GameData {

    private static GamePlayer[] players;

    private static int turn;


// carddeck objektumom a gameplayeren belul ugye CardDeck típusú -> tehát a paramétere a setPlayers metoduson belül megadott érték lesz playerDeck1-2
    public static void setPLayers(String p1name, String p2name, CardDeck playerDeck1,CardDeck playerDeck2 , SpecialSkill specialplayer1 , SpecialSkill specialPlayer2 )
    {
        players = new GamePlayer[2];

        players[0] = GamePlayer.builder().name(p1name).healthpoint(50).carddeck(playerDeck1).special(specialplayer1).build();
        players[1] = GamePlayer.builder().name(p2name).healthpoint(50).carddeck(playerDeck2).special(specialPlayer2).build();
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

