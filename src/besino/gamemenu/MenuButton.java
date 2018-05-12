package besino.gamemenu;

import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MenuButton extends StackPane {

    private Text text;

    public MenuButton (String name){
        this.text = new Text(name);
        this.text.getFont();
        text.setFont(Font.font(20));
        text.setFill(Color.WHITE);

            Rectangle bg = new Rectangle(200, 30);
            bg.setOpacity(0.6);
            bg.setFill(Color.HOTPINK);

            GaussianBlur blur = new GaussianBlur(3.5);
            bg.setEffect(blur);

            setAlignment(Pos.CENTER);
            setRotate(-0.5);
            getChildren().addAll(bg, text);

            setOnMouseEntered(event -> {
                bg.setTranslateX(2);
                text.setTranslateX(2);
                bg.setFill(Color.WHITE);
                text.setFill(Color.DARKGREY);
            });

            setOnMouseExited(event -> {
                bg.setTranslateX(0);
                text.setTranslateX(0);
                bg.setFill(Color.HOTPINK);
                text.setFill(Color.BLACK);
            });

                    DropShadow drop = new DropShadow(10, Color.WHITE);
                    drop.setInput(new Glow());

            setOnMousePressed(event -> setEffect(drop));

            setOnMouseReleased(event -> setEffect(null));

    }
}
