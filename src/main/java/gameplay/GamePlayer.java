package gameplay;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GamePlayer {
    private String name;

   // private int healthpoint;

   // private Special specialeffect;

   // private CardDeck carddeck;

   // private HandCards handcard;

}

/*
public class player {
    public String Name;

    public void Player(String Name) {
        this.Name = Name;
    }

    public String Name() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getName() {
        return this.Name;
    }
}
 */