package besino.spielerZeugs;

import besino.gamebord.GameBord;
import besino.gamebord.Rules;
import besino.gamebord.Spielstein;
import besino.spielzugRules.ZugComputer;
import besino.spielzugRules.ZugResultat;
import besino.spielzugRules.ZugTyp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Computer {

    GameBord gameBord;
    Rules rulecheck;


    public Computer(GameBord gameBord) {
        this.gameBord = gameBord;
        this.rulecheck = new Rules(gameBord);

    }

    public Spielstein findSpielstein(GameBord gameBord){
        List <Spielstein> list = gameBord.getSpielsteinweissGroup()
                .getChildren()
                .stream()
                .map(node -> (Spielstein)node)
                .collect(Collectors.toList());
        List<Spielstein> copy = new ArrayList<>(list);
        Collections.shuffle(copy);
        Spielstein random = copy.get(0);

        return  random;
    }

    public ZugComputer findeRandomZug() {

        ZugResultat resultat;
        resultat = rulecheck.tryMove(findSpielstein(gameBord),0,0);
        int newX = 0;
        int newY = 0;
        int oldX = 0;
        int oldY = 0;
        int checkruns = 0;
        Spielstein spielstein = findSpielstein(gameBord);
        while (resultat.getZugTyp() == ZugTyp.KEIN) {
            spielstein = findSpielstein(gameBord);
            oldX = spielstein.getX();
            oldY = spielstein.getY();
            for (int y = 0; y < gameBord.HEIGHT;y++) {
                for (int x = 0; x < gameBord.WIDTH;x++) {
                    resultat = rulecheck.tryMove(spielstein, x, y);
                    newX = x;
                    newY = y;
                    if (resultat.getZugTyp() != ZugTyp.KEIN){
                        x = 7;
                    }
                }
                if (resultat.getZugTyp() != ZugTyp.KEIN){
                    y = 7;
                }
            }
            checkruns++;
        }

        ZugComputer compZug = new ZugComputer(spielstein, newX, newY, oldX, oldY);

        return compZug;
    }

    public void spieleRandomZug(){
        ZugComputer compZug = findeRandomZug();

        gameBord.doCompZug(compZug);
    }

}
