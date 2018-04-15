package besino.gamemenu;

import besino.gamebord.GameBord;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;



public class MenuRegister extends Parent {

    final int offset = 380;
    private GameBord gameBord;

    public MenuRegister(){
        VBox menu0 = new VBox(10);
        VBox menu1 = new VBox(10);

        menu0.setTranslateX(offset);
        menu0.setTranslateY(350);

        menu1.setTranslateX(offset);
        menu1.setTranslateY(350);

        MenuButton btnStart = new MenuButton("Start Game");
        btnStart.setOnMouseClicked(event -> {
            getChildren().removeAll(menu0);
            getChildren().addAll(menu1);
        });
        //FadeTransition ft = new FadeTransition(Duration.seconds(0.5), this);
       // ft.setFromValue(1);
       // ft.setToValue(0);
       // ft.setOnFinished(evt -> this.setVisible(false));
       // ft.play();

        MenuButton btnQuit = new MenuButton("Quit Game");
        btnQuit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        MenuButton btnVsComputer = new MenuButton("vs Computer");
        btnVsComputer.setOnMouseClicked(event -> {
            getChildren().removeAll(menu1);
            gameBord = new GameBord();
            getChildren().addAll(gameBord);
        });

        MenuButton btnVsHuman = new MenuButton("2 Player Mode");
        btnVsHuman.setOnMouseClicked(event -> {

        });

        menu0.getChildren().addAll(btnStart, btnQuit);
        menu1.getChildren().addAll(btnVsComputer, btnVsHuman);
        Rectangle bg = new Rectangle(600,600);
        bg.setFill(Color.GRAY);
        bg.setOpacity(0.4);
        getChildren().addAll(bg, menu0);

    }

}
