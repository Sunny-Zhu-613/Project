package Control;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Aeroplane extends javafx.application.Application {
    static private Stage primaryStage;

    public static Stage getPrimaryStage() { return Aeroplane.primaryStage; }
    private static void setPrimaryStage(Stage newStage) { Aeroplane.primaryStage = newStage; }

    public static void switchStage(Stage newStage) {
        Aeroplane.getPrimaryStage().close();
        Aeroplane.setPrimaryStage(newStage);
        Aeroplane.primaryStage.show();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/View/MainView.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 512, 256));
        setPrimaryStage(primaryStage);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}