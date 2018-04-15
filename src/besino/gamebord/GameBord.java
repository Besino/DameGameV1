package besino.gamebord;

import besino.gamemenu.GameMenu;
import besino.gamemenu.MenuRegister;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class GameBord extends Parent {

    public static final int FELD_GROESSE = 75;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    private Group gamefeldGroup = new Group();
    private Group spielsteinGroup = new Group();


    public GameBord(){

        for (int y = 0; y < HEIGHT; y++){
            for (int x = 0; x < WIDTH; x++){
                GameFeld gameFeld = new GameFeld((x+y)%2==0,x,y);

                gamefeldGroup.getChildren().add(gameFeld);
            }
        }
        getChildren().addAll(gamefeldGroup,spielsteinGroup);
    }
}
