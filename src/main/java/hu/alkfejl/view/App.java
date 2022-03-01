package hu.alkfejl.view;

import hu.alkfejl.controller.Settings;
import hu.alkfejl.controller.Toplista;
import hu.alkfejl.dao.MultiPlayerDAOImpl;
import hu.alkfejl.dao.SinglePlayerDAOImpl;
import hu.alkfejl.model.SinglePlayer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;


/**
 * JavaFX App
 */
public class App extends Application {
    public static Stage stage;
    public static Scene mainWindowScene;
    public static Scene toplistScene;
    public static Scene singleScene;
    public static Scene multiScene;
    public static Scene settingsScene;
    public static Stage settingsStage;
    public static Stage editStage;
    public static Scene editScene;
    @Override
    public void start(Stage stage) throws IOException {



        Parent root4 = FXMLLoader.load(getClass().getResource("/fxml/settings.fxml"));
        App.settingsScene = new Scene(root4);
        App.settingsStage.setTitle("SETTINGS");



        Parent root2 = FXMLLoader.load(getClass().getResource("/fxml/toplista.fxml"));
        App.toplistScene = new Scene(root2);
        App.stage = stage;
        Parent root3 = FXMLLoader.load(getClass().getResource("/fxml/main_menu.fxml"));
        mainWindowScene = new Scene(root3, 1280, 720);
        stage.setTitle("SNAKE GAME");





        stage.setScene(mainWindowScene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }

}