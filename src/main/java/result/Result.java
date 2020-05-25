package result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Data class that represents {@code HighScore} objects stored in the save file.
 * */
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private String playerName1;
    private String playerName2;
    private int player1HealthPoint;
    private int player2HealthPoint;
    private String gameState;
}