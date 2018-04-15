package besino.gamebord;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import static besino.gamebord.GameBord.FELD_GROESSE;

public class Spielstein extends StackPane {

    private SteinTyp steinTyp;
    private double mouseX, mouseY;
    private double oldX, oldY;

    public Spielstein (SteinTyp steinTyp, int x, int y){
        this.steinTyp = steinTyp;

        move(x,y);

        Ellipse ellipse = new Ellipse(FELD_GROESSE * 0.3124, FELD_GROESSE * 0.3124);
        ellipse.setFill(steinTyp == SteinTyp.WEISS ? Color.valueOf("#fff9f4") : Color.valueOf("#000000"));
        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(FELD_GROESSE * 0.03);

        ellipse.setTranslateX((FELD_GROESSE - (FELD_GROESSE*0.3124*2))/2);
        ellipse.setTranslateY((FELD_GROESSE - (FELD_GROESSE*0.3124*2))/2);

        getChildren().addAll(ellipse);

        setOnMousePressed( event -> {
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
        });

        setOnMouseDragged( event -> {
            relocate(event.getSceneX() - mouseX + oldX, event.getSceneY() - mouseY + oldY);
        });

    }

    public void move(int x, int y){
        oldX = x * FELD_GROESSE;
        oldY = y * FELD_GROESSE;
        relocate(oldX,oldY);
    }

    public SteinTyp getSteinTyp(){
        return steinTyp;
    }

    public double getOldX(){
        return oldX;
    }

    public double getOldY(){
        return oldY;
    }

    public void dontmove(){
        relocate(oldX,oldY);
    }

}
