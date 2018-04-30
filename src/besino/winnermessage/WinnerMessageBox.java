package besino.winnermessage;

import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class WinnerMessageBox extends StackPane {

    private Text text;

    public WinnerMessageBox(String name){
        this.text = new Text(name);
        text.setFont(this.text.getFont().font(28));
        text.setFill(Color.WHITE);

            Rectangle bg = new Rectangle(500, 150);
            bg.setOpacity(0.95);
            bg.setFill(Color.BLACK);

            GaussianBlur blur = new GaussianBlur(5.5);
            bg.setEffect(blur);

            setAlignment(Pos.CENTER);
            setRotate(-0.5);
            getChildren().addAll(bg, text);


                    DropShadow drop = new DropShadow(5, Color.WHITE);
                    drop.setInput(new Glow());

            setOnMousePressed(event -> setEffect(drop));

            setOnMouseReleased(event -> setEffect(null));

    }
}
