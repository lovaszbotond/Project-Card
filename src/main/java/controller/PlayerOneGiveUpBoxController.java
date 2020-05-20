package controller;


import gameplay.GameData;
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
import java.io.IOException;

@Data
public class PlayerOneGiveUpBoxController {

    @FXML
    private Label p1guptext;

    @FXML
    public void initialize()
    {
        p1guptext.setText(GameData.getGamePlayer(0).getName() + " gave up.");
    }

    public void acceptHandler(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/mainmenu/mainmenu.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Project--card");
        stage.setResizable(false);
        stage.show();
        Logger.info("Player one: {} gave up.",GameData.getGamePlayer(0).getName());
    }
}
