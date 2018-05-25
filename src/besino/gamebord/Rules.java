package besino.gamebord;

import besino.spielzugRules.ZugResultat;
import besino.spielzugRules.ZugTyp;


public class Rules {

    private GameBord gamebord;

    public Rules(GameBord gamebord) {
        this.gamebord = gamebord;
    }

    public ZugResultat tryMove(Spielstein spielstein, int newX, int newY){
        // nur Diagonale züge erlauben

        if(gamebord.getBrett()[newX][newY].hatStein() || (newX + newY) % 2 == 0){
            return new ZugResultat(ZugTyp.KEIN);
        }


        int x0 = gamebord.zuBrett(spielstein.getOldX());
        int y0 = gamebord.zuBrett(spielstein.getOldY());

        boolean istweiss = (spielstein.getSteinTyp() == SteinTyp.WEISS || (spielstein.getSteinTyp() == SteinTyp.DAMEWEISS));
        boolean istschwarz = (spielstein.getSteinTyp() == SteinTyp.SCHWARZ || (spielstein.getSteinTyp() == SteinTyp.DAMESCHWARZ));
        boolean istdameweiss = (SteinTyp.DAMEWEISS == spielstein.getSteinTyp());
        boolean normalzug = (Math.abs(newX-x0) == 1) && (newY-y0 == spielstein.getSteinTyp().moveDir);
        // normalzug für beide Steintypen für diagonales Bewegen in movedirektion für normale steine
        boolean normalwandlungsrestrict = (istweiss && (y0 != 6 )||istschwarz&&(y0 != 1));
        // verhindert letzten zug an Bande, damit sicherlich Damenwandlung ausgeführt wird.
        boolean killzug = Math.abs(newX-x0) == 2 && newY-y0 == spielstein.getSteinTyp().moveDir * 2;
        // Killzug für beide Steintypen für diagonales Killen in movedirektion
        boolean killzugrestrict = ((istweiss && (y0 != 5 )||istschwarz &&(y0 != 2)));
        boolean damenormalzug = (Math.abs(newX-x0) == 1);

        //Weisse Zugregeln
        if (!(gamebord.getPlaycontrol().getTurn()) && istweiss) {
            if (normalzug && normalwandlungsrestrict) {
                return new ZugResultat(ZugTyp.NORMALWEISS);
            } else if ((y0 == 6) && normalzug) {
                return new ZugResultat(ZugTyp.WANDLEDAMEWEISS);
            } else if (killzug && (y0 == 5)) {
                int x1 = x0 + (newX - x0) / 2;
                int y1 = y0 + (newY - y0) / 2;

                if (gamebord.getBrett()[x1][y1].hatStein() &&
                        gamebord.getBrett()[x1][y1].getSpielstein().getSteinTyp() != spielstein.getSteinTyp()) {
                    return new ZugResultat(ZugTyp.KILLUNDWANDLEWEISS, gamebord.getBrett()[x1][y1].getSpielstein());
                }
            } else if(killzug) {
                int x1 = x0 + (newX - x0) / 2;
                int y1 = y0 + (newY - y0) / 2;

                if (gamebord.getBrett()[x1][y1].hatStein() &&
                        gamebord.getBrett()[x1][y1].getSpielstein().getSteinTyp() != spielstein.getSteinTyp()) {
                    return new ZugResultat(ZugTyp.KILL, gamebord.getBrett()[x1][y1].getSpielstein());
                }
            }
        }
        else if (istdameweiss && damenormalzug){
            return new ZugResultat(ZugTyp.DAMEWEISSNORMAL);
        }
        else if (gamebord.getPlaycontrol().getTurn() && istschwarz){
            if (normalzug && normalwandlungsrestrict){
                return new ZugResultat(ZugTyp.NORMALSCHWARZ);
            }
            else if(killzug && (y0 ==2)){
                int x1 = x0 + (newX - x0)/2;
                int y1 = y0 + (newY - y0)/2;

                if(gamebord.getBrett()[x1][y1].hatStein() &&
                        gamebord.getBrett()[x1][y1].getSpielstein().getSteinTyp() != spielstein.getSteinTyp()) {
                    return new ZugResultat(ZugTyp.KILLUNDWANDLESCHWARZ, gamebord.getBrett()[x1][y1].getSpielstein());
                }
            }
            else if((y0 == 1) && normalzug){
                return new ZugResultat(ZugTyp.WANDLEDAMESCHWARZ);
            }
            else if(killzug){
                int x1 = x0 + (newX - x0)/2;
                int y1 = y0 + (newY - y0)/2;

                if(gamebord.getBrett()[x1][y1].hatStein() &&
                        gamebord.getBrett()[x1][y1].getSpielstein().getSteinTyp() != spielstein.getSteinTyp()){
                    return new ZugResultat(ZugTyp.KILL,gamebord.getBrett()[x1][y1].getSpielstein());
                }
            }
        }
        return new ZugResultat(ZugTyp.KEIN);

    }



}