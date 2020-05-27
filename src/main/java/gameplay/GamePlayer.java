package gameplay;

import gameplay.gamecards.CardDeck;
import lombok.Builder;
import lombok.Data;

import java.util.Random;

/**
 * This Class describe/represents a specific player from the game.
 */
@Builder
@Data
public class GamePlayer {
    private String name;

    private int healthpoint;

    private CardDeck carddeck;

    /**
     * Choose randomly 10 or -10.
     * This method will decide if the player gets +10 health points , or loses -10 health points.
     */
    public void useSpecial()
    {
        healthpoint += new Random().nextInt(2) == 0 ? 10 : -10;
    }
}
