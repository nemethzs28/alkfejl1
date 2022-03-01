package hu.alkfejl.controller;

import hu.alkfejl.view.App;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.TextInputDialog;

import java.io.IOException;

public class MainMenu  {

    public void gotoList(ActionEvent actionEvent) throws IOException {
        App.stage.setScene(App.toplistScene);
    }

    public void onQuit(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void playsingle(ActionEvent actionEvent) throws IOException {
        TextInputDialog nev1 = new TextInputDialog("Zsombor");
        nev1.setHeaderText("A játékos neve: ");
        nev1.showAndWait();
        SingleGame.playerName = nev1.getResult();
        SingleGame.gameStart();
    }

    public void playmulti(ActionEvent actionEvent) {
        TextInputDialog nev1 = new TextInputDialog("Pista");
        nev1.setHeaderText("Az egyes játékos neve: ");
        nev1.showAndWait();
        MultiGame.playerName = nev1.getResult();

        TextInputDialog nev2 = new TextInputDialog("Ábel");
        nev2.setHeaderText("A kettes játékos neve: ");
        nev2.showAndWait();
        MultiGame.playerName2 = nev2.getResult();


        MultiGame.gameStart();
    }

    public void gotoSettings(ActionEvent actionEvent) {
        App.settingsStage.setScene(App.settingsScene);
        App.settingsStage.show();

    }
}
