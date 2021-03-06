package besino.gamebord;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

import static besino.gamebord.GameBord.FELD_GROESSE;

public class Spielstein extends StackPane {

    private SteinTyp steinTyp;
    private double mouseX, mouseY;
    private double oldX, oldY;

    Spielstein(SteinTyp steinTyp, int x, int y){
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
                createEllipse(Color.LIGHTBLUE);
                break;
            case DAMESCHWARZ:
                createEllipse(Color.GREEN);
                break;
        }


        setOnMousePressed( event -> {
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
        });

        setOnMouseDragged( event -> relocate(event.getSceneX() - mouseX + oldX, event.getSceneY() - mouseY + oldY));

    }

    void move(int x, int y){
        oldX = x * FELD_GROESSE;
        oldY = y * FELD_GROESSE;
        relocate(oldX,oldY);
    }

    public SteinTyp getSteinTyp(){
        return steinTyp;
    }

    double getOldX(){
        return oldX;
    }

    public int getX(){
        return (int)(getOldX()/FELD_GROESSE);
    }

    double getOldY(){
        return oldY;
    }

    public int getY(){
        return (int) (getOldY()/FELD_GROESSE);
    }

    void doNotMove(){ relocate(oldX,oldY);}

    private void createEllipse(Color color){
        Ellipse ellipse = new Ellipse(FELD_GROESSE * 0.3124, FELD_GROESSE * 0.3124);
        ellipse.setFill(color);
        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(FELD_GROESSE * 0.03);

        ellipse.setTranslateX((FELD_GROESSE - (FELD_GROESSE*0.3124*2))/2);
        ellipse.setTranslateY((FELD_GROESSE - (FELD_GROESSE*0.3124*2))/2);

        getChildren().addAll(ellipse);
    }

    public boolean istWeiss (){
        if (this.getSteinTyp() == SteinTyp.WEISS || (this.getSteinTyp() == SteinTyp.DAMEWEISS)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean istSchwarz (){
        if (this.getSteinTyp() == SteinTyp.SCHWARZ || (this.getSteinTyp() == SteinTyp.DAMESCHWARZ)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean istDameWeiss(){
        if (this.getSteinTyp() == SteinTyp.DAMEWEISS){
            return true;
        }
        else{
            return false;
        }
    }

}
