package besino.spielerZeugs;

import besino.gamebord.GameBord;

public class ZugController {
    private boolean istvscomputer = false;
    private boolean schwarzistdran;
    private Computer computer;

    public ZugController(GameBord gameBord, Player spieler2){

        if (spieler2.getPlayertype() == PlayerType.COMPUTER){
            this.istvscomputer = true;
            computer = new Computer(gameBord);
        }

        this.schwarzistdran = true;
    }

    public boolean getTurn(){
        return schwarzistdran;
    }

    public void changeTurn(){
        this.schwarzistdran = !schwarzistdran;

        if (istvscomputer && !schwarzistdran){
            computer.spieleRandomZug();
        }

    }

    public boolean vsComputer(){ return istvscomputer; }
}
