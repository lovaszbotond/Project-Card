package controller;

import gameplay.GameData;


import java.io.IOException;

import java.sql.PseudoColumnUsage;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

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
import org.apache.commons.lang.time.DurationFormatUtils;
import org.tinylog.Logger;


public class GamePlayTableController {

    @FXML
    private Label p1timer;
    private long start1;

    @FXML
    private Label p2timer;
    private long start2;



    private GameData gameData;
    public static String p1Name;
    public static String p2Name;

    public void fieldNames( String p1Name, String p2Name)
    {
        this.p1Name = p1Name;
        this.p2Name = p2Name;

        gameData.getGamePlayer(0).setName(p1Name);
        gameData.getGamePlayer(1).setName(p2Name);
    }

    @FXML
    public void initialize()
    {
        gameData = new GameData();

        start1 = System.currentTimeMillis();
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            long millisElapsed = System.currentTimeMillis() - start1;
            p1timer.setText(DurationFormatUtils.formatDuration(millisElapsed,"mm:ss"));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
        // TODO -> stop if enbutton is pressed , and set the player 1 timeline maximum value

        start2 = System.currentTimeMillis();
        Timeline clock2 = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            long millisElapsed = System.currentTimeMillis() - start2;
            p2timer.setText(DurationFormatUtils.formatDuration(millisElapsed,"mm:ss"));
        }), new KeyFrame(Duration.seconds(1)));
        clock2.setCycleCount(Animation.INDEFINITE);
        clock2.play();
        // TODO -> stop if enbutton is pressed , and set the player 2 timeline maximum value
    }




    public void PlayerOneGupHandler(ActionEvent actionEvent) throws IOException {

        // TODO -> only player 1 turn can use this

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/gameplay/p1gup.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Never Give Up");
        stage.show();
        Logger.info("Player one : {} want to give up.",p1Name);
    }

    public void PlayerTwoGupHandler(ActionEvent actionEvent) throws IOException {

        // TODO -> only player 2 turn can use this

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/gameplay/p2gup.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Never Give Up");
        stage.show();
        Logger.info("Player two : {} want to give up.",p2Name);
    }

}

