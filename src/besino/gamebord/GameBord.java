package besino.gamebord;

import besino.gamemenu.MenuButton;
import besino.spielerZeugs.Player;
import besino.spielerZeugs.ZugController;
import besino.spielzugRules.ZugResultat;
import besino.spielzugRules.ZugTyp;
import besino.winnermessage.WinnerMessageBox;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class GameBord extends Parent {

    public static final int FELD_GROESSE = 75;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    private Group gamefeldGroup = new Group();
    private Group spielsteinweissGroup = new Group();
    private Group spielsteinschwarzGroup = new Group();
    private GameFeld[][] brett = new GameFeld[WIDTH][HEIGHT];
    private Player player1;
    private Player player2;
    private ZugController playcontrol;

    public GameBord(Player player1, Player player2){

        this.player1 = player1;
        this.player2 = player2;

        setUpSpiel();
        getChildren().addAll(gamefeldGroup,spielsteinweissGroup, spielsteinschwarzGroup);
        playcontrol = new ZugController(player1, player2);
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
                    spielstein.doNotMove();
                    break;
                case NORMALWEISS:
                    spielstein.move(newX,newY);
                    brett[x0][y0].setSpielstein(null);
                    brett[newX][newY].setSpielstein(spielstein);
                    playcontrol.changeTurn();
                    break;
                case NORMALSCHWARZ:
                    spielstein.move(newX,newY);
                    brett[x0][y0].setSpielstein(null);
                    brett[newX][newY].setSpielstein(spielstein);
                    playcontrol.changeTurn();
                    break;
                case KILL:
                    spielstein.move(newX,newY);
                    brett[x0][y0].setSpielstein(null);
                    brett[newX][newY].setSpielstein(spielstein);

                    Spielstein gegnerStein = resultat.getSpielstein();
                    brett[zuBrett(gegnerStein.getOldX())][zuBrett(gegnerStein.getOldY())].setSpielstein(null);
                    if(gegnerStein.getSteinTyp() == SteinTyp.WEISS || gegnerStein.getSteinTyp() == SteinTyp.DAMEWEISS){
                    spielsteinweissGroup.getChildren().remove(gegnerStein);}
                    else {
                    spielsteinschwarzGroup.getChildren().remove(gegnerStein);
                    }
                    pruefeSieg();
                    playcontrol.changeTurn();
                    break;
                case WANDLEDAMEWEISS:
                    spielstein.move(newX,newY);
                    brett[x0][y0].setSpielstein(null);
                    Spielstein damensteinweiss = erstelleSpielstein(SteinTyp.DAMEWEISS,newX,newY);
                    brett[newX][newY].setSpielstein(damensteinweiss);
                    spielsteinweissGroup.getChildren().remove(spielstein);
                    spielsteinweissGroup.getChildren().add(damensteinweiss);
                    playcontrol.changeTurn();
                    break;
                case KILLUNDWANDLEWEISS:
                    spielstein.move(newX,newY);
                    brett[x0][y0].setSpielstein(null);
                    Spielstein dameweiss = erstelleSpielstein(SteinTyp.DAMEWEISS,newX,newY);
                    Spielstein gegnerStein2 = resultat.getSpielstein();
                    brett[zuBrett(gegnerStein2.getOldX())][zuBrett(gegnerStein2.getOldY())].setSpielstein(null);
                    brett[newX][newY].setSpielstein(dameweiss);
                    spielsteinweissGroup.getChildren().remove(spielstein);
                    spielsteinweissGroup.getChildren().add(dameweiss);
                    spielsteinschwarzGroup.getChildren().remove(gegnerStein2);
                    pruefeSieg();
                    playcontrol.changeTurn();
                    break;
                case WANDLEDAMESCHWARZ:
                    spielstein.move(newX,newY);
                    brett[x0][y0].setSpielstein(null);
                    Spielstein damensteinschwarz = erstelleSpielstein(SteinTyp.DAMESCHWARZ,newX,newY);
                    brett[newX][newY].setSpielstein(damensteinschwarz);
                    spielsteinschwarzGroup.getChildren().remove(spielstein);
                    spielsteinschwarzGroup.getChildren().add(damensteinschwarz);
                    playcontrol.changeTurn();
                    break;
                case KILLUNDWANDLESCHWARZ:
                    spielstein.move(newX,newY);
                    brett[x0][y0].setSpielstein(null);
                    Spielstein dameschwarz = erstelleSpielstein(SteinTyp.DAMESCHWARZ,newX,newY);
                    Spielstein gegnerStein3 = resultat.getSpielstein();
                    brett[zuBrett(gegnerStein3.getOldX())][zuBrett(gegnerStein3.getOldY())].setSpielstein(null);
                    brett[newX][newY].setSpielstein(dameschwarz);
                    spielsteinschwarzGroup.getChildren().remove(spielstein);
                    spielsteinschwarzGroup.getChildren().add(dameschwarz);
                    spielsteinweissGroup.getChildren().remove(gegnerStein3);
                    pruefeSieg();
                    playcontrol.changeTurn();
                    break;
                case DAMEWEISSNORMAL:
                    spielstein.move(newX,newY);
                    brett[x0][y0].setSpielstein(null);
                    brett[newX][newY].setSpielstein(spielstein);
                    playcontrol.changeTurn();

            }
        });

        return spielstein;
    }
    private ZugResultat tryMove(Spielstein spielstein, int newX, int newY){
        // nur Diagonale züge erlauben
        if(brett[newX][newY].hatStein() || (newX + newY) % 2 == 0){
            return new ZugResultat(ZugTyp.KEIN);
        }
        int x0 = zuBrett(spielstein.getOldX());
        int y0 = zuBrett(spielstein.getOldY());

        boolean istweiss = (spielstein.getSteinTyp() == SteinTyp.WEISS);
        boolean istschwarz = (spielstein.getSteinTyp() == SteinTyp.SCHWARZ);
        boolean istdameweiss = (SteinTyp.DAMEWEISS == spielstein.getSteinTyp());
        boolean normalzug = (Math.abs(newX-x0) == 1) && (newY-y0 == spielstein.getSteinTyp().moveDir);
        // normalzug für beide Steintypen für diagonales Bewegen in movedirektion für normale steine
        boolean normalwandlungsrestrict = (istweiss && (y0 != 6 )||istschwarz&&(y0 != 1));
        // verhindert letzten zug an Bande, damit sicherlich Damenwandlung ausgeführt wird.
        boolean killzug = Math.abs(newX-x0) == 2 && newY-y0 == spielstein.getSteinTyp().moveDir * 2;
        // Killzug für beide Steintypen für diagonales Killen in movedirektion
        boolean killzugrestrict = ((istweiss && (y0 != 5 )||istschwarz &&(y0 != 2)));
      
        boolean damenormalzug = (Math.abs(newX-x0) == 1);
        //  && Math.abs(newX-x0) >=-8 && Math.abs(newX-x0) <= 8 && Math.abs(newY-y0) <= -8 && Math.abs(newY-y0) <= 8);
        if (!(playcontrol.getTurn())) {
            if (normalzug && normalwandlungsrestrict && istweiss) {        
                return new ZugResultat(ZugTyp.NORMALWEISS);
            } else if ((y0 == 6) && normalzug) {
                return new ZugResultat(ZugTyp.WANDLEDAMEWEISS);
            } else if (killzug && (y0 == 5)) {
                int x1 = x0 + (newX - x0) / 2;
                int y1 = y0 + (newY - y0) / 2;

                if (brett[x1][y1].hatStein() &&
                        brett[x1][y1].getSpielstein().getSteinTyp() != spielstein.getSteinTyp()) {
                    return new ZugResultat(ZugTyp.KILLUNDWANDLEWEISS, brett[x1][y1].getSpielstein());
                }
            } else if (killzug && killzugrestrict) {
                int x1 = x0 + (newX - x0) / 2;
                int y1 = y0 + (newY - y0) / 2;

                if (brett[x1][y1].hatStein() &&
                        brett[x1][y1].getSpielstein().getSteinTyp() != spielstein.getSteinTyp()) {
                    return new ZugResultat(ZugTyp.KILL, brett[x1][y1].getSpielstein());
                }
            }
        }
        else if (istdameweiss && damenormalzug){
            return new ZugResultat(ZugTyp.DAMEWEISSNORMAL);
        }
        else if (playcontrol.getTurn()){
            if (normalzug && normalwandlungsrestrict && istschwarz){
                return new ZugResultat(ZugTyp.NORMALSCHWARZ);
            }
            else if(killzug && istschwarz && (y0 ==2)){
                int x1 = x0 + (newX - x0)/2;
                int y1 = y0 + (newY - y0)/2;

                if(brett[x1][y1].hatStein() &&
                        brett[x1][y1].getSpielstein().getSteinTyp() != spielstein.getSteinTyp()) {
                    return new ZugResultat(ZugTyp.KILLUNDWANDLESCHWARZ, brett[x1][y1].getSpielstein());
                }
            }
            else if(istschwarz && (y0 == 1) && normalzug){
                return new ZugResultat(ZugTyp.WANDLEDAMESCHWARZ);
            }
            else if(killzug && killzugrestrict && istschwarz){
                int x1 = x0 + (newX - x0)/2;
                int y1 = y0 + (newY - y0)/2;

                if(brett[x1][y1].hatStein() &&
                        brett[x1][y1].getSpielstein().getSteinTyp() != spielstein.getSteinTyp()){
                    return new ZugResultat(ZugTyp.KILL,brett[x1][y1].getSpielstein());
                }
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
                    spielsteinweissGroup.getChildren().add(spielstein);
                }

                if(y >= 5 && (x+y)%2!=0){
                    spielstein = erstelleSpielstein(SteinTyp.SCHWARZ,x,y);
                    spielsteinschwarzGroup.getChildren().add(spielstein);
                }
                if(spielstein != null) {
                    gameFeld.setSpielstein(spielstein);
                }
            }
        }
    }
    private void pruefeSieg(){
        if(spielsteinschwarzGroup.getChildren().isEmpty()){
            System.out.println("Weiss Gewinnt das Spiel");
            VBox winnerbox = new VBox(10);
            winnerbox.setTranslateX(70);
            winnerbox.setTranslateY(250);

            WinnerMessageBox winnermessage = new WinnerMessageBox("Weiss Gewinnt, danke fürs Spielen");

            MenuButton btnQuit = new MenuButton("Quit Game");

            btnQuit.setOnMouseClicked(event -> {
                System.exit(0);
            });

            winnerbox.getChildren().addAll(winnermessage, btnQuit);

            getChildren().addAll(winnerbox);

        }
        if(spielsteinweissGroup.getChildren().isEmpty()){
            System.out.println("Schwarz Gewinnt das Spiel");

            VBox winnerbox = new VBox(10);
            winnerbox.setTranslateX(70);
            winnerbox.setTranslateY(250);

            WinnerMessageBox winnermessage = new WinnerMessageBox("Schwarz Gewinnt, danke fürs Spielen");

            MenuButton btnQuit = new MenuButton("Quit Game");
            btnQuit.setOnMouseClicked(event -> {
                System.exit(0);
            });

            winnerbox.getChildren().addAll(winnermessage, btnQuit);

            getChildren().addAll(winnerbox);
        }
    }
}
