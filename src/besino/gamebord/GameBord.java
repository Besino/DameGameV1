package besino.gamebord;

import besino.spielerZeugs.Player;
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
    private Player player1;
    private Player player2;

    public GameBord(Player player1, Player player2){

        this.player1 = player1;
        this.player2 = player2;

        setUpSpiel();
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
                case WANDLEDAMEWEISS:
                    spielstein.move(newX,newY);
                    brett[x0][y0].setSpielstein(null);
                    Spielstein damensteinweiss = new Spielstein(SteinTyp.DAMEWEISS,newX,newY);
                    brett[newX][newY].setSpielstein(damensteinweiss);
                    spielsteinGroup.getChildren().remove(spielstein);
                    spielsteinGroup.getChildren().add(damensteinweiss);
                    break;
                case KILLUNDWANDLEWEISS:
                    spielstein.move(newX,newY);
                    brett[x0][y0].setSpielstein(null);
                    Spielstein dameweiss = new Spielstein(SteinTyp.DAMEWEISS,newX,newY);
                    Spielstein gegnerStein2 = resultat.getSpielstein();
                    brett[zuBrett(gegnerStein2.getOldX())][zuBrett(gegnerStein2.getOldY())].setSpielstein(null);
                    brett[newX][newY].setSpielstein(dameweiss);
                    spielsteinGroup.getChildren().remove(spielstein);
                    spielsteinGroup.getChildren().add(dameweiss);
                    spielsteinGroup.getChildren().remove(gegnerStein2);
                    break;
                case WANDLEDAMESCHWARZ:
                    spielstein.move(newX,newY);
                    brett[x0][y0].setSpielstein(null);
                    Spielstein damensteinschwarz = new Spielstein(SteinTyp.DAMESCHWARZ,newX,newY);
                    brett[newX][newY].setSpielstein(damensteinschwarz);
                    spielsteinGroup.getChildren().remove(spielstein);
                    spielsteinGroup.getChildren().add(damensteinschwarz);
                    break;
                case KILLUNDWANDLESCHWARZ:
                    spielstein.move(newX,newY);
                    brett[x0][y0].setSpielstein(null);
                    Spielstein dameschwarz = new Spielstein(SteinTyp.DAMESCHWARZ,newX,newY);
                    Spielstein gegnerStein3 = resultat.getSpielstein();
                    brett[zuBrett(gegnerStein3.getOldX())][zuBrett(gegnerStein3.getOldY())].setSpielstein(null);
                    brett[newX][newY].setSpielstein(dameschwarz);
                    spielsteinGroup.getChildren().remove(spielstein);
                    spielsteinGroup.getChildren().add(dameschwarz);
                    spielsteinGroup.getChildren().remove(gegnerStein3);
                    break;

            }
        });

        return spielstein;
    }
    private ZugResultat tryMove(Spielstein spielstein, int newX, int newY){
        if(brett[newX][newY].hatStein() || (newX + newY) % 2 == 0){
            return new ZugResultat(ZugTyp.KEIN);
        }
        int x0 = zuBrett(spielstein.getOldX());
        int y0 = zuBrett(spielstein.getOldY());

        boolean istweiss = (spielstein.getSteinTyp() == SteinTyp.WEISS);
        boolean istschwarz = (spielstein.getSteinTyp() == SteinTyp.SCHWARZ);
        boolean normalzug = (Math.abs(newX-x0) == 1) && (newY-y0 == spielstein.getSteinTyp().moveDir);
        // normalzug für beide Steintypen für diagonales Bewegen in movedirektion für normale steine
        boolean normalwandlungsrestrict = (istweiss && (y0 != 6 )||istschwarz&&(y0 != 1));
        // verhindert letzten zug an Bande, damit sicherlich Damenwandlung ausgeführt wird.
        boolean killzug = Math.abs(newX-x0) == 2 && newY-y0 == spielstein.getSteinTyp().moveDir * 2;
        // Killzug für beide Steintypen für diagonales Killen in movedirektion
        boolean killzugrestrict = ((istweiss && (y0 != 5 )||istschwarz &&(y0 != 2)));

        if(normalzug && normalwandlungsrestrict){
            return new ZugResultat(ZugTyp.NORMAL);

        } else if(killzug && killzugrestrict){
            int x1 = x0 + (newX - x0)/2;
            int y1 = y0 + (newY - y0)/2;

            if(brett[x1][y1].hatStein() &&
                    brett[x1][y1].getSpielstein().getSteinTyp() != spielstein.getSteinTyp()){
                return new ZugResultat(ZugTyp.KILL,brett[x1][y1].getSpielstein());
            }
        }
        else if(istweiss && (y0 == 6) && normalzug){
            return new ZugResultat(ZugTyp.WANDLEDAMEWEISS);
        }

        else if(istschwarz && (y0 == 1) && normalzug){
            return new ZugResultat(ZugTyp.WANDLEDAMESCHWARZ);
        }

        else if(killzug && istweiss && (y0 ==5)){
                int x1 = x0 + (newX - x0)/2;
                int y1 = y0 + (newY - y0)/2;

                if(brett[x1][y1].hatStein() &&
                        brett[x1][y1].getSpielstein().getSteinTyp() != spielstein.getSteinTyp()) {
                    return new ZugResultat(ZugTyp.KILLUNDWANDLEWEISS, brett[x1][y1].getSpielstein());
                }
        }

        else if(killzug && istschwarz && (y0 ==2)){
            int x1 = x0 + (newX - x0)/2;
            int y1 = y0 + (newY - y0)/2;

            if(brett[x1][y1].hatStein() &&
                    brett[x1][y1].getSpielstein().getSteinTyp() != spielstein.getSteinTyp()) {
                return new ZugResultat(ZugTyp.KILLUNDWANDLESCHWARZ, brett[x1][y1].getSpielstein());
            }
        }
        return new ZugResultat(ZugTyp.KEIN);
    }

    private int zuBrett (double pixel){
        return (int)(pixel + FELD_GROESSE/2) / FELD_GROESSE;
    }

    private void setUpSpiel(){
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
    }
}
