package besino.spielzugRules;

import besino.gamebord.Spielstein;

public class ZugComputer {

    int newX;
    int newY;
    Spielstein spielstein;

    public ZugComputer (Spielstein spielstein, int newX, int newY){
        this.spielstein = spielstein;
        this.newX = newX;
        this.newY = newY;
    }

    public int getNewX(){
        return newX;
    }

    public int getNewY(){
        return newY;
    }

    public Spielstein getSpielstein(){
        return spielstein;
    }
}
