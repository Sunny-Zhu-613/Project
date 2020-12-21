package Control;

import View.GameView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LoadGameController {

    @FXML
    private TextField filePath;
    public void handleGetPath(ActionEvent actionEvent) {
        String path = filePath.getText();
        System.out.println(path);

        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Stage newStage = Aeroplane.getPrimaryStage();
            newStage.setTitle("Aeroplane");
            GameView gameView = (GameView) in.readObject();;
            newStage.setScene(gameView.getGameView());
            Aeroplane.switchStage(newStage);
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            System.out.println("file not found");
            c.printStackTrace();
            return;
        }
    }
}
