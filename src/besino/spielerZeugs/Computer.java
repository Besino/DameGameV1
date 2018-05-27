package besino.spielerZeugs;

import besino.gamebord.GameBord;
import besino.gamebord.Spielstein;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Computer {

    GameBord gameBord;


    public Computer(GameBord gameBord) {
        this.gameBord = gameBord;
        Thread kithread = new Thread(new Runnable() {
            @Override
            public void run() {
                /*while... Bedinung noch ergänzen über Zugcontroller:
                * dann finde Spielstein
                * finde Move (eventuell eigene Klasse Move ergänzen wo Objekte mit Koordinaten gespeichert sind)
                * führe Move aus, solange Spiel nicht Ende (Winnermessage) */
            }
        });
        kithread.start();
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


}
