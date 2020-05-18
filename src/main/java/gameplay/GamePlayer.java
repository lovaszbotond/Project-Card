package gameplay;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GamePlayer {
    private String name;
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