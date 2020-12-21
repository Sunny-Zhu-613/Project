package Control;

import Model.*;
import View.GameView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    public void startBtnPressed() {
        Stage newStage = Aeroplane.getPrimaryStage();

//        Parent root = FXMLLoader.load(getClass().getResource("/View/GameView.fxml"));
        newStage.setTitle("Aeroplane");
//        newStage.setScene(new Scene(root, 600, 600));
        GameView gameView = new GameView(1200, 600);
        newStage.setScene(gameView.getGameView());

        Aeroplane.switchStage(newStage);
    }

    @FXML
    public void loadBtnPressed(ActionEvent actionEvent) throws IOException {
        Stage newStage = Aeroplane.getPrimaryStage();

        Parent root = FXMLLoader.load(getClass().getResource("/View/LoadGameView.fxml"));
        newStage.setTitle("Load Game");
        newStage.setScene(new Scene(root, 300, 100));
//        GameView gameView = new GameView(1200, 600);
//        newStage.setScene(gameView.getGameView());
        Aeroplane.switchStage(newStage);
    }
}
