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
        do {
            Spielstein spielstein = findSpielstein(gameBord);

            for (int y = 0; y < gameBord.HEIGHT; y++) {
                for (int x = 0; x < gameBord.WIDTH; x++) {
                    resultat = rulecheck.tryMove(spielstein, x, y);
                    newX = x;
                    newY = y;
                }
            }
        } while (resultat.getZugTyp() == ZugTyp.KEIN);
        Spielstein spielstein = resultat.getSpielstein();

        ZugComputer compZug = new ZugComputer(spielstein, newX, newY);

        return compZug;
    }

    public void spieleRandomZug(){
        ZugComputer compZug = findeRandomZug();

        gameBord.erstelleSpielstein(compZug.getSpielstein().getSteinTyp(), compZug.getNewX(),compZug.getNewY());
    }

}
