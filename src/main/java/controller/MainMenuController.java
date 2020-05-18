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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/rulebox.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Rules");
        stage.show();
    }
    @FXML
    public void startHandler(ActionEvent actionEvent) throws IOException {
        if (p1Field.getText().isEmpty())
        {
            p1Error.setText("Please add name to Player one");
        }
        else {
            p1Error.setText("");
        }
        if (p2Field.getText().isEmpty())
        {
            p2Error.setText("Please add name to Player two");
        }
        else {
            p2Error.setText("");
        }
        if (!p1Field.getText().isEmpty() && !p2Field.getText().isEmpty())
        {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/gameplaytable.fxml"));
            Parent root = fxmlLoader.load();
            fxmlLoader.<GamePlayTableController>getController().fieldNames(p1Field.getText(), p2Field.getText());
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Project--card");
            stage.setResizable(true);
            stage.setMaximized(true);
            stage.show();
        }
    }
    @FXML
    public void exitHandler(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/exitquestion.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Exit");
        stage.show();
    }
}
