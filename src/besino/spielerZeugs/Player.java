package besino.spielerZeugs;

public class Player {

    private PlayerType playertype;

    public Player(PlayerType playertype){
        this.playertype = playertype;
    }

    public PlayerType getPlayertype() {
        return playertype;
    }

}
