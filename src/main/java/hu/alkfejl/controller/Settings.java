package hu.alkfejl.controller;

import hu.alkfejl.view.App;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Settings implements Initializable {
    @FXML
    public TextField sizeField;
    public ColorPicker snakePicker2;
    public ColorPicker snakePicker1;
    public ColorPicker foodPicker1;
    public ColorPicker foodPicker2;
    public ColorPicker foodPicker3;
    public ComboBox<Integer> speedBox;
    public ToggleButton wallToggle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        App.settingsStage = new Stage();
        speedBox.setItems(FXCollections.observableArrayList(5, 6, 7));
        speedBox.setValue(5);
        App.settingsStage.setOnCloseRequest(windowEvent -> {
            //size lekérése
            try {
                int szelesseg = Integer.parseInt(sizeField.getText());
                SingleGame.tableSize = szelesseg;
                MultiGame.tableSize = szelesseg;
            } catch (NumberFormatException nfe) {
                SingleGame.tableSize = 20;
                MultiGame.tableSize = 20;
            }
            //speed lekérése
            SingleGame.speed = speedBox.getValue();
            MultiGame.speed = speedBox.getValue();
            MultiGame.speed2 = speedBox.getValue();

            //Snake color 1 lekérése
            SingleGame.snakeColor = snakePicker1.getValue();
            MultiGame.snakeColor = snakePicker1.getValue();

            //Snake color 2 lekérése
            MultiGame.snakeColor2 = snakePicker2.getValue();

            //Food color 1,2,3 lekérése
            SingleGame.foodColor1 = foodPicker1.getValue();
            MultiGame.foodColor1 = foodPicker1.getValue();
            SingleGame.foodColor2 = foodPicker2.getValue();
            MultiGame.foodColor2 = foodPicker2.getValue();
            SingleGame.foodColor3 = foodPicker3.getValue();
            MultiGame.foodColor3 = foodPicker3.getValue();

            //Wall toggle lekérése
            SingleGame.fal = wallToggle.isSelected();
            MultiGame.fal = wallToggle.isSelected();


        });
    }
}
