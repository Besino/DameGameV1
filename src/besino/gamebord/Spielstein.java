package besino.gamebord;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import java.util.concurrent.CopyOnWriteArraySet;

import static besino.gamebord.GameBord.FELD_GROESSE;

public class Spielstein extends StackPane {

    private SteinTyp steinTyp;
    private double mouseX, mouseY;
    private double oldX, oldY;

    public Spielstein (SteinTyp steinTyp, int x, int y){
        this.steinTyp = steinTyp;

        move(x,y);

        switch(steinTyp){
            case WEISS:
                createEllipse(Color.WHITE);
                break;
            case SCHWARZ:
                createEllipse(Color.BLACK);
                break;
            case DAMEWEISS:
                createEllipse(Color.CORAL);
                break;
            case DAMESCHWARZ:
                createEllipse(Color.DARKGREY);
                break;
        }


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

    public void dontmove(){ relocate(oldX,oldY);}

    public void createEllipse(Color color){
        Ellipse ellipse = new Ellipse(FELD_GROESSE * 0.3124, FELD_GROESSE * 0.3124);
        ellipse.setFill(color);
        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(FELD_GROESSE * 0.03);

        ellipse.setTranslateX((FELD_GROESSE - (FELD_GROESSE*0.3124*2))/2);
        ellipse.setTranslateY((FELD_GROESSE - (FELD_GROESSE*0.3124*2))/2);

        getChildren().addAll(ellipse);
    }
}
