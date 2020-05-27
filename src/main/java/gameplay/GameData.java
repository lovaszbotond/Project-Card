package gameplay;

import gameplay.gamecards.CardDeck;

/**
 * This class represent the state of turn , and players conditions.
 */
public class GameData {
    /**
     * Array of game player.
     */
    private static GamePlayer[] players;
    /**
     * Instance of turn.
     */
    private static int turn;

    /**
     * Set player properties.
     * @param p1name Name of player 1.
     * @param p2name Name of player 2.
     * @param playerDeck1 Card Deck of player 1.
     * @param playerDeck2 Card Deck of player 2.
     */
    public static void setPLayers(String p1name, String p2name, CardDeck playerDeck1,CardDeck playerDeck2)
    {
        players = new GamePlayer[2];

        players[0] = GamePlayer.builder().name(p1name).healthpoint(50).carddeck(playerDeck1).build();
        players[1] = GamePlayer.builder().name(p2name).healthpoint(50).carddeck(playerDeck2).build();

    }

    /**
     * Returns player 1 or player 2.
     * @param gamePlayerIndex Helps us to get player 1 or player 2 from Gameplayer[] players array.
     * @return Player 1 or player 2 depends on the index we choose ( 0 or 1 )
     */
    public static GamePlayer getGamePlayer(int gamePlayerIndex)
    {
        return players[gamePlayerIndex];
    }

    /**
     * Method for setting the turn status.
     * @param a Set round 0 or 1. Round '0' for player 1 , round '1' for player 2.
     */
    public static void setTurn(int a)
    {
        turn = a;
    }

    /**
     * Method for checking the turn index, that which round we got at the moment.
     * @return The actual turn index.
     */
    public static int getTurn()
    {
        return turn;
    }
}

