package besino.gamebord;

import besino.spielzugRules.ZugResultat;
import besino.spielzugRules.ZugTyp;


public class Rules {

    private GameBord gamebord;

    public Rules(GameBord gamebord) {
        this.gamebord = gamebord;
    }

    public ZugResultat tryMove(Spielstein spielstein, int newX, int newY) {
        // nur Diagonale züge erlauben

        if (gamebord.getBrett()[newX][newY].hatStein() || (newX + newY) % 2 == 0) {
            return new ZugResultat(ZugTyp.KEIN);
        }


        int x0 = gamebord.zuBrett(spielstein.getOldX());
        int y0 = gamebord.zuBrett(spielstein.getOldY());

        ZugResultat resultat;

        resultat = checkAndReturnWhiteRules(newX,newY,x0,y0,spielstein);
        if (resultat.getZugTyp() == ZugTyp.KEIN){
            resultat = checkAndReturnBlackRules(newX,newY,x0,y0,spielstein);
        }
        return resultat;
    }


        private ZugResultat checkAndReturnWhiteRules(int newX, int newY, int x0, int y0, Spielstein spielstein){
            if (!(gamebord.getPlaycontrol().getTurn()) && spielstein.istWeiss()) {
                if (checkIfNormalzug(newX, newY, x0, y0, spielstein)) {
                    return new ZugResultat(ZugTyp.NORMAL);
                } else if ((y0 == 6) && checkIfNormalzug(newX, newY, x0, y0, spielstein)) {
                    return new ZugResultat(ZugTyp.WANDLEDAMEWEISS);
                } else if (checkIfKillzug(newX,newY,x0,y0,spielstein) && (y0 == 5)) {
                    int x1 = x0 + (newX - x0) / 2;
                    int y1 = y0 + (newY - y0) / 2;

                    if (gamebord.getBrett()[x1][y1].hatStein() &&
                            gamebord.getBrett()[x1][y1].getSpielstein().getSteinTyp() != spielstein.getSteinTyp()) {
                        return new ZugResultat(ZugTyp.KILLUNDWANDLEWEISS, gamebord.getBrett()[x1][y1].getSpielstein());
                    }
                } else if (checkIfKillzug(newX,newY,x0,y0,spielstein)) {
                    ZugResultat killZug = returnKillzug(newX, newY, x0, y0, spielstein);
                    return killZug;
                }
                else if (spielstein.istDameWeiss() && (Math.abs(newX - x0) == 1)) {
                    return new ZugResultat(ZugTyp.NORMAL);
                }
            }
            return new ZugResultat(ZugTyp.KEIN);
        }

        private ZugResultat checkAndReturnBlackRules (int newX, int newY, int x0, int y0, Spielstein spielstein){
            if (gamebord.getPlaycontrol().getTurn() && spielstein.istSchwarz()) {
                if (checkIfNormalzug(newX, newY, x0, y0, spielstein)) {
                    return new ZugResultat(ZugTyp.NORMAL);
                } else if (checkIfKillzug(newX,newY,x0,y0,spielstein) && (y0 == 2)) {
                    int x1 = x0 + (newX - x0) / 2;
                    int y1 = y0 + (newY - y0) / 2;

                    if (gamebord.getBrett()[x1][y1].hatStein() &&
                            gamebord.getBrett()[x1][y1].getSpielstein().getSteinTyp() != spielstein.getSteinTyp()) {
                        return new ZugResultat(ZugTyp.KILLUNDWANDLESCHWARZ, gamebord.getBrett()[x1][y1].getSpielstein());
                    }
                } else if ((y0 == 1) && checkIfNormalzug(newX, newY, x0, y0, spielstein)) {
                    return new ZugResultat(ZugTyp.WANDLEDAMESCHWARZ);
                } else if (checkIfKillzug(newX,newY,x0,y0,spielstein)) {
                    ZugResultat killZug = returnKillzug(newX,newY,x0,y0,spielstein);
                    return killZug;
                }
            }
            return new ZugResultat(ZugTyp.KEIN);
        }

        private boolean checkIfNormalzug ( int newX, int newY, int x0, int y0, Spielstein spielstein){
            if ((Math.abs(newX - x0) == 1) && (newY - y0 == spielstein.getSteinTyp().moveDir) &&
                    (spielstein.istWeiss() && (y0 != 6) || spielstein.istSchwarz() && (y0 != 1))){
                return true;
            }
            return false;
        }

        private boolean checkIfKillzug (int newX, int newY, int x0, int y0, Spielstein spielstein){
            if (Math.abs(newX - x0) == 2 && newY - y0 == spielstein.getSteinTyp().moveDir * 2){
                return true;
            }
            return false;
        }

        private ZugResultat returnKillzug ( int newX, int newY, int x0, int y0, Spielstein spielstein){
            int x1 = x0 + (newX - x0) / 2;
            int y1 = y0 + (newY - y0) / 2;

            if (gamebord.getBrett()[x1][y1].hatStein() &&
                    gamebord.getBrett()[x1][y1].getSpielstein().getSteinTyp() != spielstein.getSteinTyp()) {
                return new ZugResultat(ZugTyp.KILL, gamebord.getBrett()[x1][y1].getSpielstein());
            } else {
                return new ZugResultat(ZugTyp.KEIN);
            }
    }
}