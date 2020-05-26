package controller;

import gameplay.GameData;

import java.io.IOException;
import java.util.*;

import gameplay.gamecards.CardDeck;
import gameplay.gamecards.GameCard;

import javafx.scene.control.Button;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lombok.Data;
import org.tinylog.Logger;
import utillities.FileHelper;
import xmlhelper.JAXBHelper;

import javax.xml.bind.JAXBException;

/**
 * This Class responsible for the whole control above of gameplay.
 */
@Data
public class GamePlayTableController {

    @FXML
    private Label p1timer;
    private long start1;
    @FXML
    private Label p2timer;
    private long start2;
    @FXML
    private Label playeronenameslot, playertwonameslot;
    @FXML
    private Label p1Lifepoints, p2Lifepoints;
    @FXML
    private Button p1gup, p2gup;
    @FXML
    private Button p2spec, p1spec;
    @FXML
    private Button p2card00, p2card01, p1card00, p1card01;
    @FXML
    private Button p1Deck, p2Deck;
    @FXML
    private GridPane gridp1, gridp2;
    @FXML
    private Button p1cardTableSlot00, p1cardTableSlot01, p2cardTableSlot00, p2cardTableSlot01;
    @FXML
    private Button endturn;

    private CardDeck player1hand, player2hand;
    private CardDeck player1Table, player2Table;

    private boolean p2card00selected, p2card01selected, p1card00selected, p1card01selected;
    private boolean p2cardTableSlot00selected, p2cardTableSlot01selected, p1cardTableSlot00selected, p1cardTableSlot01selected;

    /**
     * This method initialize the start state of the game thanks for javaFx.
     * @throws JAXBException If something wrong with reading/creating the file.
     * @throws IOException If the path of the file is wrong , or miss spelled '.xml' or wrong name of it.
     */
    @FXML
    public void initialize() throws JAXBException, IOException {

        if (!FileHelper.doesSaveFileExist()) {
            FileHelper.createSaveFile();
        }

        playeronenameslot.setText(GameData.getGamePlayer(0).getName());
        playertwonameslot.setText(GameData.getGamePlayer(1).getName());

        GameData.setTurn(1);

        p1Lifepoints.setText(String.valueOf(GameData.getGamePlayer(0).getHealthpoint()));
        p2Lifepoints.setText(String.valueOf(GameData.getGamePlayer(1).getHealthpoint()));

        CardDeck deck1 = JAXBHelper.fromXML(gameplay.gamecards.CardDeck.class, getClass().getClassLoader().getResourceAsStream("xml/deck1.xml"));
        CardDeck deck2 = JAXBHelper.fromXML(gameplay.gamecards.CardDeck.class, getClass().getClassLoader().getResourceAsStream("xml/deck2.xml"));
        Collections.shuffle(deck1.getGameCards());
        Collections.shuffle(deck2.getGameCards());

        Random randdeck = new Random();
        if (randdeck.nextBoolean()) {
            GameData.getGamePlayer(0).setCarddeck(deck1);
            GameData.getGamePlayer(1).setCarddeck(deck2);
        } else {
            GameData.getGamePlayer(0).setCarddeck(deck2);
            GameData.getGamePlayer(1).setCarddeck(deck1);
        }

        player1hand = new CardDeck(new ArrayList<>());
        player1hand.getGameCards().add(getTopCardFromDeck(GameData.getGamePlayer(0).getCarddeck().getGameCards()));
        player1hand.getGameCards().add(getTopCardFromDeck(GameData.getGamePlayer(0).getCarddeck().getGameCards()));

        p1card00.setGraphic(new ImageView(player1hand.getGameCards().get(0).getCardImage()));
        p1card01.setGraphic(new ImageView(player1hand.getGameCards().get(1).getCardImage()));

        player2hand = new CardDeck(new ArrayList<>());
        player2hand.getGameCards().add(getTopCardFromDeck(GameData.getGamePlayer(1).getCarddeck().getGameCards()));
        player2hand.getGameCards().add(getTopCardFromDeck(GameData.getGamePlayer(1).getCarddeck().getGameCards()));

        p2card00.setGraphic(new ImageView(player2hand.getGameCards().get(0).getCardImage()));
        p2card01.setGraphic(new ImageView(player2hand.getGameCards().get(1).getCardImage()));

        player2Table = new CardDeck(new ArrayList<>());
        player1Table = new CardDeck(new ArrayList<>());

        p2cardTableSlot00.setVisible(false);
        p2cardTableSlot01.setVisible(false);

        p1cardTableSlot00.setVisible(false);
        p1cardTableSlot01.setVisible(false);

        p2gup.setDisable(true);
        p2gup.setDisable(true);

        p2spec.setDisable(true);

        p2card00.setVisible(false);
        p2card01.setVisible(false);

        p2Deck.setDisable(true);
        Logger.info("The game has started, {} start the game",GameData.getGamePlayer(0).getName());
    }

    /**
     * This method checks how many health point a player got.
     */
    public void checkPlayerHealth() {
        if ((GameData.getGamePlayer(0).getHealthpoint()) <= 0) {
            Logger.info("{} health is {}",GameData.getGamePlayer(0).getName(),GameData.getGamePlayer(0).getHealthpoint() );
            try {
                endGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if ((GameData.getGamePlayer(1).getHealthpoint()) <= 0)
        {
            Logger.info("{} health is {}",GameData.getGamePlayer(1).getName(),GameData.getGamePlayer(1).getHealthpoint() );
            try {
                endGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method store infos from checkPlayerHealth method.
     * @throws IOException if the '.xml' file does not exist.
     */
    private void endGame() throws IOException {
        try {
            Logger.info("We saved the actual state of the game, in main menu, you can check the scores if you push the HighScore button there.");
            FileHelper.savePlayerStats(GameData.getGamePlayer(0), GameData.getGamePlayer(1));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/gameplay/endgame.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ( gridp1.getScene().getWindow());
        stage.setScene(new Scene(root));
        stage.setTitle("Project--card");
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Handle the player 1 give up button.
     * @param actionEvent Player 1 give up the game.
     * @throws IOException If something wrong with the path.
     */
    public void PlayerOneGupHandler(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/gameplay/p1gup.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Never Give Up");
        stage.show();

        Logger.info("Player one : {} want to give up.", GameData.getGamePlayer(0).getName());
    }

    /**
     * Handle the player 2 give up button.
     * @param actionEvent Player 2 giva up the game.
     * @throws IOException If something wrong with the path.
     */
    public void PlayerTwoGupHandler(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/gameplay/p2gup.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Never Give Up");
        stage.show();
        Logger.info("Player two : {} want to give up.", GameData.getGamePlayer(1).getName());
    }

    /**
     * Handle if the player would like to end the turn.
     * @param mouseEvent Switch the turn.
     */
    public void endTurnHandler(MouseEvent mouseEvent) {
        if (GameData.getTurn() == 0)
            {
            Logger.info("{} turn.", GameData.getGamePlayer(0).getName());
            GameData.setTurn(1);

            p1gup.setDisable(false);
            p2gup.setDisable(true);

            p1spec.setDisable(false);
            p2spec.setDisable(true);

            p2Deck.setDisable(true);
            p1Deck.setDisable(false);

            p2card00.setVisible(false);
            p2card01.setVisible(false);

            p1card00.setVisible(true);
            p1card01.setVisible(true);

            if (player1hand.getGameCards().size() == 0) {
                p1card00.setVisible(false);

                p1card01.setVisible(false);
            } else if (player1hand.getGameCards().size() == 1) {
                p1card01.setVisible(false);

                p1card00.setVisible(true);
            } else {
                p1card00.setVisible(true);

                p1card01.setVisible(true);
            }
            if (player1Table.getGameCards().size() == 0) {
                p1cardTableSlot00.setVisible(false);
                p1cardTableSlot00.setDisable(true);

                p1cardTableSlot01.setVisible(false);
                p1cardTableSlot01.setDisable(true);
            } else if (player1Table.getGameCards().size() == 1) {
                p1cardTableSlot00.setDisable(false);
                p1cardTableSlot00.setVisible(true);

                p1cardTableSlot01.setDisable(true);
                p1cardTableSlot01.setVisible(false);
            } else {
                p1cardTableSlot00.setVisible(true);
                p1cardTableSlot00.setDisable(false);

                p1cardTableSlot01.setVisible(true);
                p1cardTableSlot01.setDisable(false);
            }
            if (player2Table.getGameCards().size() == 0) {
                p2cardTableSlot00.setDisable(true);
                p2cardTableSlot00.setVisible(false);

                p2cardTableSlot01.setDisable(true);
                p2cardTableSlot01.setVisible(false);

            } else if (player2Table.getGameCards().size() == 1) {
                p2cardTableSlot00.setDisable(false);
                p2cardTableSlot00.setVisible(true);

                p2cardTableSlot01.setDisable(true);
                p2cardTableSlot01.setVisible(false);
            } else {
                p2cardTableSlot00.setDisable(false);
                p2cardTableSlot00.setVisible(true);

                p2cardTableSlot01.setVisible(true);
                p2cardTableSlot01.setDisable(false);
            }
        } else
            {
            Logger.info("{} turn.", GameData.getGamePlayer(1).getName());
            GameData.setTurn(0);
            p2gup.setDisable(false);
            p1gup.setDisable(true);

            p2spec.setDisable(false);
            p1spec.setDisable(true);

            p2Deck.setDisable(false);
            p1Deck.setDisable(true);

            p2card00.setVisible(true);
            p2card01.setVisible(true);

            p1card00.setVisible(false);
            p1card01.setVisible(false);
            if (player2hand.getGameCards().size() == 0) {
                p2card00.setVisible(false);
                p2card00.setDisable(true);

                p2card01.setDisable(true);
                p2card01.setVisible(false);
            } else if (player2hand.getGameCards().size() == 1) {
                p2card00.setVisible(true);
                p2card00.setDisable(false);

                p2card01.setDisable(true);
                p2card01.setVisible(false);
            } else {
                p2card00.setVisible(true);
                p2card00.setDisable(false);

                p2card01.setVisible(true);
                p2card01.setDisable(false);
            }
            if (player2Table.getGameCards().size() == 0) {
                p2cardTableSlot00.setDisable(true);
                p2cardTableSlot00.setVisible(false);

                p2cardTableSlot01.setVisible(false);
                p2cardTableSlot01.setDisable(false);
            } else if (player2Table.getGameCards().size() == 1) {
                p2cardTableSlot00.setDisable(false);
                p2cardTableSlot00.setVisible(true);

                p2cardTableSlot01.setDisable(true);
                p2cardTableSlot01.setVisible(false);
            } else {
                p2cardTableSlot00.setDisable(false);
                p2cardTableSlot00.setVisible(true);

                p2cardTableSlot01.setVisible(true);
                p2cardTableSlot01.setDisable(false);
            }
            if (player1Table.getGameCards().size() == 0) {
                p1cardTableSlot00.setDisable(true);
                p1cardTableSlot00.setVisible(false);

                p1cardTableSlot01.setDisable(true);
                p1cardTableSlot01.setVisible(false);
            } else if (player1Table.getGameCards().size() == 1) {
                p1cardTableSlot00.setDisable(false);
                p1cardTableSlot00.setVisible(true);

                p1cardTableSlot01.setVisible(false);
                p1cardTableSlot01.setDisable(true);
            } else {
                p1cardTableSlot00.setVisible(true);
                p1cardTableSlot00.setDisable(false);

                p1cardTableSlot01.setVisible(true);
                p1cardTableSlot01.setDisable(false);
            }
        }
    }

    /**
     * Handle the special skill of player 1.
     * @param actionEvent Player 1 use his special skill.
     */
    public void p1specHandler(ActionEvent actionEvent) {
        GameData.getGamePlayer(0).setHealthpoint(GameData.getGamePlayer(0).getHealthpoint() + 10);
        p1Lifepoints.setText(String.valueOf(GameData.getGamePlayer(0).getHealthpoint()));

        gridp1.getChildren().remove(p1spec);
        Logger.info("{} used an ability.",GameData.getGamePlayer(0).getName());
    }

    /**
     * Handle the special skill of player 2.
     * @param actionEvent Player 2 use his special skill.
     */
    public void p2specHandler(ActionEvent actionEvent) {
        GameData.getGamePlayer(1).setHealthpoint(GameData.getGamePlayer(1).getHealthpoint() + 10);
        p2Lifepoints.setText(String.valueOf(GameData.getGamePlayer(1).getHealthpoint()));

        gridp2.getChildren().remove(p2spec);
        Logger.info("{} used an ability.",GameData.getGamePlayer(1).getName());
    }

    /**
     * This method helps us to get the first card from the list and remove the picked card from the list.
     * @param deck CardDeck of player 1 or player 2.
     * @return The card we picked.
     */
    public static GameCard getTopCardFromDeck(List<GameCard> deck) {
        GameCard card = deck.get(0);
        deck.remove(0);
        return card;
    }

    /**
     * This method is almost the same like getTopCardFromDeck , but now we can choose which index we would like to get from the list.
     * @param deck CardDeck of player 1 or player 2.
     * @param index The position of card in the list.
     * @return The car we picked.
     */
    public static GameCard getCardFromDeck(List<GameCard> deck, int index) {
        GameCard card = deck.get(index);
        deck.remove(index);
        return card;
    }

    /**
     * Handle player 2 deck.
     * @param actionEvent Pick 1 card from the deck , depends on how many card we got in it hands.
     */
    public void p2DeckHandler(ActionEvent actionEvent) {
        if (player2hand.getGameCards().size() == 1) {
            player2hand.getGameCards().add(getTopCardFromDeck(GameData.getGamePlayer(1).getCarddeck().getGameCards()));
            p2card00.setGraphic(new ImageView(player2hand.getGameCards().get(0).getCardImage()));
            p2card01.setGraphic(new ImageView(player2hand.getGameCards().get(1).getCardImage()));

            p2card01.setVisible(true);
            p2card01.setDisable(false);

            p2card00.setVisible(true);

            Logger.info("{} drew a card.",GameData.getGamePlayer(1).getName());
        } else if (player2hand.getGameCards().size() == 0) {
            player2hand.getGameCards().add(getTopCardFromDeck(GameData.getGamePlayer(1).getCarddeck().getGameCards()));
            p2card00.setGraphic(new ImageView(player2hand.getGameCards().get(0).getCardImage()));

            p2card00.setVisible(true);

            p2card00.setDisable(false);

            p2card01.setVisible(false);

            Logger.info("{} drew a card.",GameData.getGamePlayer(1).getName());
        } else {
            Logger.info("Hand is full");
        }
        if (GameData.getGamePlayer(1).getCarddeck().getGameCards().isEmpty()) {
            Logger.info("Deck is empty");
            p2Deck.setVisible(false);
        }
    }

    /**
     * Handle player 1 deck.
     * @param actionEvent Pick 1 card from the deck , depends on how many card player 1 git in it hands.
     */
    public void p1DeckHandler(ActionEvent actionEvent) {
        System.out.println(GameData.getGamePlayer(0).getCarddeck().getGameCards().size());
       if (player1hand.getGameCards().size() == 1) {
            player1hand.getGameCards().add(getTopCardFromDeck(GameData.getGamePlayer(0).getCarddeck().getGameCards()));
            p1card00.setGraphic(new ImageView(player1hand.getGameCards().get(0).getCardImage()));
            p1card01.setGraphic(new ImageView(player1hand.getGameCards().get(1).getCardImage()));

            p1card01.setVisible(true);
            p1card00.setVisible(true);

           Logger.info("{} drew a card.",GameData.getGamePlayer(0).getName());
        } else if (player1hand.getGameCards().size() == 0) {
            player1hand.getGameCards().add(getTopCardFromDeck(GameData.getGamePlayer(0).getCarddeck().getGameCards()));
            p1card00.setGraphic(new ImageView(player1hand.getGameCards().get(0).getCardImage()));

            p1card00.setVisible(true);
            p1card01.setVisible(false);

           Logger.info("{} drew a card.",GameData.getGamePlayer(0).getName());
        } else {
            Logger.info("Hand is full");
        }
        if (GameData.getGamePlayer(0).getCarddeck().getGameCards().isEmpty()) {
            Logger.info("Deck is empty");
            p1Deck.setVisible(false);
        }
    }

    /**
     * Handle the first card of player 2 hand.
     * @param actionEvent Select the first card.
     */
    public void p2card00Handler(ActionEvent actionEvent) {
        if (!p2card00selected) {
            p2card01.setDisable(true);

            p1cardTableSlot00.setDisable(true);
            p1cardTableSlot01.setDisable(true);

            p1gup.setDisable(true);
            p2gup.setDisable(true);

            endturn.setDisable(true);

            p2spec.setDisable(true);
            p1spec.setDisable(true);

            p1Deck.setDisable(true);
            p2Deck.setDisable(true);

            p2card00selected = true;

        if (player2Table.getGameCards().isEmpty()) {
            p2cardTableSlot00.setVisible(true);
            p2cardTableSlot00.setDisable(false);

            p2cardTableSlot01.setDisable(true);
            p2cardTableSlot01.setVisible(false);
            }
        else if (player2Table.getGameCards().size() == 1) {
            p2cardTableSlot00.setDisable(true);
            p2cardTableSlot00.setVisible(true);

            p2cardTableSlot01.setDisable(false);
            p2cardTableSlot01.setVisible(true);
            } else {
            p2cardTableSlot00.setVisible(true);
            p2cardTableSlot00.setDisable(true);

            p2cardTableSlot01.setDisable(true);
            p2cardTableSlot01.setVisible(true);
            Logger.info("Table is full");
            }
             } else {
            p2card01.setDisable(false);

            p1cardTableSlot00.setDisable(false);
            p1cardTableSlot01.setDisable(false);

            p1gup.setDisable(true);
            p2gup.setDisable(false);

            endturn.setDisable(false);

            p2spec.setDisable(false);
            p1spec.setDisable(true);

            p1Deck.setDisable(true);
            p2Deck.setDisable(true);

            p2card00selected = false;

         if (player2Table.getGameCards().isEmpty()) {
             p2cardTableSlot00.setVisible(false);
             p2cardTableSlot00.setDisable(true);

             p2cardTableSlot01.setDisable(true);
             p2cardTableSlot01.setVisible(false);
            } else if (player2Table.getGameCards().size() == 1) {
             p2cardTableSlot00.setDisable(false);
             p2cardTableSlot00.setVisible(true);

             p2cardTableSlot01.setDisable(true);
             p2cardTableSlot01.setVisible(false);
            } else {
             p2cardTableSlot00.setVisible(true);
             p2cardTableSlot00.setDisable(false);

             p2cardTableSlot01.setDisable(false);
             p2cardTableSlot01.setVisible(true);
            }
        }
    }

    /**
     * Handle the second card of player 2 hand.
     * @param actionEvent Select the second card.
     */
    public void p2card01Handler(ActionEvent actionEvent) {
        if (!p2card01selected) {
            p2card00.setDisable(true);

            p1cardTableSlot00.setDisable(true);
            p1cardTableSlot01.setDisable(true);

            p1gup.setDisable(true);
            p2gup.setDisable(true);

            endturn.setDisable(true);

            p2spec.setDisable(true);
            p1spec.setDisable(true);

            p1Deck.setDisable(true);
            p2Deck.setDisable(true);

            p2card01selected = true;

        if (player2Table.getGameCards().isEmpty()) {
            p2cardTableSlot00.setVisible(true);
            p2cardTableSlot00.setDisable(false);

            p2cardTableSlot01.setVisible(false);
            p2cardTableSlot01.setDisable(true);
        } else if (player2Table.getGameCards().size() == 1) {
            p2cardTableSlot00.setDisable(true);
            p2cardTableSlot00.setVisible(true);

            p2cardTableSlot01.setDisable(false);
            p2cardTableSlot01.setVisible(true);
            } else {
            p2cardTableSlot00.setDisable(true);
            p2cardTableSlot00.setVisible(true);

            p2cardTableSlot01.setDisable(true);
            p2cardTableSlot01.setVisible(true);
            Logger.info("Table is full");
            }
        } else {
            p2card00.setDisable(false);

            p1cardTableSlot00.setDisable(false);
            p1cardTableSlot01.setDisable(false);

            p1gup.setDisable(true);
            p2gup.setDisable(false);

            endturn.setDisable(false);

            p2spec.setDisable(false);
            p1spec.setDisable(true);

            p1Deck.setDisable(true);
            p2Deck.setDisable(true);

            p2card01selected = false;

         if (player2Table.getGameCards().isEmpty()) {
            p2cardTableSlot00.setVisible(false);
            p2cardTableSlot00.setDisable(true);

            p2cardTableSlot01.setVisible(false);
            p2cardTableSlot01.setDisable(true);
         } else if (player2Table.getGameCards().size() == 1) {
             p2cardTableSlot00.setDisable(false);
             p2cardTableSlot00.setVisible(true);

             p2cardTableSlot01.setDisable(true);
             p2cardTableSlot01.setVisible(false);
         } else {
             p2cardTableSlot00.setDisable(false);
             p2cardTableSlot00.setVisible(true);

             p2cardTableSlot01.setVisible(true);
             p2cardTableSlot01.setDisable(false);
           }
        }
    }

    /**
     * Handle the first card of player 1 hand.
     * @param actionEvent Select the first card.
     */
    public void p1card01Handler(ActionEvent actionEvent) {
        if (!p1card01selected) {
            p1card00.setDisable(true);

            p2cardTableSlot00.setDisable(true);
            p2cardTableSlot01.setDisable(true);

            p1gup.setDisable(true);
            p2gup.setDisable(true);

            endturn.setDisable(true);

            p2spec.setDisable(true);
            p1spec.setDisable(true);

            p1Deck.setDisable(true);
            p2Deck.setDisable(true);

            p1card01selected = true;

            if (player1Table.getGameCards().isEmpty()) {
                p1cardTableSlot00.setVisible(true);
                p1cardTableSlot00.setDisable(false);

                p1cardTableSlot01.setVisible(false);
                p1cardTableSlot01.setDisable(true);
            } else if (player1Table.getGameCards().size() == 1) {
                p1cardTableSlot00.setDisable(true);
                p1cardTableSlot00.setVisible(true);

                p1cardTableSlot01.setDisable(false);
                p1cardTableSlot01.setVisible(true);
            } else {
                p1cardTableSlot00.setDisable(true);
                p1cardTableSlot00.setVisible(true);

                p1cardTableSlot01.setVisible(true);
                p1cardTableSlot01.setDisable(true);
                Logger.info("Table is full");
            }
        } else {
            p1card00.setDisable(false);

            p2cardTableSlot00.setDisable(false);
            p2cardTableSlot01.setDisable(false);

            p1gup.setDisable(false);
            p2gup.setDisable(true);

            endturn.setDisable(false);

            p2spec.setDisable(true);
            p1spec.setDisable(false);

            p1Deck.setDisable(true);
            p2Deck.setDisable(true);

            p1card01selected = false;

            if (player2Table.getGameCards().size() == 0) {
                p2cardTableSlot00.setDisable(true);
                p2cardTableSlot00.setVisible(false);

                p2cardTableSlot01.setDisable(true);
                p2cardTableSlot01.setVisible(false);
            } else if (player2Table.getGameCards().size() == 1) {
                p2cardTableSlot00.setDisable(false);
                p2cardTableSlot00.setVisible(true);

                p2cardTableSlot01.setDisable(true);
                p2cardTableSlot01.setVisible(false);
            } else {
                p2cardTableSlot00.setDisable(false);
                p2cardTableSlot00.setVisible(true);

                p2cardTableSlot01.setVisible(true);
                p2cardTableSlot01.setDisable(false);
            }

            if (player1Table.getGameCards().isEmpty()) {
                p1cardTableSlot00.setVisible(false);
                p1cardTableSlot00.setDisable(true);

                p1cardTableSlot01.setVisible(false);
                p1cardTableSlot01.setDisable(true);
            } else if (player1Table.getGameCards().size() == 1) {
                p1cardTableSlot00.setDisable(false);
                p1cardTableSlot00.setVisible(true);

                p1cardTableSlot01.setDisable(true);
                p1cardTableSlot01.setVisible(false);
            } else {
                p1cardTableSlot00.setDisable(false);
                p1cardTableSlot00.setVisible(true);

                p1cardTableSlot01.setVisible(true);
                p1cardTableSlot01.setDisable(false);
            }
        }
    }

    /**
     * Handle the second card of player 1 hand.
     * @param actionEvent Select the card.
     */
    public void p1card00Handler(ActionEvent actionEvent) {
        if (!p1card00selected) {
            p1card01.setDisable(true);

            p2cardTableSlot00.setDisable(true);
            p2cardTableSlot01.setDisable(true);

            p1gup.setDisable(true);
            p2gup.setDisable(true);

            endturn.setDisable(true);

            p2spec.setDisable(true);
            p1spec.setDisable(true);

            p1Deck.setDisable(true);
            p2Deck.setDisable(true);

            p1card00selected = true;
            if (player1Table.getGameCards().isEmpty()) {
                p1cardTableSlot00.setDisable(false);
                p1cardTableSlot00.setVisible(true);

                p1cardTableSlot01.setDisable(true);
                p1cardTableSlot01.setVisible(false);
            } else if (player1Table.getGameCards().size() == 1) {
                p1cardTableSlot00.setDisable(true);
                p1cardTableSlot00.setVisible(true);

                p1cardTableSlot01.setDisable(false);
                p1cardTableSlot01.setVisible(true);
            } else {
                p1cardTableSlot00.setDisable(true);
                p1cardTableSlot00.setVisible(true);

                p1cardTableSlot00.setVisible(true);
                p1cardTableSlot01.setDisable(true);
                Logger.info("Table is full");
            }
        } else {
            p1card01.setDisable(false);

            p2cardTableSlot00.setDisable(false);
            p2cardTableSlot01.setDisable(false);

            p1gup.setDisable(false);
            p2gup.setDisable(true);

            endturn.setDisable(false);

            p2spec.setDisable(true);
            p1spec.setDisable(false);

            p1Deck.setDisable(true);
            p2Deck.setDisable(true);
            if (player1Table.getGameCards().isEmpty()) {
                p1cardTableSlot00.setDisable(false);
                p1cardTableSlot00.setVisible(false);

                p1cardTableSlot01.setDisable(true);
                p1cardTableSlot01.setVisible(false);
            } else if (player1Table.getGameCards().size() == 1) {
                p1cardTableSlot00.setDisable(false);
                p1cardTableSlot00.setVisible(true);

                p1cardTableSlot01.setDisable(false);
                p1cardTableSlot01.setVisible(false);
            } else {
                p1cardTableSlot00.setDisable(false);
                p1cardTableSlot00.setVisible(true);

                p1cardTableSlot00.setVisible(true);
                p1cardTableSlot01.setDisable(false);
                Logger.info("Table is full");
            }

            p1card00selected = false;

            if (player2Table.getGameCards().size() == 0) {
                p2cardTableSlot00.setDisable(true);
                p2cardTableSlot00.setVisible(false);

                p2cardTableSlot01.setDisable(true);
                p2cardTableSlot01.setVisible(false);
            } else if (player2Table.getGameCards().size() == 1) {
                p2cardTableSlot00.setDisable(false);
                p2cardTableSlot00.setVisible(true);

                p2cardTableSlot01.setDisable(true);
                p2cardTableSlot01.setVisible(false);
            } else {
                p2cardTableSlot00.setDisable(false);
                p2cardTableSlot00.setVisible(true);

                p2cardTableSlot01.setVisible(true);
                p2cardTableSlot01.setDisable(false);
            }
        }
    }

    /**
     * Handle the first slot of player 2 on the table, and the attack/defense move.
     * @param actionEvent Select the first slot.
     */
    public void p2cardTableSlot00Handler(ActionEvent actionEvent) {

        if (p2card00selected) {
            GameCard card = getCardFromDeck(player2hand.getGameCards(), 0);
            player2Table.getGameCards().add(card);
            p2cardTableSlot00.setGraphic(new ImageView(card.getCardImage()));

            p2card00.setDisable(false);
            p2card01.setDisable(false);

            if (player2hand.getGameCards().size() == 1) {
            p2card01.setVisible(false);

            p2card00.setGraphic(new ImageView(player2hand.getGameCards().get(0).getCardImage()));
            } else if (player2hand.getGameCards().size() == 0) {
            p2card00.setVisible(false);
            p2card01.setVisible(false);
            } else {
            Logger.error("Negative card in hand");
            }
            if (player1Table.getGameCards().size() == 0) {
            p1cardTableSlot00.setDisable(true);
            p1cardTableSlot00.setVisible(false);

            p1cardTableSlot01.setVisible(false);
            p1cardTableSlot01.setDisable(true);
            } else if (player1Table.getGameCards().size() == 1) {
            p1cardTableSlot00.setDisable(false);
            p1cardTableSlot00.setVisible(true);

            p1cardTableSlot01.setVisible(false);
            p1cardTableSlot01.setDisable(true);
            } else {
            p1cardTableSlot00.setVisible(true);
            p1cardTableSlot00.setDisable(false);

            p1cardTableSlot01.setVisible(true);
            p1cardTableSlot01.setDisable(false);
            }
            p2Deck.setDisable(true);

            p2card00selected = false;

            p2cardTableSlot01.setDisable(false);

            endturn.setDisable(false);

            p2gup.setDisable(false);

            p2spec.setDisable(false);

            Logger.info("Player {} summoned a card.",GameData.getGamePlayer(1).getName());
        } else if (p2card01selected)
            {
            GameCard card = getCardFromDeck(player2hand.getGameCards(), 1);
            player2Table.getGameCards().add(card);
            p2cardTableSlot00.setGraphic(new ImageView(card.getCardImage()));

            p2card00.setDisable(false);
            p2card01.setDisable(false);
            if (player2hand.getGameCards().size() == 1) {
                p2card00.setDisable(false);
                p2card01.setVisible(false);
                p2card00.setGraphic(new ImageView(player2hand.getGameCards().get(0).getCardImage()));
            } else if (player2hand.getGameCards().size() == 0) {
                p2card00.setVisible(false);
                p2card01.setVisible(false);
            } else {
                Logger.error("Negative card in hand");
            }
            if (player1Table.getGameCards().size() == 0) {
                p1cardTableSlot00.setDisable(true);
                p1cardTableSlot00.setVisible(false);

                p1cardTableSlot01.setVisible(false);
                p1cardTableSlot01.setDisable(true);
            } else if (player1Table.getGameCards().size() == 1) {
                p1cardTableSlot00.setDisable(false);
                p1cardTableSlot00.setVisible(true);

                p1cardTableSlot01.setVisible(false);
                p1cardTableSlot01.setDisable(true);
            } else {
                p1cardTableSlot00.setDisable(false);
                p1cardTableSlot00.setVisible(true);

                p1cardTableSlot01.setVisible(true);
                p1cardTableSlot01.setDisable(false);
            }

            p2card01selected = false;

            p2cardTableSlot01.setDisable(false);

            endturn.setDisable(false);

            p2gup.setDisable(false);

            p2spec.setDisable(false);

            Logger.info("Player {} summoned a card.",GameData.getGamePlayer(1).getName());

        } else {
            if (p1cardTableSlot00selected) {
                int attack1, attack2;
                int defense1, defense2;
                int damage1, damage2;

                GameCard card = getCardFromDeck(player1Table.getGameCards(), 0);
                attack1 = card.getCardAttackPoint();
                GameCard card2 = getCardFromDeck(player2Table.getGameCards(), 0);
                defense1 = card2.getCardDefensePoint();
                damage1 = defense1 - attack1;
                GameData.getGamePlayer(1).setHealthpoint(GameData.getGamePlayer(1).getHealthpoint() + damage1);
                p2Lifepoints.setText(String.valueOf(GameData.getGamePlayer(1).getHealthpoint()));

                attack2 = card2.getCardAttackPoint();
                defense2 = card.getCardDefensePoint();
                damage2 = defense2 - attack2;
                GameData.getGamePlayer(0).setHealthpoint(GameData.getGamePlayer(0).getHealthpoint() + damage2);
                p1Lifepoints.setText(String.valueOf(GameData.getGamePlayer(0).getHealthpoint()));

                p1gup.setDisable(false);

                endturn.setDisable(false);

                p1spec.setDisable(false);

                p2card00.setDisable(false);
                p2card01.setDisable(false);

                p1cardTableSlot01.setDisable(false);

                p1card00.setDisable(false);
                p1card01.setDisable(false);

                p1cardTableSlot00selected = false;

                refreshTableGUI();

                Logger.info("Players attack each other");
            } else if (p1cardTableSlot01selected) {
                int attack1, attack2;
                int defense1, defense2;
                int damage1, damage2;

                GameCard card = getCardFromDeck(player1Table.getGameCards(), 1);
                attack1 = card.getCardAttackPoint();
                System.out.println(card);
                GameCard card2 = getCardFromDeck(player2Table.getGameCards(), 0);
                defense1 = card2.getCardDefensePoint();
                damage1 = defense1 - attack1;
                System.out.println(card2);
                GameData.getGamePlayer(1).setHealthpoint(GameData.getGamePlayer(1).getHealthpoint() + damage1);
                p2Lifepoints.setText(String.valueOf(GameData.getGamePlayer(1).getHealthpoint()));

                attack2 = card2.getCardAttackPoint();
                defense2 = card.getCardDefensePoint();
                damage2 = defense2 - attack2;
                GameData.getGamePlayer(0).setHealthpoint(GameData.getGamePlayer(0).getHealthpoint() + damage2);
                p1Lifepoints.setText(String.valueOf(GameData.getGamePlayer(0).getHealthpoint()));

                p1gup.setDisable(false);

                endturn.setDisable(false);

                p1spec.setDisable(false);

                p2card00.setDisable(false);
                p2card01.setDisable(false);

                p1cardTableSlot00.setDisable(false);

                p1card00.setDisable(false);
                p1card01.setDisable(false);


                p1cardTableSlot01selected = false;

                refreshTableGUI();

                Logger.info("Players attack each other");
            } else if (GameData.getTurn() == 0) {
                if (!p2cardTableSlot00selected) {
                    p2gup.setDisable(true);
                    endturn.setDisable(true);
                    p2spec.setDisable(true);
                    p2Deck.setDisable(true);

                    p1card00.setDisable(true);
                    p1card01.setDisable(true);

                    p2cardTableSlot01.setDisable(true);

                    p2card00.setDisable(true);
                    p2card01.setDisable(true);

                    p2cardTableSlot00selected = true;
                } else {
                    p2gup.setDisable(false);
                    endturn.setDisable(false);
                    p2spec.setDisable(false);

                    p1card00.setDisable(false);
                    p1card01.setDisable(false);

                    p2cardTableSlot01.setDisable(false);

                    p2card00.setDisable(false);
                    p2card01.setDisable(false);

                    p2cardTableSlot00selected = false;
                }
            }
        }
        checkPlayerHealth();
    }

    /**
     * Handle the second slot of player 2 on the table, and the attack/defense move.
     * @param actionEvent Select the second slot.
     */
    public void p2cardTableSlot01Handler(ActionEvent actionEvent) {
        if (p2card00selected) {
            GameCard card = getCardFromDeck(player2hand.getGameCards(), 0);
            player2Table.getGameCards().add(card);
            p2cardTableSlot01.setGraphic(new ImageView(card.getCardImage()));

            p2card00.setDisable(false);
            p2card01.setDisable(false);

            if (player2hand.getGameCards().size() == 1) {
                p2card01.setVisible(false);

                p2card00.setGraphic(new ImageView(player2hand.getGameCards().get(0).getCardImage()));
            } else if (player2hand.getGameCards().size() == 0) {
                p2card00.setVisible(false);

                p2card01.setVisible(false);
            } else {
                Logger.error("Negative card in hand");
            }
            if (player1Table.getGameCards().size() == 0) {
                p1cardTableSlot00.setDisable(true);

                p1cardTableSlot01.setDisable(true);
            } else if (player1Table.getGameCards().size() == 1) {
                p1cardTableSlot00.setDisable(false);

                p1cardTableSlot01.setDisable(true);
            } else {
                p1cardTableSlot00.setDisable(false);
                p1cardTableSlot01.setDisable(false);
            }

            p2card00selected = false;

            p2cardTableSlot00.setDisable(false);

            endturn.setDisable(false);

            p2gup.setDisable(false);

            p2spec.setDisable(false);

            Logger.info("Player {} summoned a card.",GameData.getGamePlayer(1).getName());
        } else if (p2card01selected)
        {
            GameCard card = getCardFromDeck(player2hand.getGameCards(), 1);
            player2Table.getGameCards().add(card);
            p2cardTableSlot01.setGraphic(new ImageView(card.getCardImage()));

            p2card00.setDisable(false);
            p2card01.setDisable(false);
            if (player2hand.getGameCards().size() == 1) {
                p2card00.setDisable(false);
                p2card01.setVisible(false);

                p2card00.setGraphic(new ImageView(player2hand.getGameCards().get(0).getCardImage()));
            } else if (player2hand.getGameCards().size() == 0) {
                p2card00.setVisible(false);

                p2card01.setVisible(false);
            } else {
                Logger.error("Negative card in hand");
            }
            if (player1Table.getGameCards().size() == 0) {
                p1cardTableSlot00.setDisable(true);

                p1cardTableSlot01.setDisable(true);
            } else if (player1Table.getGameCards().size() == 1) {
                p1cardTableSlot00.setDisable(false);

                p1cardTableSlot01.setDisable(true);
            } else {
                p1cardTableSlot00.setDisable(false);

                p1cardTableSlot01.setDisable(false);
            }

            p2card01selected = false;

            p2cardTableSlot00.setDisable(false);

            endturn.setDisable(false);

            p2gup.setDisable(false);

            p2spec.setDisable(false);
            Logger.info("Player {} summoned a card.",GameData.getGamePlayer(1).getName());
        } else {
            if (p1cardTableSlot00selected) {
                int attack1, attack2;
                int defense1, defense2;
                int damage1, damage2;
                GameCard card = getCardFromDeck(player1Table.getGameCards(), 0);
                attack1 = card.getCardAttackPoint();

                GameCard card2 = getCardFromDeck(player2Table.getGameCards(), 1);
                defense1 = card2.getCardDefensePoint();
                damage1 = defense1 - attack1;

                GameData.getGamePlayer(1).setHealthpoint(GameData.getGamePlayer(1).getHealthpoint() + damage1);
                p2Lifepoints.setText(String.valueOf(GameData.getGamePlayer(1).getHealthpoint()));

                attack2 = card2.getCardAttackPoint();
                defense2 = card.getCardDefensePoint();
                damage2 = defense2 - attack2;
                GameData.getGamePlayer(0).setHealthpoint(GameData.getGamePlayer(0).getHealthpoint() + damage2);
                p1Lifepoints.setText(String.valueOf(GameData.getGamePlayer(0).getHealthpoint()));

                p1gup.setDisable(false);

                endturn.setDisable(false);

                p1spec.setDisable(false);

                p2card00.setDisable(false);
                p2card01.setDisable(false);

                p1cardTableSlot01.setDisable(false);

                p1card00.setDisable(false);
                p1card01.setDisable(false);

                p1cardTableSlot00selected = false;

                refreshTableGUI();
                Logger.info("Players attack each other");
            } else if (p1cardTableSlot01selected) {
                int attack1, attack2;
                int defense1, defense2;
                int damage1, damage2;
                GameCard card = getCardFromDeck(player1Table.getGameCards(), 1);
                attack1 = card.getCardAttackPoint();

                GameCard card2 = getCardFromDeck(player2Table.getGameCards(), 1);
                defense1 = card2.getCardDefensePoint();
                damage1 = defense1 - attack1;

                GameData.getGamePlayer(1).setHealthpoint(GameData.getGamePlayer(1).getHealthpoint() + damage1);
                p2Lifepoints.setText(String.valueOf(GameData.getGamePlayer(1).getHealthpoint()));

                attack2 = card2.getCardAttackPoint();
                defense2 = card.getCardDefensePoint();
                damage2 = defense2 - attack2;
                GameData.getGamePlayer(0).setHealthpoint(GameData.getGamePlayer(0).getHealthpoint() + damage2);
                p1Lifepoints.setText(String.valueOf(GameData.getGamePlayer(0).getHealthpoint()));

                p1gup.setDisable(false);

                endturn.setDisable(false);

                p1spec.setDisable(false);

                p2card00.setDisable(false);
                p2card01.setDisable(false);

                p1cardTableSlot00.setDisable(false);

                p1card00.setDisable(false);
                p1card01.setDisable(false);

                p1cardTableSlot01selected = false;

                refreshTableGUI();
                Logger.info("Players attack each other");

            } else if (GameData.getTurn() == 0) {
                if (!p2cardTableSlot01selected) {

                    p2gup.setDisable(true);

                    endturn.setDisable(true);

                    p2spec.setDisable(true);

                    p2Deck.setDisable(true);

                    p1card00.setDisable(true);
                    p1card01.setDisable(true);

                    p2cardTableSlot00.setDisable(true);

                    p2card00.setDisable(true);
                    p2card01.setDisable(true);

                    p2cardTableSlot01selected = true;

                } else {
                    p2gup.setDisable(false);

                    endturn.setDisable(false);

                    p2spec.setDisable(false);

                    p1card00.setDisable(false);
                    p1card01.setDisable(false);

                    p2cardTableSlot00.setDisable(false);

                    p2card00.setDisable(false);
                    p2card01.setDisable(false);

                    p2cardTableSlot01selected = false;
                }
            }
        }
        checkPlayerHealth();
    }

    /**
     * Handle the second slot of player 1 on the table, and the attack/defense move.
     * @param actionEvent Select the second slot.
     */
    public void p1cardTableSlot01Handler(ActionEvent actionEvent) {
        if (p1card00selected) {
            GameCard card = getCardFromDeck(player1hand.getGameCards(), 0);
            player1Table.getGameCards().add(card);
            p1cardTableSlot01.setGraphic(new ImageView(card.getCardImage()));

            p1card00.setDisable(false);
            p1card01.setDisable(false);
            if (player1hand.getGameCards().size() == 1) {
                p1card01.setVisible(false);

                p1card00.setGraphic(new ImageView(player1hand.getGameCards().get(0).getCardImage()));
            } else if (player1hand.getGameCards().size() == 0) {
                p1card00.setVisible(false);

                p1card01.setVisible(false);
            } else {
                Logger.error("Negative card in hand");
            }
            if (player2Table.getGameCards().size() == 0) {
                p2cardTableSlot00.setDisable(true);

                p2cardTableSlot01.setDisable(true);
            } else if (player2Table.getGameCards().size() == 1) {
                p2cardTableSlot00.setDisable(false);

                p2cardTableSlot01.setDisable(true);
            } else {
                p2cardTableSlot00.setDisable(false);

                p2cardTableSlot01.setDisable(false);
            }

            p1card00selected = false;

            p2cardTableSlot00.setDisable(false);
            p2cardTableSlot01.setDisable(false);

            p1cardTableSlot00.setDisable(false);

            endturn.setDisable(false);

            p1gup.setDisable(false);

            p1spec.setDisable(false);

            Logger.info("Player {} summoned a card.",GameData.getGamePlayer(0).getName());
        } else if (p1card01selected)
        {
            GameCard card = getCardFromDeck(player1hand.getGameCards(), 1);
            player1Table.getGameCards().add(card);
            p1cardTableSlot01.setGraphic(new ImageView(card.getCardImage()));

            p1card00.setDisable(false);
            p1card01.setDisable(false);
            if (player1hand.getGameCards().size() == 1) {
                p1card00.setDisable(false);
                p1card01.setVisible(false);

                p1card00.setGraphic(new ImageView(player1hand.getGameCards().get(0).getCardImage()));
            } else if (player1hand.getGameCards().size() == 0) {
                p1card00.setVisible(false);

                p1card01.setVisible(false);
            } else {
                Logger.error("Negative card in hand");
            }
            if (player2Table.getGameCards().size() == 0) {
                p2cardTableSlot00.setDisable(true);

                p2cardTableSlot01.setDisable(true);
            } else if (player2Table.getGameCards().size() == 1) {
                p2cardTableSlot00.setDisable(false);

                p2cardTableSlot01.setDisable(true);
            } else {
                p2cardTableSlot00.setDisable(false);

                p2cardTableSlot01.setDisable(false);
            }

            p1card01selected = false;

            p1cardTableSlot00.setDisable(false);

            endturn.setDisable(false);

            p1gup.setDisable(false);

            p1spec.setDisable(false);

            Logger.info("Player {} summoned a card.",GameData.getGamePlayer(0).getName());
        } else {

            if (p2cardTableSlot00selected) {
                int attack1, attack2;
                int defense1, defense2;
                int damage1, damage2;

                GameCard card = getCardFromDeck(player2Table.getGameCards(), 0);
                attack1 = card.getCardAttackPoint();

                GameCard card2 = getCardFromDeck(player1Table.getGameCards(), 1);
                defense1 = card2.getCardDefensePoint();
                damage1 = defense1 - attack1;

                GameData.getGamePlayer(0).setHealthpoint(GameData.getGamePlayer(0).getHealthpoint() + damage1);
                p1Lifepoints.setText(String.valueOf(GameData.getGamePlayer(0).getHealthpoint()));

                attack2 = card2.getCardAttackPoint();
                defense2 = card.getCardDefensePoint();
                damage2 = defense2 - attack2;
                GameData.getGamePlayer(1).setHealthpoint(GameData.getGamePlayer(1).getHealthpoint() + damage2);
                p2Lifepoints.setText(String.valueOf(GameData.getGamePlayer(1).getHealthpoint()));

                p2gup.setDisable(false);
                endturn.setDisable(false);
                p2spec.setDisable(false);

                p1card00.setDisable(false);
                p1card01.setDisable(false);

                p2cardTableSlot00.setDisable(false);

                p2card00.setDisable(false);
                p2card01.setDisable(false);

                p2cardTableSlot00selected = false;

                refreshTableGUI();
                Logger.info("Players attack each other");

            } else if (p2cardTableSlot01selected) {
                int attack1, attack2;
                int defense1, defense2;
                int damage1, damage2;

                GameCard card = getCardFromDeck(player2Table.getGameCards(), 1);
                attack1 = card.getCardAttackPoint();

                GameCard card2 = getCardFromDeck(player1Table.getGameCards(), 1);
                defense1 = card2.getCardDefensePoint();
                damage1 = defense1 - attack1;

                GameData.getGamePlayer(0).setHealthpoint(GameData.getGamePlayer(0).getHealthpoint() + damage1);
                p1Lifepoints.setText(String.valueOf(GameData.getGamePlayer(0).getHealthpoint()));

                attack2 = card2.getCardAttackPoint();
                defense2 = card.getCardDefensePoint();
                damage2 = defense2 - attack2;
                GameData.getGamePlayer(1).setHealthpoint(GameData.getGamePlayer(1).getHealthpoint() + damage2);
                p2Lifepoints.setText(String.valueOf(GameData.getGamePlayer(1).getHealthpoint()));

                p2gup.setDisable(false);

                endturn.setDisable(false);

                p2spec.setDisable(false);

                p1card00.setDisable(false);
                p1card01.setDisable(false);

                p2cardTableSlot00.setDisable(false);

                p2card00.setDisable(false);
                p2card01.setDisable(false);

                p2cardTableSlot01selected = false;

                refreshTableGUI();
                Logger.info("Players attack each other");
            } else if (GameData.getTurn() == 1) {
                if (!p1cardTableSlot01selected) {
                    p1gup.setDisable(true);

                    endturn.setDisable(true);

                    p1spec.setDisable(true);

                    p1Deck.setDisable(true);

                    p2card00.setDisable(true);
                    p2card01.setDisable(true);

                    p1cardTableSlot00.setDisable(true);

                    p1card00.setDisable(true);
                    p1card01.setDisable(true);

                    p1cardTableSlot01selected = true;
                } else {
                    p1gup.setDisable(false);

                    endturn.setDisable(false);

                    p1spec.setDisable(false);

                    p2card00.setDisable(false);
                    p2card01.setDisable(false);

                    p1cardTableSlot00.setDisable(false);

                    p1card00.setDisable(false);
                    p1card01.setDisable(false);

                    p1cardTableSlot01selected = false;
                }
            }
        }
        checkPlayerHealth();
    }

    /**
     * Handle the first slot of player 1 on the table, and the attack/defense move.
     * @param actionEvent Select the first slot.
     */
    public void p1cardTableSlot00Handler(ActionEvent actionEvent) {
        if (p1card00selected) {
            GameCard card = getCardFromDeck(player1hand.getGameCards(), 0);
            player1Table.getGameCards().add(card);

            p1cardTableSlot00.setGraphic(new ImageView(card.getCardImage()));

            p1card00.setDisable(false);
            p1card01.setDisable(false);
            if (player1hand.getGameCards().size() == 1) {
                p1card01.setVisible(false);

                p1card00.setGraphic(new ImageView(player1hand.getGameCards().get(0).getCardImage()));
            } else if (player1hand.getGameCards().size() == 0) {
                p1card00.setVisible(false);

                p1card01.setVisible(false);
            } else {
                Logger.error("Negative card in hand");
            }
            if (player2Table.getGameCards().size() == 0) {
                p2cardTableSlot00.setDisable(true);

                p2cardTableSlot01.setDisable(true);
            } else if (player2Table.getGameCards().size() == 1) {
                p2cardTableSlot00.setDisable(false);

                p2cardTableSlot01.setDisable(true);
            } else {
                p2cardTableSlot00.setDisable(false);

                p2cardTableSlot01.setDisable(false);
            }
            p1Deck.setDisable(true);

            p1card00selected = false;

            p1cardTableSlot01.setDisable(true);

            endturn.setDisable(false);

            p1gup.setDisable(false);

            p1spec.setDisable(false);

            Logger.info("Player {} summoned a card.",GameData.getGamePlayer(0).getName());
        } else if (p1card01selected) {
            GameCard card = getCardFromDeck(player1hand.getGameCards(), 1);
            player1Table.getGameCards().add(card);

            p1cardTableSlot00.setGraphic(new ImageView(card.getCardImage()));

            p1card00.setDisable(false);
            p1card01.setDisable(false);
            if (player1hand.getGameCards().size() == 1) {
                p1card00.setDisable(false);
                p1card01.setVisible(false);

                p1card00.setGraphic(new ImageView(player1hand.getGameCards().get(0).getCardImage()));
            } else if (player1hand.getGameCards().size() == 0) {
                p1card00.setVisible(false);
                p1card01.setVisible(false);
            } else {
                Logger.error("Negative card in hand");
            }

            p2cardTableSlot00.setDisable(false);
            p2cardTableSlot01.setDisable(false);

            p1card01selected = false;

            if (player1Table.getGameCards().size() == 0) {
                p1cardTableSlot00.setDisable(true);

                p1cardTableSlot01.setDisable(true);
            } else if (player1Table.getGameCards().size() == 1) {
                p1cardTableSlot00.setDisable(false);

                p1cardTableSlot01.setDisable(true);
            } else {
                p1cardTableSlot00.setDisable(false);

                p1cardTableSlot01.setDisable(false);
            }
            if (player2Table.getGameCards().size() == 0) {
                p2cardTableSlot00.setDisable(true);

                p2cardTableSlot01.setDisable(true);
            } else if (player2Table.getGameCards().size() == 1) {
                p2cardTableSlot00.setDisable(false);

                p2cardTableSlot01.setDisable(true);
            } else {
                p2cardTableSlot00.setDisable(false);

                p2cardTableSlot01.setDisable(false);
            }
            p1cardTableSlot01.setDisable(false);

            endturn.setDisable(false);

            p1gup.setDisable(false);

            p1spec.setDisable(false);

            Logger.info("Player {} summoned a card.",GameData.getGamePlayer(0).getName());
        } else {
            if (p2cardTableSlot00selected) {
                int attack1, attack2;
                int defense1, defense2;
                int damage1, damage2;

                GameCard card = getCardFromDeck(player2Table.getGameCards(), 0);
                attack1 = card.getCardAttackPoint();

                GameCard card2 = getCardFromDeck(player1Table.getGameCards(), 0);
                defense1 = card2.getCardDefensePoint();
                damage1 = defense1 - attack1;

                GameData.getGamePlayer(0).setHealthpoint(GameData.getGamePlayer(0).getHealthpoint() + damage1);
                p1Lifepoints.setText(String.valueOf(GameData.getGamePlayer(0).getHealthpoint()));

                attack2 = card2.getCardAttackPoint();
                defense2 = card.getCardDefensePoint();
                damage2 = defense2 - attack2;
                GameData.getGamePlayer(1).setHealthpoint(GameData.getGamePlayer(1).getHealthpoint() + damage2);
                p2Lifepoints.setText(String.valueOf(GameData.getGamePlayer(1).getHealthpoint()));

                p2gup.setDisable(false);

                endturn.setDisable(false);

                p2spec.setDisable(false);

                p1card00.setDisable(false);
                p1card01.setDisable(false);

                p2cardTableSlot01.setDisable(false);

                p2card00.setDisable(false);
                p2card01.setDisable(false);

                p2cardTableSlot00selected = false;

                refreshTableGUI();
                Logger.info("Players attack each other");

            } else if (p2cardTableSlot01selected) {
                int attack1, attack2;
                int defense1, defense2;
                int damage1, damage2;
                GameCard card = getCardFromDeck(player2Table.getGameCards(), 1);
                attack1 = card.getCardAttackPoint();

                GameCard card2 = getCardFromDeck(player1Table.getGameCards(), 0);
                defense1 = card2.getCardDefensePoint();
                damage1 = defense1 - attack1;

                GameData.getGamePlayer(0).setHealthpoint(GameData.getGamePlayer(0).getHealthpoint() + damage1);
                p1Lifepoints.setText(String.valueOf(GameData.getGamePlayer(0).getHealthpoint()));

                attack2 = card2.getCardAttackPoint();
                defense2 = card.getCardDefensePoint();
                damage2 = defense2 - attack2;
                GameData.getGamePlayer(1).setHealthpoint(GameData.getGamePlayer(1).getHealthpoint() + damage2);
                p2Lifepoints.setText(String.valueOf(GameData.getGamePlayer(1).getHealthpoint()));

                p2gup.setDisable(false);

                endturn.setDisable(false);

                p2spec.setDisable(false);

                p1card00.setDisable(false);
                p1card01.setDisable(false);

                p2cardTableSlot00.setDisable(false);

                p2card00.setDisable(false);
                p2card01.setDisable(false);

                p2cardTableSlot01selected = false;

                refreshTableGUI();
                Logger.info("Players attack each other");
            } else if (GameData.getTurn() == 1) {
                if (!p1cardTableSlot00selected) {
                    p1gup.setDisable(true);

                    endturn.setDisable(true);

                    p1spec.setDisable(true);

                    p1Deck.setDisable(true);

                    p2card00.setDisable(true);
                    p2card01.setDisable(true);

                    p1cardTableSlot01.setDisable(true);

                    p1card00.setDisable(true);
                    p1card01.setDisable(true);

                    p1cardTableSlot00selected = true;

                } else {
                    p1gup.setDisable(false);

                    endturn.setDisable(false);

                    p1spec.setDisable(false);

                    p2card00.setDisable(false);
                    p2card01.setDisable(false);

                    p1cardTableSlot01.setDisable(false);

                    p1card00.setDisable(false);
                    p1card01.setDisable(false);

                    p1cardTableSlot00selected = false;
                }
            }
        }
        checkPlayerHealth();
    }

    /**
     * This method helps us to refresh the Graphic View of the table after one attack.
     * Checks if player 1 table is empty , or got 1 card on it or 2 card on it.
     * Checks if player 2 table is empty , or got 1 card on it or 2 card on it.
     */
    private void refreshTableGUI() {
        if (player1Table.getGameCards().size() == 0) {
            p1cardTableSlot00.setGraphic(null);
            p1cardTableSlot01.setGraphic(null);

            p1cardTableSlot00.setVisible(false);
            p1cardTableSlot01.setVisible(false);
        } else if (player1Table.getGameCards().size() == 1) {
            p1cardTableSlot00.setGraphic(new ImageView(player1Table.getGameCards().get(0).getCardImage()));
            p1cardTableSlot01.setGraphic(null);

            p1cardTableSlot00.setVisible(true);
            p1cardTableSlot01.setVisible(false);
        } else {
            p1cardTableSlot00.setGraphic(new ImageView(player1Table.getGameCards().get(0).getCardImage()));
            p1cardTableSlot01.setGraphic(new ImageView(player1Table.getGameCards().get(1).getCardImage()));

            p1cardTableSlot00.setVisible(true);
            p1cardTableSlot01.setVisible(true);
        }
        if (player2Table.getGameCards().size() == 0) {
            p2cardTableSlot00.setGraphic(null);
            p2cardTableSlot01.setGraphic(null);

            p2cardTableSlot00.setVisible(false);
            p2cardTableSlot01.setVisible(false);
        } else if (player2Table.getGameCards().size() == 1) {
            p2cardTableSlot00.setGraphic(new ImageView(player2Table.getGameCards().get(0).getCardImage()));
            p2cardTableSlot01.setGraphic(null);

            p2cardTableSlot00.setVisible(true);
            p2cardTableSlot01.setVisible(false);
        } else {
            p2cardTableSlot00.setGraphic(new ImageView(player2Table.getGameCards().get(0).getCardImage()));
            p2cardTableSlot01.setGraphic(new ImageView(player2Table.getGameCards().get(1).getCardImage()));

            p2cardTableSlot00.setVisible(false);
            p2cardTableSlot01.setVisible(false);
        }
    }
}


