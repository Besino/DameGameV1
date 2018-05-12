package besino.spielerZeugs;

public class ZugController {
    Player spieler1;
    Player spieler2;
    boolean istvscomputer = false;
    boolean schwarzistdran;

    public ZugController(Player spieler1, Player spieler2){
        this.spieler1 = spieler1;
        this.spieler2 = spieler2;

        if (spieler2.getPlayertype() == PlayerType.COMPUTER){
            this.istvscomputer = true;
        }
        this.schwarzistdran = true;
    }

    public boolean getturn(){
        return schwarzistdran;
    }

    public void changeturn(){
        if (schwarzistdran == true) {
            this.schwarzistdran = false;
        }
        else {
            this.schwarzistdran = true;
        }
    }
}
