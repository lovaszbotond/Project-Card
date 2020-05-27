package gameplay;

import gameplay.gamecards.CardDeck;
import gameplay.gamecards.GameCard;
import lombok.Builder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameDataTest {


    @Test
    void setPLayers()
    {

        List <GameCard> gameCards = new ArrayList<>();
        CardDeck cardDeck = new CardDeck(gameCards);
        gameCards.add(new GameCard("k1", "", 13,10124));
        gameCards.add(new GameCard("k2", "", 11,10123));
        gameCards.add(new GameCard("k3", "", 123,10123));
        gameCards.add(new GameCard("k4", "", 123,10123));
        gameCards.add(new GameCard("k5", "", 123,10123));
        gameCards.add(new GameCard("k6", "", 123,10123));
        gameCards.add(new GameCard("k7", "", 123,10123));
        gameCards.add(new GameCard("k8", "", 123,10123));
        gameCards.add(new GameCard("k9", "", 123,10123));
        gameCards.add(new GameCard("k10", "", 123,10123));

        GameData.setPLayers("scorpion",null,cardDeck,cardDeck);

        assertNotEquals(20,GameData.getGamePlayer(0).getHealthpoint());
        assertEquals(50,GameData.getGamePlayer(0).getHealthpoint());

        assertNotEquals(20,GameData.getGamePlayer(1).getHealthpoint());
        assertEquals(50,GameData.getGamePlayer(1).getHealthpoint());


        assertEquals(cardDeck,GameData.getGamePlayer(0).getCarddeck());
        assertEquals(cardDeck,GameData.getGamePlayer(1).getCarddeck());

        assertEquals("scorpion",GameData.getGamePlayer(0).getName());
        assertEquals(null,GameData.getGamePlayer(1).getName());

        assertNotEquals("null",GameData.getGamePlayer(0).getName());
        assertNotEquals("scorpion",GameData.getGamePlayer(1).getName());
    }

    @Test
    void setTurn() {

        GameData.setTurn(0);

        assertEquals(0,GameData.getTurn());
        assertNotEquals(1,GameData.getTurn());

        GameData.setTurn(1);

        assertEquals(1,GameData.getTurn());
        assertNotEquals(0,GameData.getTurn());

        assertNotEquals(2,GameData.getTurn());

    }
}