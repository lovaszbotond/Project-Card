package utillities;

import gameplay.GamePlayer;
import org.tinylog.Logger;
import result.Result;
import result.Results;
import xmlhelper.JAXBHelper;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.ArrayList;

/**
 * Class for handling file operations.
 * */
public class FileHelper {
    /**
     * @param player1 Name of player 1.
     * @param player2 Name of player 2.
     * @throws IOException If there is something wrong during the file open procedure.
     * @throws JAXBException If something wrong with reading the file.
     */
    public static void savePlayerStats(GamePlayer player1, GamePlayer player2) throws IOException, JAXBException {
        String userHome = System.getProperty("user.home");
        String separator = File.separator;
        String saveFilePath = userHome + separator + ".Project--Card" + separator + "result.xml";
        Logger.info("Loading save file from " + saveFilePath);

        File saveFile = new File(saveFilePath);
        InputStream is = new FileInputStream(saveFile);
        Results results = JAXBHelper.fromXML(Results.class, is);
        is.close();
        if(results.getResults() == null)
        {
            results.setResults(new ArrayList<>());
        }

        String gameState;
        if(player1.getHealthpoint() <= 0 && player2.getHealthpoint() <= 0)
        {
            gameState = "Draw";
        }else if(player1.getHealthpoint() > player2.getHealthpoint())
        {
            gameState = player1.getName() + " Won";
        }else
        {
            gameState = player2.getName() + " Lose";
        }

        results.getResults().add(new Result(player1.getName(),player2.getName(), player1.getHealthpoint(),player2.getHealthpoint(),gameState));
        OutputStream os = new FileOutputStream(saveFile);
        JAXBHelper.toXML(results, os);
        os.close();
    }

    /**
     * Checks for existing save file in the file system.
     * @return Is the file exist or not
     * */
    public static boolean doesSaveFileExist()
    {
        Logger.info("Checking for save file");

        String userHome = System.getProperty("user.home");
        String separator = File.separator;
        String saveFilePath = userHome + separator + ".Project--Card" + separator + "result.xml";
        Logger.debug("Save file location: " + saveFilePath);
        File saveFile = new File(saveFilePath);
        return saveFile.exists();
    }

    /**
     * Creates save file.
     * @throws IOException If any problem occurs opening the save file.
     * @throws JAXBException If any problem occurs during serialization.
     * */
    public static void createSaveFile() throws IOException, JAXBException {
        Logger.info("Save file not found. Creating save file.");

        String userHome = System.getProperty("user.home");
        String separator = File.separator;
        String saveFilePath = userHome + separator + ".Project--Card" + separator + "result.xml";
        String saveFileDir = userHome + separator + ".Project--Card";
        new File(saveFileDir).mkdirs();
        OutputStream os = new FileOutputStream(saveFilePath);
        Results results = new Results();
        JAXBHelper.toXML(results, os);
        os.close();
    }
}