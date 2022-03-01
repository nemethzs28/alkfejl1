package hu.alkfejl.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MultiPlayer {
    private IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private StringProperty name1 = new SimpleStringProperty(this, "name1");
    private StringProperty name2 = new SimpleStringProperty(this, "name2");
    private IntegerProperty score1 = new SimpleIntegerProperty(this, "score1");
    private IntegerProperty score2 = new SimpleIntegerProperty(this, "score2");

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName1() {
        return name1.get();
    }

    public StringProperty name1Property() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1.set(name1);
    }

    public String getName2() {
        return name2.get();
    }

    public StringProperty name2Property() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2.set(name2);
    }

    public int getScore1() {
        return score1.get();
    }

    public IntegerProperty score1Property() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1.set(score1);
    }

    public int getScore2() {
        return score2.get();
    }

    public IntegerProperty score2Property() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2.set(score2);
    }

    public MultiPlayer() {
    }

    public MultiPlayer(int id, String name1, int score1, int score2, String name2) {
        this.id = new SimpleIntegerProperty(id);
        this.name1 = new SimpleStringProperty(name1);
        this.name2 = new SimpleStringProperty(name2);
        this.score1 = new SimpleIntegerProperty(score1);
        this.score2 = new SimpleIntegerProperty(score2);
    }

    public MultiPlayer(String name1, int score1, int score2, String name2) {
        this.name1 = new SimpleStringProperty(name1);
        this.name2 = new SimpleStringProperty(name2);
        this.score1 = new SimpleIntegerProperty(score1);
        this.score2 = new SimpleIntegerProperty(score2);
    }
}
