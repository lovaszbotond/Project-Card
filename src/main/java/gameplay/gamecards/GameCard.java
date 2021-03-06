package gameplay.gamecards;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import lombok.*;

/**
 * Class describes/represents a card from the deck.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class GameCard
{
    private String cardName;
    private String cardImage;
    private int cardAttackPoint;
    private int cardDefensePoint;
}





