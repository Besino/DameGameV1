package besino.game;

import javafx.animation.FadeTransition;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.scene.shape.Rectangle;


public class MenuRegister extends Parent {

    final int offset = 400;

    public MenuRegister(){
        VBox menu0 = new VBox(10);
        VBox menu1 = new VBox(10);

        menu0.setTranslateX(100);
        menu0.setTranslateY(200);

        menu1.setTranslateX(100);
        menu1.setTranslateY(200);

        MenuButton btnStart = new MenuButton("Start Game");
        //FadeTransition ft = new FadeTransition(Duration.seconds(0.5), this);
       // ft.setFromValue(1);
       // ft.setToValue(0);
       // ft.setOnFinished(evt -> this.setVisible(false));
       // ft.play();

        MenuButton btnQuit = new MenuButton("Quit Game");
        btnQuit.setOnMouseClicked(event -> {
            System.exit(0);
        });

        menu0.getChildren().addAll(btnStart, btnQuit);
        Rectangle bg = new Rectangle(800,600);
        bg.setFill(Color.GRAY);
        bg.setOpacity(0.4);
        getChildren().addAll(bg, menu0);
    }


}
