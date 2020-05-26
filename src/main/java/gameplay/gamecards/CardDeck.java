package gameplay.gamecards;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.List;

/**
 * This Class represent the object of a CardDeck.
 */
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@Data
public class CardDeck {
    private List<GameCard> gameCards;
}
