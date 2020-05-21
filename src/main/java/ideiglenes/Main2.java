package ideiglenes;

import gameplay.gamecards.CardDeck;
import gameplay.gamecards.GameCard;
import xmlhelper.JAXBHelper;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.ArrayList;

public class Main2 {


    public static void main(String[] args) throws FileNotFoundException, JAXBException {


        CardDeck carddeck = new CardDeck();
        carddeck.setGameCards(new ArrayList<>());
        carddeck.getGameCards().add(new GameCard("smoke","images/deckimages/smoke.jpg" , 17 , 13));
        carddeck.getGameCards().add(new GameCard("jade" ,"images/deckimages/jade.jpg", 10 , 8));
        carddeck.getGameCards().add(new GameCard("noob" ,"images/deckimages/noob.jpg", 18 , 12));
        carddeck.getGameCards().add(new GameCard("quanchi" ,"images/deckimages/quanchi.jpg", 15 , 9));
        carddeck.getGameCards().add(new GameCard("kabal" ,"images/deckimages/kabal.jpg", 17 , 14));
        carddeck.getGameCards().add(new GameCard("nightwolf" ,"images/deckimages/nightwolf.jpg", 15 , 9));
        carddeck.getGameCards().add(new GameCard("scorpion" ,"images/deckimages/scorpion.jpg", 16 , 14));
        carddeck.getGameCards().add(new GameCard("liukang" ,"images/deckimages/liukang.jpg", 18 , 5));
        carddeck.getGameCards().add(new GameCard("kitana" ,"images/deckimages/kitana.jpg", 20 , 11));
        carddeck.getGameCards().add(new GameCard("johny" ,"images/deckimages/johny.jpg", 15 , 10));

        OutputStream os = new FileOutputStream("D:\\Probapakk\\deck1.xml");

        JAXBHelper.toXML(carddeck,os);

        CardDeck carddeck2 = new CardDeck();
        carddeck2.setGameCards(new ArrayList<>());
        carddeck2.getGameCards().add(new GameCard("kunglao","images/deckimages/kunglao.jpg" , 15 , 5));
        carddeck2.getGameCards().add(new GameCard("ermac" ,"images/deckimages/ermac.jpg", 17 , 7));
        carddeck2.getGameCards().add(new GameCard("cyrax" ,"images/deckimages/cyrax.jpg", 19 , 11));
        carddeck2.getGameCards().add(new GameCard("raiden" ,"images/deckimages/raiden.jpg", 15 , 10));
        carddeck2.getGameCards().add(new GameCard("sektor" ,"images/deckimages/sektor.jpg", 18 , 12));
        carddeck2.getGameCards().add(new GameCard("reptile" ,"images/deckimages/reptile.jpg", 17 , 8));
        carddeck2.getGameCards().add(new GameCard("subzero" ,"images/deckimages/subzero.jpg", 17 , 13));
        carddeck2.getGameCards().add(new GameCard("jax" ,"images/deckimages/jax.jpg", 18 , 14));
        carddeck2.getGameCards().add(new GameCard("sonya" ,"images/deckimages/sonya.jpg", 15 , 9));
        carddeck2.getGameCards().add(new GameCard("projectsub" ,"images/deckimages/projectsub.jpg", 24 , 7));



        OutputStream os2 = new FileOutputStream("D:\\Probapakk\\deck2.xml");

        JAXBHelper.toXML(carddeck2,os2);
    }
}
