package Control;

import Model.*;
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

        Parent root = FXMLLoader.load(getClass().getResource("/View/GameView.fxml"));
        newStage.setTitle("Aeroplane");
        newStage.setScene(new Scene(root, 600, 600));

        Aeroplane.switchStage(newStage);
    }

}
