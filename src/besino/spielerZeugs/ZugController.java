package besino.spielerZeugs;

public class ZugController {
    private boolean istvscomputer = false;
    private boolean schwarzistdran;

    public ZugController(Player spieler1, Player spieler2){

        if (spieler2.getPlayertype() == PlayerType.COMPUTER){
            this.istvscomputer = true;
        }
        this.schwarzistdran = true;
    }

    public boolean getTurn(){
        return schwarzistdran;
    }

    public void changeTurn(){
      this.schwarzistdran = !schwarzistdran;
    }

    public boolean vsComputer(){ return istvscomputer; }
}
