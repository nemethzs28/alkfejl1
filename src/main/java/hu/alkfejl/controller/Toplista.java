package hu.alkfejl.controller;

import hu.alkfejl.dao.MultiPlayerDAO;
import hu.alkfejl.dao.MultiPlayerDAOImpl;
import hu.alkfejl.dao.SinglePlayerDAO;
import hu.alkfejl.dao.SinglePlayerDAOImpl;
import hu.alkfejl.model.MultiPlayer;
import hu.alkfejl.model.SinglePlayer;
import hu.alkfejl.view.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Toplista implements Initializable {
    SinglePlayerDAO dao = new SinglePlayerDAOImpl();
    MultiPlayerDAO dao2 = new MultiPlayerDAOImpl();
    @FXML
    private BorderPane rootPane;

    @FXML
    private TableView<SinglePlayer> singlePlayerTableView;
    @FXML
    private TableView<MultiPlayer> multiPlayerTableView;

    @FXML
    private TableColumn<SinglePlayer, String> nameColumn;
    @FXML
    private TableColumn<SinglePlayer, String> scoreColumn;
    @FXML
    private TableColumn<SinglePlayer, Void> actionsColumn1;
    @FXML
    private TableColumn<MultiPlayer, Void> actionsColumn2;
    @FXML
    private TableColumn<MultiPlayer, String> name1Column;
    @FXML
    private TableColumn<MultiPlayer, String> score1Column;
    @FXML
    private TableColumn<MultiPlayer, String> score2Column;
    @FXML
    private TableColumn<MultiPlayer, String> name2Column;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        refreshTable();

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
        actionsColumn1.setCellFactory(e -> new TableCell<>(){
            private final Button editBtn = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");
            {
                deleteBtn.setOnAction(e -> {
                    SinglePlayer s = getTableRow().getItem();
                    deleteSinglePlayer(s);
                    refreshTable();
                });
                editBtn.setOnAction(e -> {
                    SinglePlayer s = getTableRow().getItem();
                    App.editStage = new Stage();
                    App.editStage.setTitle("EDIT");
                    VBox root5 = new VBox();
                    HBox nameField = new HBox();
                    HBox scoreField = new HBox();
                    Label nameLabel = new Label("Name:");
                    Label scoreLabel = new Label("Score:");
                    TextField nameTextField = new TextField();
                    TextField scoreTextField = new TextField();
                    nameLabel.setPadding(new Insets(0,10,0,0));
                    scoreLabel.setPadding(new Insets(0,10,0,0));
                    nameField.setPadding(new Insets(10,0,20,0));
                    nameField.getChildren().addAll(nameLabel, nameTextField);
                    scoreField.getChildren().addAll(scoreLabel, scoreTextField);
                    root5.getChildren().addAll(nameField, scoreField);
                    App.editScene = new Scene(root5, 300, 100);
                    App.editStage.setScene(App.editScene);
                    App.editStage.show();
                    App.editStage.setOnCloseRequest(event -> {
                        String name;
                        int score;
                        if(nameTextField.getText().equals("")) {
                            name = s.getName();
                        } else {
                            name=nameTextField.getText();
                        }
                        try {
                            score = Integer.parseInt(scoreTextField.getText());
                        } catch (NumberFormatException t) {
                            score = s.getScore();
                        }
                        dao.save(new SinglePlayer(s.getId(), name, score));
                        refreshTable();

                    });


                });

            }

            @Override
            protected void updateItem(Void s, boolean b) {
                super.updateItem(s, b);
                if(b) {
                    setGraphic(null);
                } else {
                    HBox container = new HBox();
                    container.getChildren().addAll(editBtn, deleteBtn);
                    container.setSpacing(10.0);
                    setGraphic(container);
                }
            }
        });

        name1Column.setCellValueFactory(new PropertyValueFactory<>("name1"));
        score1Column.setCellValueFactory(new PropertyValueFactory<>("score1"));
        score2Column.setCellValueFactory(new PropertyValueFactory<>("score2"));
        name2Column.setCellValueFactory(new PropertyValueFactory<>("name2"));
        actionsColumn2.setCellFactory(e -> new TableCell<>(){
            private final Button editBtn = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");
            {
                deleteBtn.setOnAction(e -> {
                    MultiPlayer m = getTableRow().getItem();
                    deleteMultiPlayer(m);
                    refreshTable();
                });
                editBtn.setOnAction(e -> {
                    MultiPlayer m = getTableRow().getItem();
                    App.editStage = new Stage();
                    App.editStage.setTitle("EDIT");
                    VBox root5 = new VBox();
                    HBox nameField1 = new HBox();
                    HBox scoreField1 = new HBox();
                    HBox scoreField2 = new HBox();
                    HBox nameField2 = new HBox();
                    Label nameLabel1 = new Label("Player 1 name:");
                    Label scoreLabel1 = new Label("Player 1 score:");
                    Label scoreLabel2 = new Label("Player 2 score:");
                    Label nameLabel2 = new Label("Player 2 name:");
                    TextField nameTextField1 = new TextField();
                    TextField scoreTextField1 = new TextField();
                    TextField scoreTextField2 = new TextField();
                    TextField nameTextField2 = new TextField();
                    nameLabel1.setPadding(new Insets(0,10,0,0));
                    scoreLabel1.setPadding(new Insets(0,10,0,0));
                    scoreLabel2.setPadding(new Insets(0,10,0,0));
                    nameLabel2.setPadding(new Insets(0,10,0,0));
                    nameField1.setPadding(new Insets(10,0,20,0));
                    scoreField2.setPadding(new Insets(20,0,20,0));
                    nameField1.getChildren().addAll(nameLabel1, nameTextField1);
                    scoreField1.getChildren().addAll(scoreLabel1, scoreTextField1);
                    scoreField2.getChildren().addAll(scoreLabel2, scoreTextField2);
                    nameField2.getChildren().addAll(nameLabel2, nameTextField2);
                    root5.getChildren().addAll(nameField1, scoreField1, scoreField2, nameField2);
                    App.editScene = new Scene(root5, 300, 300);
                    App.editStage.setScene(App.editScene);
                    App.editStage.show();
                    App.editStage.setOnCloseRequest(event -> {
                        String name;
                        String name2;
                        int score;
                        int score2;
                        if(nameTextField1.getText().equals("")) {
                            name = m.getName1();
                        } else {
                            name=nameTextField1.getText();
                        }
                        try {
                            score = Integer.parseInt(scoreTextField1.getText());
                        } catch (NumberFormatException t) {
                            score = m.getScore1();
                        }
                        try {
                            score2 = Integer.parseInt(scoreTextField2.getText());
                        } catch (NumberFormatException t) {
                            score2 = m.getScore2();
                        }
                        if(nameTextField2.getText().equals("")) {
                            name2 = m.getName2();
                        } else {
                            name2 = nameTextField2.getText();
                        }
                        dao2.save(new MultiPlayer(m.getId(), name, score, score2, name2));
                        refreshTable();

                    });


                });

            }

            @Override
            protected void updateItem(Void s, boolean b) {
                super.updateItem(s, b);
                if(b) {
                    setGraphic(null);
                } else {
                    HBox container = new HBox();
                    container.getChildren().addAll(editBtn, deleteBtn);
                    container.setSpacing(10.0);
                    setGraphic(container);
                }
            }
        });
    }



    private void deleteSinglePlayer(SinglePlayer s) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this: " + s.getName() + " - " + s.getScore() + "?", ButtonType.YES, ButtonType.NO);
        confirmation.showAndWait().ifPresent(buttonType -> {
            if(buttonType.equals(ButtonType.YES)) {
                dao.delete(s);
            }
        });

    }
    private void deleteMultiPlayer(MultiPlayer m) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this: " + m.getName1() + " - " + m.getScore1() + " - " + m.getScore2() + " - " + m.getName2() + "?", ButtonType.YES, ButtonType.NO);
        confirmation.showAndWait().ifPresent(buttonType -> {
            if(buttonType.equals(ButtonType.YES)) {
                dao2.delete(m);
            }
        });

    }


    private void refreshTable() {
        singlePlayerTableView.getItems().setAll(dao.findAll());
        multiPlayerTableView.getItems().setAll(dao2.findAll());
    }


    public void onBack(ActionEvent actionEvent) {
        App.stage.setScene(App.mainWindowScene);
    }

}

