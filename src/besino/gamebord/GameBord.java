package besino.gamebord;

import besino.spielzugRules.ZugResultat;
import besino.spielzugRules.ZugTyp;
import javafx.scene.Group;
import javafx.scene.Parent;


public class GameBord extends Parent {

    public static final int FELD_GROESSE = 75;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    private Group gamefeldGroup = new Group();
    private Group spielsteinGroup = new Group();
    private GameFeld[][] brett = new GameFeld[WIDTH][HEIGHT];

    private ZugResultat tryMove(Spielstein spielstein, int newX, int newY){
        if(brett[newX][newY].hatStein() || (newX + newY) % 2 == 0){
            return new ZugResultat(ZugTyp.KEIN);
        }
        int x0 = zuBrett(spielstein.getOldX());
        int y0 = zuBrett(spielstein.getOldY());

        if(Math.abs(newX-x0) == 1 && newY-y0 == spielstein.getSteinTyp().moveDir){
            return new ZugResultat(ZugTyp.NORMAL);
        } else if(Math.abs(newX-x0) == 2 && newY-y0 == spielstein.getSteinTyp().moveDir * 2){
            int x1 = x0 + (newX - x0)/2;
            int y1 = y0 + (newY - y0)/2;
            if(brett[x1][y1].hatStein() &&
                    brett[x1][y1].getSpielstein().getSteinTyp() != spielstein.getSteinTyp()){
                return new ZugResultat(ZugTyp.KILL,brett[x1][y1].getSpielstein());
            }
        }
        return new ZugResultat(ZugTyp.KEIN);
    }

    private int zuBrett (double pixel){
        return (int)(pixel + FELD_GROESSE/2) / FELD_GROESSE;
    }

    public GameBord(){

        for (int y = 0; y < HEIGHT; y++){
            for (int x = 0; x < WIDTH; x++){
                GameFeld gameFeld = new GameFeld((x+y)%2==0,x,y);
                brett[x][y] = gameFeld;
                gamefeldGroup.getChildren().add(gameFeld);

                Spielstein spielstein = null;

                if(y <= 2 && (x+y)%2!=0){
                   spielstein = erstelleSpielstein(SteinTyp.WEISS,x,y);
                }

                if(y >= 5 && (x+y)%2!=0){
                    spielstein = erstelleSpielstein(SteinTyp.SCHWARZ,x,y);
                }
                if(spielstein != null) {
                    gameFeld.setSpielstein(spielstein);
                    spielsteinGroup.getChildren().add(spielstein);
                }
            }
        }
        getChildren().addAll(gamefeldGroup,spielsteinGroup);


    }

    private Spielstein erstelleSpielstein(SteinTyp type, int x, int y){
        Spielstein spielstein = new Spielstein(type, x, y);

        spielstein.setOnMouseReleased(event ->{
            int newX = zuBrett(spielstein.getLayoutX());
            int newY = zuBrett(spielstein.getLayoutY());

            ZugResultat resultat = tryMove(spielstein,newX,newY);

            int x0 = zuBrett(spielstein.getOldX());
            int y0 = zuBrett(spielstein.getOldY());

            switch (resultat.getZugTyp()){

                case KEIN:
                    spielstein.dontmove();
                    break;
                case NORMAL:
                    spielstein.move(newX,newY);
                    brett[x0][y0].setSpielstein(null);
                    brett[newX][newY].setSpielstein(spielstein);
                    break;
                case KILL:
                    spielstein.move(newX,newY);
                    brett[x0][y0].setSpielstein(null);
                    brett[newX][newY].setSpielstein(spielstein);

                    Spielstein gegnerStein = resultat.getSpielstein();
                    brett[zuBrett(gegnerStein.getOldX())][zuBrett(gegnerStein.getOldY())].setSpielstein(null);
                    spielsteinGroup.getChildren().remove(gegnerStein);
                    break;
            }
        });

        return spielstein;
    }
}
