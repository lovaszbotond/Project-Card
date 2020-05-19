package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;


public class MainMenuController {


    @FXML
    private TextField p1Field;
    @FXML
    private TextField p2Field;
    @FXML
    private  Label p1Error;
    @FXML
    private  Label p2Error;


    @FXML
    public void ruleHandler(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/mainmenu/rulebox.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Rules");
        stage.show();
        Logger.info("The users are check the rules.");
    }
    @FXML
    public void startHandler(ActionEvent actionEvent) throws IOException {
        if (p1Field.getText().isEmpty())
        {
            p1Error.setText("Please add name to Player one");
            Logger.error("Can not find the player one name in the field.");
        }
        else {
            p1Error.setText("");
            Logger.info("Player one set the name to {}.",p1Field.getText());
        }
        if (p2Field.getText().isEmpty())
        {
            p2Error.setText("Please add name to Player two");
            Logger.error("Can not find the player two name in the field.");
        }
        else {
            p2Error.setText("");
            Logger.info("Player two set the name to {}.",p2Field.getText());
        }
        if (!p1Field.getText().isEmpty() && !p2Field.getText().isEmpty())
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/gameplay/gameplaytable.fxml"));
            Parent root = fxmlLoader.load();
            fxmlLoader.<GamePlayTableController>getController().fieldNames(p1Field.getText(), p2Field.getText());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Project--card");
            stage.setResizable(false);
          //  stage.setMaximized(true);
            stage.show();
            Logger.info("Everything looks good , let start the duel and have fun!");
        }
    }
    @FXML
    public void exitHandler(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/mainmenu/exitquestion.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Exit");
        stage.show();
    }
}
