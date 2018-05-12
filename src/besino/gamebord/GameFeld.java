package besino.gamebord;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class GameFeld extends Rectangle {

    private Spielstein spielstein;

    GameFeld(boolean farbe, int x, int y) {
        setWidth(GameBord.FELD_GROESSE);
        setHeight(GameBord.FELD_GROESSE);
        relocate(x * GameBord.FELD_GROESSE, y * GameBord.FELD_GROESSE);

        setFill(farbe ? Color.valueOf("#000000") : Color.valueOf("FF00FF"));
    }

    boolean hatStein(){
        return spielstein != null;
    }

    Spielstein getSpielstein(){
        return spielstein;
    }

    void setSpielstein(Spielstein spielstein){
        this.spielstein = spielstein;
    }
}
