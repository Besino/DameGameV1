package besino.spielzugRules;

import besino.gamebord.Spielstein;

public class ZugComputer {

    int newX;
    int newY;
    int oldX;
    int oldY;
    Spielstein spielstein;

    public ZugComputer (Spielstein spielstein, int newX, int newY, int oldX, int oldY){
        this.spielstein = spielstein;
        this.newX = newX;
        this.newY = newY;
        this.oldX = oldX;
        this.oldY = oldY;
    }

    public int getNewX(){
        return newX;
    }

    public int getNewY(){
        return newY;
    }

    public int getOldX(){
        return oldX;
    }

    public int getOldY(){
        return oldY;
    }

    public Spielstein getSpielstein(){
        return spielstein;
    }
}
