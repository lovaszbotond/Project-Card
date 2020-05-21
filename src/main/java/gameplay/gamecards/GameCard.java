package gameplay.gamecards;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import lombok.*;


@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameCard
{
    private String cardName;
    private String cardImage;
    private int cardAttackPoint;
    private int cardDefensePoint;
}





