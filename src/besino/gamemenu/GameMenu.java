package besino.gamemenu;

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

    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane root = new Pane();
        root.setPrefSize(600,600);

        InputStream is = Files.newInputStream(Paths.get("res/images/background.png"));
        Image img = new Image(is);
        is.close();

        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(600);
        imgView.setFitWidth(600);

        MenuRegister register = new MenuRegister();

        root.getChildren().addAll(imgView, register);

        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Unicorn Dame");
        primaryStage.show();

    }

    public static void main(String[] args){
        launch(args);
    }
}
