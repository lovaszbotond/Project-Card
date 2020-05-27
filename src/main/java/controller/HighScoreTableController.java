package controller;



import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import result.Result;
import result.Results;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.tinylog.Logger;
import xmlhelper.JAXBHelper;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This class responsible for the cell and column values of ResultTable.
 */
public class HighScoreTableController implements Initializable {

    @FXML
    private TableView<Result> resultTable;
    @FXML
    private TableColumn playerName1, playerName2, player1HealthPoint, player2HealthPoint,gameState;

    /**
     * Responsible for the view of ResultTable and read the '.xml' where we can store the high scores.
     * @param location The path of '.xml' file
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String userHome = System.getProperty("user.home");
        String separator = File.separator;
        String saveFilePath = userHome + separator + ".Project--Card" + separator + "result.xml";
        Logger.info("Loading -  " + saveFilePath);

        File saveFile = new File(saveFilePath);

        try {
            InputStream is = new FileInputStream(saveFile);
            Results results = JAXBHelper.fromXML(Results.class, is);

            //mely oszlo alapjan , mit szedjen össze
            playerName1.setCellValueFactory(new PropertyValueFactory<>("playerName1"));
            playerName2.setCellValueFactory(new PropertyValueFactory<>("playerName2"));
            player1HealthPoint.setCellValueFactory(new PropertyValueFactory<>("player1HealthPoint"));
            player2HealthPoint.setCellValueFactory(new PropertyValueFactory<>("player2HealthPoint"));
            gameState.setCellValueFactory(new PropertyValueFactory<>("gameState"));

            resultTable.getColumns().addAll(playerName1,playerName2,player1HealthPoint,player2HealthPoint,gameState);

            //beletoljuk a tömbbe
            for(Result result : results.getResults())
            {
                resultTable.getItems().add(result);
            }
        } catch (FileNotFoundException | JAXBException e) {
            e.printStackTrace();
            Logger.warn("File not found on this path.");
        }
    }

    /**
     * Handle Accept button.
     * @param actionEvent Navigate the user to tha main menu.
     * @throws IOException If the file not exist or miss spelled.
     */
    public void backToMainHandler(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/mainmenu/mainmenu.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Project--card");
        stage.show();
        Logger.info("Send me an email if you have some good ide about the Result design.");
    }
}

