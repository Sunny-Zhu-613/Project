package Control;

import Model.*;
import View.GameView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class MainController {

    @FXML
    public void startBtnPressed() throws IOException {
        Stage newStage = Aeroplane.getPrimaryStage();

//        Parent root = FXMLLoader.load(getClass().getResource("/View/GameView.fxml"));
        newStage.setTitle("Aeroplane");
//        newStage.setScene(new Scene(root, 600, 600));
        GameView gameView = new GameView(1200, 600);
        newStage.setScene(gameView.getGameView());

        Aeroplane.switchStage(newStage);
    }

}
