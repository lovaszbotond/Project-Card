package gameplay;

import gameplay.gamecards.CardDeck;
import lombok.Builder;
import lombok.Data;

/**
 * This Class describe/represents a specific player from the game.
 */
@Builder
@Data
public class GamePlayer {
    private String name;

    private int healthpoint;

    private CardDeck carddeck;

    private SpecialSkill special;
}
