package besino.game;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GameMenu extends Application {

    private MenuRegister register;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane root = new Pane();
        root.setPrefSize(800,600);

        InputStream is = Files.newInputStream(Paths.get("res/images/background.png"));
        Image img = new Image(is);
        is.close();

        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(600);
        imgView.setFitWidth(800);

        register = new MenuRegister();

        root.getChildren().addAll(imgView, register);

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
