package controller;

import gameplay.GameData;



import java.io.IOException;


import gameplay.gamecards.CardDeck;
import javafx.scene.control.Button;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
    private Label playeronenameslot,playertwonameslot;
    @FXML
    private Label p1Lifepoints,p2Lifepoints;
    @FXML
    private Button p1gup,p2gup;
    @FXML
    private Button p2spec,p1spec;
    @FXML
    private Button p2card00,p2card01,p1card00,p1card01;
    @FXML
    private Button p1Deck,p2Deck;
    @FXML
    private GridPane gridp1,gridp2;
    //  @FXML
   // private Button endturn;

    @FXML
    public void initialize() throws JAXBException {


        start1 = System.currentTimeMillis();
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            long millisElapsed = System.currentTimeMillis() - start1;
            p1timer.setText(DurationFormatUtils.formatDuration(millisElapsed,"mm:ss"));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

        // TODO -> stop if end button is pressed , and set the player 1 timeline maximum value

        start2 = System.currentTimeMillis();
        Timeline clock2 = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            long millisElapsed = System.currentTimeMillis() - start2;
            p2timer.setText(DurationFormatUtils.formatDuration(millisElapsed,"mm:ss"));
        }), new KeyFrame(Duration.seconds(1)));
        clock2.setCycleCount(Animation.INDEFINITE);
        clock2.play();

        p2gup.setDisable(true);
        p2gup.setDisable(true);
        p2spec.setDisable(true);
        p2card00.setDisable(true);
        p2card01.setDisable(true);
        p2Deck.setDisable(true);
        // TODO -> stop if end button is pressed , and set the player 2 timeline maximum value

        playeronenameslot.setText(GameData.getGamePlayer(0).getName());
        playertwonameslot.setText(GameData.getGamePlayer(1).getName());

        GameData.setTurn(0);

        p1Lifepoints.setText(String.valueOf(GameData.getGamePlayer(0).getHealthpoint()));
        p2Lifepoints.setText(String.valueOf(GameData.getGamePlayer(1).getHealthpoint()));
       // p1card00.setGraphic(new ImageView("/images/deckimages/cyrax.jpg"));


       //    JAXBHelper.fromXML(Class<T> , IS )
            // ez mar az egész a mappelés - jaxb helper hozza letre az objektumot az xml alapjan Carddeck a typus + valtozonec
            // deck1.getGameCards().get(1);


          CardDeck deck1 = JAXBHelper.fromXML(gameplay.gamecards.CardDeck.class ,  getClass().getClassLoader().getResourceAsStream("xml/deck1.xml"));
          p1card00.setGraphic(new ImageView(deck1.getGameCards().get(1).getCardImage()));


    }



    public void PlayerOneGupHandler(ActionEvent actionEvent) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/gameplay/p1gup.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Never Give Up");
        stage.show();
        Logger.info("Player one : {} want to give up.",GameData.getGamePlayer(0).getName());
    }

    public void PlayerTwoGupHandler(ActionEvent actionEvent) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/gameplay/p2gup.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Never Give Up");
        stage.show();
        Logger.info("Player two : {} want to give up.",GameData.getGamePlayer(1).getName());
    }

    public void endTurnHandler(MouseEvent mouseEvent) {
        if (GameData.getTurn() == 0) {
            Logger.info("{} turn.",GameData.getGamePlayer(0).getName());
            GameData.setTurn(1);
            p1gup.setDisable(false);
            p2gup.setDisable(true);
            p1spec.setDisable(false);
            p2spec.setDisable(true);
            p2Deck.setDisable(true);
            p1Deck.setDisable(false);
            p2card00.setDisable(true);
            p1card00.setDisable(false);
            p2card01.setDisable(true);
            p1card01.setDisable(false);
        } else {
            Logger.info("{} turn.",GameData.getGamePlayer(1).getName());
            GameData.setTurn(0);
            p2gup.setDisable(false);
            p1gup.setDisable(true);
            p2spec.setDisable(false);
            p1spec.setDisable(true);
            p2Deck.setDisable(false);
            p1Deck.setDisable(true);
            p2card00.setDisable(false);
            p1card00.setDisable(true);
            p2card01.setDisable(false);
            p1card01.setDisable(true);

        }
    }


    public void p1specHandler(ActionEvent actionEvent)
    {
        p1Lifepoints.setText(String.valueOf(GameData.getGamePlayer(0).getHealthpoint() + 10));
        gridp1.getChildren().remove(p1spec);
    }

    public void p2specHandler(ActionEvent actionEvent) {
        p2Lifepoints.setText(String.valueOf(GameData.getGamePlayer(0).getHealthpoint() + 10));
        gridp2.getChildren().remove(p2spec);
    }
}

