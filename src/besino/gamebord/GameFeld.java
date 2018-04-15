package besino.gamebord;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameFeld extends Rectangle {

    private Spielstein spielstein;

    public GameFeld(boolean farbe, int x, int y) {
        setWidth(GameBord.FELD_GROESSE);
        setHeight(GameBord.FELD_GROESSE);
        relocate(x * GameBord.FELD_GROESSE, y * GameBord.FELD_GROESSE);

        setFill(farbe ? Color.valueOf("#005500") : Color.valueOf("FF00FF"));
    }

    public boolean hatStein(){
        return spielstein != null;
    }

    public Spielstein getSpielstein(){
        return spielstein;
    }

    public void setSpielstein(Spielstein spielstein){
        this.spielstein = spielstein;
    }
}
