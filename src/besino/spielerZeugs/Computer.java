package besino.spielerZeugs;

import besino.gamebord.GameBord;
import besino.gamebord.Spielstein;
import javafx.scene.Group;
import javafx.scene.Node;

import java.util.List;
import java.util.Random;

public class Computer {

    GameBord gameBord;

    public Computer(GameBord gameBord) {
        this.gameBord = gameBord;
    }
    /*
    public Spielstein findSpielstein(GameBord gameBord){
        int size = gameBord.getSpielsteinweissGroup().getChildren().size();
        int item = new Random().nextInt(size); // In real life, the Random object should be rather more shared than this
        int i = 0;
        for (Spielstein spielstein : gameBord.getSpielsteinweissGroup().){

        }
    }*/

}
