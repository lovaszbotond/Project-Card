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
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lombok.Data;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.tinylog.Logger;
import utillities.FileHelper;
import xmlhelper.JAXBHelper;

import javax.xml.bind.JAXBException;

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

    private CardDeck player1hand , player2hand;
    private CardDeck player1Table , player2Table;

    //TODO -> mindet beallitani false ertekre az initen belul
    private boolean p2card00selected , p2card01selected , p1card00selected , p1card01selected;
    private boolean p2cardTableSlot00selected , p2cardTableSlot01selected , p1cardTableSlot00selected, p1cardTableSlot01selected;



    @FXML
    public void initialize() throws JAXBException, IOException {


        if(!FileHelper.doesSaveFileExist())
        {
            FileHelper.createSaveFile();
        }


        start1 = System.currentTimeMillis();
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            long millisElapsed = System.currentTimeMillis() - start1;
            p1timer.setText(DurationFormatUtils.formatDuration(millisElapsed, "mm:ss"));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

        // TODO -> stop if end button is pressed , and set the player 1 timeline maximum value

        start2 = System.currentTimeMillis();
        Timeline clock2 = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            long millisElapsed = System.currentTimeMillis() - start2;
            p2timer.setText(DurationFormatUtils.formatDuration(millisElapsed, "mm:ss"));
        }), new KeyFrame(Duration.seconds(1)));
        clock2.setCycleCount(Animation.INDEFINITE);
        clock2.play();

        p2gup.setDisable(true);
        p2gup.setDisable(true);
        p2spec.setDisable(true);
        p2card00.setVisible(false);
        p2card01.setVisible(false);
        p2Deck.setDisable(true);
        // TODO -> stop if end button is pressed , and set the player 2 timeline maximum value

        playeronenameslot.setText(GameData.getGamePlayer(0).getName());
        playertwonameslot.setText(GameData.getGamePlayer(1).getName());

        GameData.setTurn(0);
        //special button
        p1Lifepoints.setText(String.valueOf(GameData.getGamePlayer(0).getHealthpoint()));
        p2Lifepoints.setText(String.valueOf(GameData.getGamePlayer(1).getHealthpoint()));


        //set card 1 2
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


    }


    public void checkPlayerHealth()
    {
        if(GameData.getGamePlayer(0).getHealthpoint() <= 0)
        {
            endGame();
        }else if(GameData.getGamePlayer(1).getHealthpoint() <= 0);
        {
            endGame();
        }
    }

    private void endGame() {
        boolean player1 = GameData.getGamePlayer(0).getHealthpoint() <= 0;
        boolean player2 = GameData.getGamePlayer(1).getHealthpoint() <= 0;
    }

    public void PlayerOneGupHandler(ActionEvent actionEvent) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/gameplay/p1gup.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Never Give Up");
        stage.show();
        Logger.info("Player one : {} want to give up.", GameData.getGamePlayer(0).getName());
    }

    public void PlayerTwoGupHandler(ActionEvent actionEvent) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/gameplay/p2gup.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Never Give Up");
        stage.show();
        Logger.info("Player two : {} want to give up.", GameData.getGamePlayer(1).getName());
    }

    public void endTurnHandler(MouseEvent mouseEvent) {
        if (GameData.getTurn() == 0) {
            Logger.info("{} turn.", GameData.getGamePlayer(0).getName());
            GameData.setTurn(1);
            p1gup.setDisable(false);
            p2gup.setDisable(true);
            p1spec.setDisable(false);
            p2spec.setDisable(true);
            p2Deck.setDisable(true);
            p1Deck.setDisable(false);
            p2card00.setVisible(false);
            p1card00.setVisible(true);
            p2card01.setVisible(false);
            p1card01.setVisible(true);

            if(player1hand.getGameCards().size() == 0)
            {
                p1card00.setVisible(false);
                p1card01.setVisible(false);
            }else if (player1hand.getGameCards().size() == 1)
            {
                p1card01.setVisible(false);
            }else
            {
                p1card00.setVisible(true);
                p1card01.setVisible(true);
            }
            // itt ujítottam
            if(player1Table.getGameCards().size() == 0)
            {
                p1cardTableSlot00.setDisable(false);
                p1cardTableSlot01.setDisable(false);
            }else if(player1Table.getGameCards().size() == 1)
            {
                p1cardTableSlot00.setDisable(false);
                p1cardTableSlot01.setDisable(true);
            }else
            {
                Logger.info("problem");
            }
            // p1 view of endturn ez lehet nem kell mert lehet a tamadashoz fog kelleni
            if(player2Table.getGameCards().size() == 0)
            {
                p2cardTableSlot00.setDisable(true);
                p2cardTableSlot01.setDisable(true);
            }else if(player2Table.getGameCards().size() == 1)
            {
                p2cardTableSlot00.setDisable(false);
                p2cardTableSlot01.setDisable(true);
            }else
            {
                p2cardTableSlot00.setDisable(false);
                p2cardTableSlot00.setDisable(false);
            }

        } else {
            Logger.info("{} turn.", GameData.getGamePlayer(1).getName());
            GameData.setTurn(0);
            p2gup.setDisable(false);
            p1gup.setDisable(true);
            p2spec.setDisable(false);
            p1spec.setDisable(true);
            p2Deck.setDisable(false);
            p1Deck.setDisable(true);
            p2card00.setVisible(true);
            p1card00.setVisible(false);
            p2card01.setVisible(true);
            p1card01.setVisible(false);
            // player 2 view of endturn
            if(player2hand.getGameCards().size() == 0)
            {
                p2card00.setVisible(false);
                p2card01.setVisible(false);
            }else if (player2hand.getGameCards().size() == 1)
            {
                p2card01.setVisible(false);
            }else
            {
                p2card00.setVisible(true);
                p2card01.setVisible(true);
            }
            // itt ujítottam
            if(player2Table.getGameCards().size() == 0)
            {
                p2cardTableSlot00.setDisable(false);
                p2cardTableSlot01.setDisable(false);
            }else if(player2Table.getGameCards().size() == 1)
            {
                p2cardTableSlot00.setDisable(false);
                p2cardTableSlot01.setDisable(true);
            }else
            {
                p2cardTableSlot00.setDisable(false);
                p2cardTableSlot01.setDisable(false);
            }
            // p2 view of endturn ez lehet nem kell mert tamadasnal fog inkabb jol jonni
            if(player1Table.getGameCards().size() == 0)
            {
                p1cardTableSlot00.setDisable(true);
                p1cardTableSlot01.setDisable(true);
            }else if(player1Table.getGameCards().size() == 1)
            {
                p1cardTableSlot00.setDisable(false);
                p1cardTableSlot01.setDisable(true);
            }else
            {
                p1cardTableSlot00.setDisable(false);
                p1cardTableSlot00.setDisable(false);
            }

        }
    }


    public void p1specHandler(ActionEvent actionEvent) {
        GameData.getGamePlayer(0).setHealthpoint(GameData.getGamePlayer(0).getHealthpoint() + 10);
        p1Lifepoints.setText(String.valueOf(GameData.getGamePlayer(0).getHealthpoint()));
        gridp1.getChildren().remove(p1spec);
    }

    public void p2specHandler(ActionEvent actionEvent) {
        GameData.getGamePlayer(1).setHealthpoint(GameData.getGamePlayer(1).getHealthpoint() + 10);
        p2Lifepoints.setText(String.valueOf(GameData.getGamePlayer(1).getHealthpoint()));
        gridp2.getChildren().remove(p2spec);
    }

    public GameCard getTopCardFromDeck(List<GameCard> deck) {
        GameCard card = deck.get(0);
        deck.remove(0);
        return card;
    }

    public GameCard getCardFromDeck(List<GameCard> deck, int index) {
        GameCard card = deck.get(index);
        deck.remove(index);
        return card;
    }

    public void p2DeckHandler(ActionEvent actionEvent) {
        if (player2hand.getGameCards().size() == 1) {
            player2hand.getGameCards().add(getTopCardFromDeck(GameData.getGamePlayer(1).getCarddeck().getGameCards()));
            p2card00.setGraphic(new ImageView(player2hand.getGameCards().get(0).getCardImage()));
            p2card01.setGraphic(new ImageView(player2hand.getGameCards().get(1).getCardImage()));
            p2card01.setVisible(true);
            p2card00.setVisible(true);
        } else if (player2hand.getGameCards().size() == 0) {
            player2hand.getGameCards().add(getTopCardFromDeck(GameData.getGamePlayer(1).getCarddeck().getGameCards()));
            p2card00.setGraphic(new ImageView(player2hand.getGameCards().get(0).getCardImage()));
            p2card00.setVisible(true);
            p2card01.setVisible(false);

        } else {
            Logger.info("Hand is full");
        }
    }

    public void p1DeckHandler(ActionEvent actionEvent) {
        if (player1hand.getGameCards().size() == 1) {
            player1hand.getGameCards().add(getTopCardFromDeck(GameData.getGamePlayer(1).getCarddeck().getGameCards()));
            p1card00.setGraphic(new ImageView(player1hand.getGameCards().get(0).getCardImage()));
            p1card01.setGraphic(new ImageView(player1hand.getGameCards().get(1).getCardImage()));
            p1card01.setVisible(true);
            p1card00.setVisible(true);
        } else if (player1hand.getGameCards().size() == 0) {
            player1hand.getGameCards().add(getTopCardFromDeck(GameData.getGamePlayer(1).getCarddeck().getGameCards()));
            p1card00.setGraphic(new ImageView(player1hand.getGameCards().get(0).getCardImage()));
            p1card00.setVisible(true);
            p1card01.setVisible(false);

        } else {
            Logger.info("Hand is full");
        }
    }

    public void p2card00Handler(ActionEvent actionEvent)
    {
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

         if(player2Table.getGameCards().isEmpty())
         {
            p2cardTableSlot01.setDisable(true);
         }else if(player2Table.getGameCards().size() == 1)
         {
             p2cardTableSlot00.setDisable(true);
             p2cardTableSlot01.setDisable(false);
         }else
         {
             p2cardTableSlot01.setDisable(true);
             p2cardTableSlot00.setDisable(true);
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
             p2cardTableSlot00.setDisable(false);
             p2cardTableSlot01.setDisable(false);

        p2card00selected = false;

        }

    }
    public void p2card01Handler (ActionEvent actionEvent)
    {
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

            if(player2Table.getGameCards().isEmpty())
            {
                p2cardTableSlot01.setDisable(true);
            }else if(player2Table.getGameCards().size() == 1)
            {
                p2cardTableSlot00.setDisable(true);
                p2cardTableSlot01.setDisable(false);
            }else
            {
                p2cardTableSlot00.setDisable(true);
                p2cardTableSlot01.setDisable(true);
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

            p2cardTableSlot01.setDisable(false);
            p2cardTableSlot00.setDisable(false);
        }

    }

      public void p1card01Handler (ActionEvent actionEvent)
      {
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

              if(player1Table.getGameCards().isEmpty())
              {
                  p1cardTableSlot01.setDisable(true);
              }else if(player1Table.getGameCards().size() == 1)
              {
                  p1cardTableSlot00.setDisable(true);
                  p1cardTableSlot01.setDisable(false);
              }else
              {
                  p1cardTableSlot00.setDisable(true);
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
                p1cardTableSlot01.setDisable(false);
                p1cardTableSlot00.setDisable(false);


                p1card01selected = false;

              //ujitass
              if(player2Table.getGameCards().size() == 0)
              {
                  p2cardTableSlot00.setDisable(true);
                  p2cardTableSlot01.setDisable(true);
              }else if(player2Table.getGameCards().size() == 1)
              {
                  p2cardTableSlot00.setDisable(false);
                  p2cardTableSlot01.setDisable(true);
              }else
              {
                  p2cardTableSlot00.setDisable(false);
                  p2cardTableSlot00.setDisable(false);
              }



          }

      }

       public void p1card00Handler (ActionEvent actionEvent)
        {
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

                if(player1Table.getGameCards().isEmpty())
                {
                    p1cardTableSlot01.setDisable(true);
                }else if(player1Table.getGameCards().size() == 1)
                {
                    p1cardTableSlot00.setDisable(true);
                    p1cardTableSlot01.setDisable(false);
                }else {
                    p1cardTableSlot01.setDisable(true);
                    p1cardTableSlot00.setDisable(true);
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
              p1cardTableSlot01.setDisable(false);
              p1cardTableSlot00.setDisable(false);

              p1card00selected = false;
                //ujitas
                if(player2Table.getGameCards().size() == 0)
                {
                    p2cardTableSlot00.setDisable(true);
                    p2cardTableSlot01.setDisable(true);
                }else if(player2Table.getGameCards().size() == 1)
                {
                    p2cardTableSlot00.setDisable(false);
                    p2cardTableSlot01.setDisable(true);
                }else
                {
                    p2cardTableSlot00.setDisable(false);
                    p2cardTableSlot00.setDisable(false);
                }


            }
        }


    public void p2cardTableSlot00Handler(ActionEvent actionEvent) {

        if (p2card00selected) {
            GameCard card = getCardFromDeck(player2hand.getGameCards(),0);
            player2Table.getGameCards().add(card);
            p2cardTableSlot00.setGraphic(new ImageView(card.getCardImage()));
            p2card00.setDisable(false);
            p2card01.setDisable(false);
            if(player2hand.getGameCards().size() == 1)
            {
                p2card01.setVisible(false);
                p2card00.setGraphic(new ImageView(player2hand.getGameCards().get(0).getCardImage()));
            }else if(player2hand.getGameCards().size() == 0)
            {
                p2card00.setVisible(false);
                p2card01.setVisible(false);
            }else
            {
                Logger.error("Problem");
            }
            if(player1Table.getGameCards().size() == 0)
            {
                p1cardTableSlot00.setDisable(true);
                p1cardTableSlot01.setDisable(true);
            }else if(player1Table.getGameCards().size() == 1)
            {
                p1cardTableSlot00.setDisable(false);
                p1cardTableSlot01.setDisable(true);
            }else {
                p1cardTableSlot00.setDisable(false);
                p1cardTableSlot01.setDisable(false);
            }

            p2Deck.setDisable(true);

            p2card00selected = false;

            p2cardTableSlot01.setDisable(false);
            endturn.setDisable(false);
            p2gup.setDisable(false);
            p2spec.setDisable(false);
        } else if (p2card01selected)
            //p2card01 van kivalasztva ... ha az sincs kivalasztva akkor tamado helyzet van és megkell neznunk hogy azt a kartyat tamadjak vagy az a kartya tamad
        {
            GameCard card = getCardFromDeck(player2hand.getGameCards(),1);
            player2Table.getGameCards().add(card);
            p2cardTableSlot00.setGraphic(new ImageView(card.getCardImage()));
            p2card00.setDisable(false);
            p2card01.setDisable(false);
            if(player2hand.getGameCards().size() == 1)
            {
                p2card00.setDisable(false);
                p2card01.setVisible(false);
                p2card00.setGraphic(new ImageView(player2hand.getGameCards().get(0).getCardImage()));
            }else if(player2hand.getGameCards().size() == 0)
            {
                p2card00.setVisible(false);
                p2card01.setVisible(false);
            }else
            {
                Logger.error("Problem");
            }
            if(player1Table.getGameCards().size() == 0)
            {
                p1cardTableSlot00.setDisable(true);
                p1cardTableSlot01.setDisable(true);
            }else if(player1Table.getGameCards().size() == 1)
            {
                p1cardTableSlot00.setDisable(false);
                p1cardTableSlot01.setDisable(true);
            }else {
                p1cardTableSlot00.setDisable(false);
                p1cardTableSlot01.setDisable(false);
            }

            p2card01selected = false;
            p2cardTableSlot01.setDisable(false);
            endturn.setDisable(false);
            p2gup.setDisable(false);
            p2spec.setDisable(false);
       }else
        {
            //def
          /*  if(p2cardTableSlot00selected)
            {
                int attack1,attack2;
                int defense1,defense2;
                int damage1,damage2;
                GameCard card = getCardFromDeck(player1Table.getGameCards(),0);
                attack1 = card.getCardAttackPoint();
                System.out.println(card);
                GameCard card2 = getCardFromDeck(player2Table.getGameCards(),0);
                defense1 = card2.getCardDefensePoint();
                damage1 = defense1 - attack1;
                System.out.println(card2);
                GameData.getGamePlayer(1).setHealthpoint(GameData.getGamePlayer(1).getHealthpoint() + damage1);
                p1Lifepoints.setText(String.valueOf(GameData.getGamePlayer(0).getHealthpoint()));

                attack2 = card2.getCardAttackPoint();
                defense2 = card.getCardDefensePoint();
                damage2 = defense2 - attack2;
                GameData.getGamePlayer(0).setHealthpoint(GameData.getGamePlayer(0).getHealthpoint() + damage2);
                p2Lifepoints.setText(String.valueOf(GameData.getGamePlayer(1).getHealthpoint()));


                //levesszuk a szurkitest
                p1gup.setDisable(false);
                endturn.setDisable(false);
                p1spec.setDisable(false);
                p2card00.setDisable(false);
                p2card01.setDisable(false);
                p1cardTableSlot01.setDisable(false);
                p1card00.setDisable(false);
                p1card01.setDisable(false);


                p1cardTableSlot00selected = false;

                //
            }else if(p2cardTableSlot01selected) {
            }

           */
        }




    }

    public void p2cardTableSlot01Handler(ActionEvent actionEvent) {
        if (p2card00selected) {
            GameCard card = getCardFromDeck(player2hand.getGameCards(),0);
            player2Table.getGameCards().add(card);
            p2cardTableSlot01.setGraphic(new ImageView(card.getCardImage()));
            p2card00.setDisable(false);
            p2card01.setDisable(false);
            if(player2hand.getGameCards().size() == 1)
            {
                p2card01.setVisible(false);
                p2card00.setGraphic(new ImageView(player2hand.getGameCards().get(0).getCardImage()));
            }else if(player2hand.getGameCards().size() == 0)
            {
                p2card00.setVisible(false);
                p2card01.setVisible(false);
            }else
            {
                Logger.error("Problem");
            }
            if(player1Table.getGameCards().size() == 0)
            {
                p1cardTableSlot00.setDisable(true);
                p1cardTableSlot01.setDisable(true);
            }else if(player1Table.getGameCards().size() == 1)
            {
                p1cardTableSlot00.setDisable(false);
                p1cardTableSlot01.setDisable(true);
            }else {
                p1cardTableSlot00.setDisable(false);
                p1cardTableSlot01.setDisable(false);
            }

            p2card00selected = false;
            p2cardTableSlot00.setDisable(false);
            endturn.setDisable(false);
            p2gup.setDisable(false);
            p2spec.setDisable(false);
        } else if (p2card01selected)
        //p2card01 van kivalasztva ... ha az sincs kivalasztva akkor tamado helyzet van és megkell neznunk hogy azt a kartyat tamadjak vagy az a kartya tamad
        {
            GameCard card = getCardFromDeck(player2hand.getGameCards(),1);
            player2Table.getGameCards().add(card);
            p2cardTableSlot01.setGraphic(new ImageView(card.getCardImage()));
            p2card00.setDisable(false);
            p2card01.setDisable(false);
            if(player2hand.getGameCards().size() == 1)
            {
                p2card00.setDisable(false);
                p2card01.setVisible(false);
                p2card00.setGraphic(new ImageView(player2hand.getGameCards().get(0).getCardImage()));
            }else if(player2hand.getGameCards().size() == 0)
            {
                p2card00.setVisible(false);
                p2card01.setVisible(false);
            }else
            {
                Logger.error("Problem");
            }
            if(player1Table.getGameCards().size() == 0)
            {
                p1cardTableSlot00.setDisable(true);
                p1cardTableSlot01.setDisable(true);
            }else if(player1Table.getGameCards().size() == 1)
            {
                p1cardTableSlot00.setDisable(false);
                p1cardTableSlot01.setDisable(true);
            }else {
                p1cardTableSlot00.setDisable(false);
                p1cardTableSlot01.setDisable(false);
            }


            p2card01selected = false;
            p2cardTableSlot00.setDisable(false);
            endturn.setDisable(false);
            p2gup.setDisable(false);
            p2spec.setDisable(false);
        }else
        {
            //TODO attack , def
        }
    }

    public void p1cardTableSlot01Handler(ActionEvent actionEvent) {
        if (p1card00selected) {
            GameCard card = getCardFromDeck(player1hand.getGameCards(),0);
            player1Table.getGameCards().add(card);
            p1cardTableSlot01.setGraphic(new ImageView(card.getCardImage()));
            p1card00.setDisable(false);
            p1card01.setDisable(false);
            //   p2cardTableSlot00.setDisable(false);
            //   p2cardTableSlot01.setDisable(false);
            if(player1hand.getGameCards().size() == 1)
            {
                p1card01.setVisible(false);
                p1card00.setGraphic(new ImageView(player1hand.getGameCards().get(0).getCardImage()));
            }else if(player1hand.getGameCards().size() == 0)
            {
                p1card00.setVisible(false);
                p1card01.setVisible(false);
            }else
            {
                Logger.error("Problem");
            }
            // itt van az ujitas
            if(player2Table.getGameCards().size() == 0)
            {
                p2cardTableSlot00.setDisable(true);
                p2cardTableSlot01.setDisable(true);
            }else if(player2Table.getGameCards().size() == 1)
            {
                p2cardTableSlot00.setDisable(false);
                p2cardTableSlot01.setDisable(true);
            }else
            {
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
        } else if(p1card01selected)
        //p2card01 van kivalasztva ... ha az sincs kivalasztva akkor tamado helyzet van és megkell neznunk hogy azt a kartyat tamadjak vagy az a kartya tamad
        {
            GameCard card = getCardFromDeck(player1hand.getGameCards(),1);
            player1Table.getGameCards().add(card);
            p1cardTableSlot01.setGraphic(new ImageView(card.getCardImage()));
            p1card00.setDisable(false);
            p1card01.setDisable(false);
            if(player1hand.getGameCards().size() == 1)
            {
                p1card00.setDisable(false);
                p1card01.setVisible(false);
                p1card00.setGraphic(new ImageView(player1hand.getGameCards().get(0).getCardImage()));
            }else if(player1hand.getGameCards().size() == 0)
            {
                p1card00.setVisible(false);
                p1card01.setVisible(false);
            }else
            {
                Logger.error("Problem");
            }
            if(player2Table.getGameCards().size() == 0)
            {
                p2cardTableSlot00.setDisable(true);
                p2cardTableSlot01.setDisable(true);
            }else if(player2Table.getGameCards().size() == 1)
            {
                p2cardTableSlot00.setDisable(false);
                p2cardTableSlot01.setDisable(true);
            }else
            {
                p2cardTableSlot00.setDisable(false);
                p2cardTableSlot01.setDisable(false);
            }

            p1card01selected = false;
            p1cardTableSlot00.setDisable(false);
            endturn.setDisable(false);
            p1gup.setDisable(false);
            p1spec.setDisable(false);
        }else
        {
            //TODO attack , def
        }
    }

    public void p1cardTableSlot00Handler(ActionEvent actionEvent) {
        if (p1card00selected) {
            GameCard card = getCardFromDeck(player1hand.getGameCards(),0);
            player1Table.getGameCards().add(card);
            p1cardTableSlot00.setGraphic(new ImageView(card.getCardImage()));
            p1card00.setDisable(false);
            p1card01.setDisable(false);

            if(player1hand.getGameCards().size() == 1)
            {
                p1card01.setVisible(false);
                p1card00.setGraphic(new ImageView(player1hand.getGameCards().get(0).getCardImage()));
            }else if(player1hand.getGameCards().size() == 0)
            {
                p1card00.setVisible(false);
                p1card01.setVisible(false);
            }else
            {
                Logger.error("Problem");
            }
            if(player2Table.getGameCards().size() == 0)
            {
                p2cardTableSlot00.setDisable(true);
                p2cardTableSlot01.setDisable(true);
            }else if(player2Table.getGameCards().size() == 1)
            {
                p2cardTableSlot00.setDisable(false);
                p2cardTableSlot01.setDisable(true);
            }else
            {
                p2cardTableSlot00.setDisable(false);
                p2cardTableSlot01.setDisable(false);
            }

            p1Deck.setDisable(true);

            p1card00selected = false;

            p1cardTableSlot01.setDisable(true);
            endturn.setDisable(false);
            p1gup.setDisable(false);
            p1spec.setDisable(false);

        } else if (p1card01selected)
        {
            GameCard card = getCardFromDeck(player1hand.getGameCards(),1);
            player1Table.getGameCards().add(card);
            p1cardTableSlot00.setGraphic(new ImageView(card.getCardImage()));
            p1card00.setDisable(false);
            p1card01.setDisable(false);
            if(player1hand.getGameCards().size() == 1)
            {
                p1card00.setDisable(false);
                p1card01.setVisible(false);
                p1card00.setGraphic(new ImageView(player1hand.getGameCards().get(0).getCardImage()));
            }else if(player1hand.getGameCards().size() == 0)
            {
                p1card00.setVisible(false);
                p1card01.setVisible(false);
            }else
            {
                Logger.error("Problem");
            }
            p2cardTableSlot00.setDisable(false);
            p2cardTableSlot01.setDisable(false);

            p1card01selected = false;

            if(player1Table.getGameCards().size() == 0)
            {
                p1cardTableSlot00.setDisable(true);
                p1cardTableSlot01.setDisable(true);
            }else if(player1Table.getGameCards().size() == 1)
            {
                p1cardTableSlot00.setDisable(false);
                p1cardTableSlot01.setDisable(true);
            }else
            {
                p1cardTableSlot00.setDisable(false);
                p1cardTableSlot00.setDisable(false);
            }
            if(player2Table.getGameCards().size() == 0)
            {
                p2cardTableSlot00.setDisable(true);
                p2cardTableSlot01.setDisable(true);
            }else if(player2Table.getGameCards().size() == 1)
            {
                p2cardTableSlot00.setDisable(false);
                p2cardTableSlot01.setDisable(true);
            }else
            {
                p2cardTableSlot00.setDisable(false);
                p2cardTableSlot01.setDisable(false);
            }

            p1cardTableSlot01.setDisable(false);
            endturn.setDisable(false);
            p1gup.setDisable(false);
            p1spec.setDisable(false);
        }else
        {
            //tamadas if
            if(!p1cardTableSlot00selected)
            {

                //megfelelo szurkites
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
            }else
            {
                // megfelelo szurkites
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


   // private int attack ;
   // private int defense;
  //  private int damage;

    public void tesztelek ()
    {
        int attack1,attack2;
        int defense1,defense2;
        int damage1,damage2;

        GameCard card = getCardFromDeck(player1Table.getGameCards(),0);
        attack1 = card.getCardAttackPoint();
        GameCard card2 = getCardFromDeck(player2Table.getGameCards(),0);
        defense1 = card2.getCardDefensePoint();
        damage1 = defense1 - attack1;
        GameData.getGamePlayer(0).setHealthpoint(GameData.getGamePlayer(0).getHealthpoint() + damage1);
        p2Lifepoints.setText(String.valueOf(GameData.getGamePlayer(0).getHealthpoint()));

        attack2 = card2.getCardAttackPoint();
        defense2 = card.getCardDefensePoint();
        damage2 = defense2 - attack2;
        GameData.getGamePlayer(1).setHealthpoint(GameData.getGamePlayer(1).getHealthpoint() + damage2);
        p1Lifepoints.setText(String.valueOf(GameData.getGamePlayer(1).getHealthpoint()));
    }


/* Ez akkor lesz ha majd tamadunk ->
if(p2cardTableSlot00selected)
            {
                p2cardTableSlot00selected = false;
            }else
            {
                GameCard card = getCardFromDeck(player2hand.getGameCards(),0);
                p2cardTableSlot00.setGraphic(new ImageView(card.getCardImage()));
                p2cardTableSlot00selected = true;

            }
           */
}

    //p2card00.setGraphic(new ImageView(player2hand.getGameCards().get(0).getCardImage()));
    //p2card01.setGraphic(new ImageView(player2hand.getGameCards().get(1).getCardImage()));


