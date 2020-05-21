package controller;

import gameplay.GameData;


import java.io.IOException;
import java.sql.Time;
import java.util.Collection;


import javafx.animation.KeyValue;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
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
    private Button p1gup,p2gup;
    @FXML
    private Button p2spec,p1spec;
    @FXML
    private Button p2card00,p2card01,p1card00,p1card01;
    @FXML
    private Button p1Deck,p2Deck;
    @FXML
    private Button endturn;

    /*
    private Timeline animation;
    private int temp = 60;
    private String s = "";

    private void timeLabel()
    {
        if(temp >=0)
        {
            temp--;
        }
        s = temp + "";
        p1timer.setText(s);
    }*/


    @FXML
    public void initialize() {


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


        /*p1timer = new Label("60");
        animation = new Timeline(new KeyFrame(Duration.seconds(1), e -> timeLabel()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();*/

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

}

