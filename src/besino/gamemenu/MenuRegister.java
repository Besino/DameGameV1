package besino.gamemenu;

import besino.gamebord.GameBord;
import besino.spielerZeugs.Player;
import besino.spielerZeugs.PlayerType;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;



class MenuRegister extends Parent {

    private GameBord gameBord;

    MenuRegister(){
        VBox menu0 = new VBox(10);
        VBox menu1 = new VBox(10);

        int offset = 380;
        menu0.setTranslateX(offset);
        menu0.setTranslateY(350);

        menu1.setTranslateX(offset);
        menu1.setTranslateY(350);

        MenuButton btnStart = new MenuButton("Start Game");
        btnStart.setOnMouseClicked(event -> {
            getChildren().removeAll(menu0);
            getChildren().addAll(menu1);
        });

        MenuButton btnQuit = new MenuButton("Quit Game");
        btnQuit.setOnMouseClicked(event -> System.exit(0));

        MenuButton btnVsComputer = new MenuButton("vs Computer Mode");
        btnVsComputer.setOnMouseClicked(event -> {
            getChildren().removeAll(menu1);
            Player player1 = new Player(PlayerType.HUMAN);
            Player player2 = new Player(PlayerType.COMPUTER);
            gameBord = new GameBord(player1,player2);
            getChildren().addAll(gameBord);
        });

        MenuButton btnVsHuman = new MenuButton("2 Player Mode");
        btnVsHuman.setOnMouseClicked(event -> {
            Player player1 = new Player(PlayerType.HUMAN);
            Player player2 = new Player(PlayerType.HUMAN);
            gameBord = new GameBord(player1,player2);
            getChildren().addAll(gameBord);
        });

        menu0.getChildren().addAll(btnStart, btnQuit);
        menu1.getChildren().addAll(btnVsComputer, btnVsHuman);
        Rectangle bg = new Rectangle(600,600);
        bg.setFill(Color.GRAY);
        bg.setOpacity(0.4);
        getChildren().addAll(bg, menu0);

    }

}
