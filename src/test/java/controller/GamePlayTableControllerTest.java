package controller;

import gameplay.gamecards.CardDeck;
import gameplay.gamecards.GameCard;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GamePlayTableControllerTest {

    @Test
    void getTopCardFromDeck() {
        List<GameCard> gameCards = new ArrayList<>();
        CardDeck cardDeck = new CardDeck(gameCards);
        gameCards.add(new GameCard("name1", "", 15,10));
        gameCards.add(new GameCard("name2", "", 15,10));
        gameCards.add(new GameCard("name3", "", 15,10));
        gameCards.add(new GameCard("name4", "", 15,10));
        gameCards.add(new GameCard("name5", "", 15,10));
        gameCards.add(new GameCard("name6", "", 15,10));
        gameCards.add(new GameCard("name7", "", 15,10));
        gameCards.add(new GameCard("name8", "", 15,10));
        gameCards.add(new GameCard("name9", "", 15,10));

        assertEquals(new GameCard("name1", "", 15,10), GamePlayTableController.getTopCardFromDeck(cardDeck.getGameCards()));
        assertEquals(new GameCard("name2", "", 15,10), GamePlayTableController.getTopCardFromDeck(cardDeck.getGameCards()));
        assertEquals(new GameCard("name3", "", 15,10), GamePlayTableController.getTopCardFromDeck(cardDeck.getGameCards()));
        assertEquals(new GameCard("name4", "", 15,10), GamePlayTableController.getTopCardFromDeck(cardDeck.getGameCards()));
        assertEquals(new GameCard("name5", "", 15,10), GamePlayTableController.getTopCardFromDeck(cardDeck.getGameCards()));

        assertNotEquals(new GameCard("name7", "", 15,10), GamePlayTableController.getTopCardFromDeck(cardDeck.getGameCards()));
        assertEquals(new GameCard("name7", "", 15,10), GamePlayTableController.getTopCardFromDeck(cardDeck.getGameCards()));

        assertEquals(new GameCard("name8", "", 15,10), GamePlayTableController.getTopCardFromDeck(cardDeck.getGameCards()));
        assertEquals(new GameCard("name9", "", 15,10), GamePlayTableController.getTopCardFromDeck(cardDeck.getGameCards()));

        try {
            GamePlayTableController.getTopCardFromDeck(cardDeck.getGameCards());
            fail();
        }catch (IndexOutOfBoundsException e)
        {

        }
    }

    @Test
    void getCardFromDeck() {
        List<GameCard> deck = new ArrayList<>();
        CardDeck cardDeck = new CardDeck(deck);
        deck.add(new GameCard("name1", "", 15,10));
        deck.add(new GameCard("name2", "", 15,10));
        deck.add(new GameCard("name3", "", 15,10));

        assertNotEquals(new GameCard("name7", "", 15,10), GamePlayTableController.getCardFromDeck(cardDeck.getGameCards(),2));
        assertEquals(new GameCard("name2","",15,10),GamePlayTableController.getCardFromDeck(cardDeck.getGameCards(),1));

        assertEquals(new GameCard("name1","",15,10),GamePlayTableController.getCardFromDeck(cardDeck.getGameCards(),0));
        try {
            GamePlayTableController.getCardFromDeck(cardDeck.getGameCards(), 1);
            fail();
        }catch(IndexOutOfBoundsException e)
        {

        }

    }
}