package besino.spielerZeugs;

public class ZugController {
    private Player spieler1;
    private Player spieler2;
    private boolean istvscomputer = false;
    private boolean schwarzistdran;

    public ZugController(Player spieler1, Player spieler2){
        this.spieler1 = spieler1;
        this.spieler2 = spieler2;

        if (spieler2.getPlayertype() == PlayerType.COMPUTER){
            this.istvscomputer = true;
        }
        this.schwarzistdran = true;
    }

    public boolean getTurn(){
        return schwarzistdran;
    }

    public void changeTurn(){
        if (schwarzistdran) {
            this.schwarzistdran = false;
        }
        else {
            this.schwarzistdran = true;
        }
    }
}
