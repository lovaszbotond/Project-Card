package gameplay;

import gameplay.gamecards.CardDeck;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GamePlayer {
    private String name;

    private int healthpoint;

    private CardDeck carddeck;

    private SpecialSkill special;


}
