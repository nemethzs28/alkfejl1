package hu.alkfejl.controller;

import hu.alkfejl.dao.SinglePlayerDAO;
import hu.alkfejl.dao.SinglePlayerDAOImpl;
import hu.alkfejl.model.Corner;
import hu.alkfejl.model.SinglePlayer;
import hu.alkfejl.view.App;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SingleGame {
    static int score = 0;
    static String playerName;
    static Color snakeColor = Color.GREEN;
    static Color foodColor1 = Color.YELLOW;
    static Color foodColor2 = Color.PINK;
    static Color foodColor3 = Color.ORANGE;
    static boolean fal = false;
    static int speed = 5;
    static int foodcolor = 0;
    static int tableSize = 20;
    static int foodX = 0;
    static int foodY = 0;
    static int cornersize = 25;
    static boolean gameOver = false;
    static Random rand = new Random();
    private static List<Corner> snake = new ArrayList<>();
    static Dir direction = Dir.left;
    static int prevFood;
    static boolean ehseg = false;

    public enum Dir {
        left, right, up, down
    }

    public static void gameStart() {
        try {
            prevFood = newFood();

            VBox root = new VBox();
            Canvas c = new Canvas(tableSize * cornersize, tableSize * cornersize);
            GraphicsContext gc = c.getGraphicsContext2D();
            root.getChildren().add(c);

            new AnimationTimer() {
                long lastTick = 0;

                public void handle(long now) {
                    if (lastTick == 0) {
                        lastTick = now;
                        tick(gc);
                        return;
                    }

                    if (now - lastTick > 1000000000 / speed) {
                        lastTick = now;
                        if(!gameOver) {
                            tick(gc);
                        } else {
                            gc.setFill(Color.RED);
                            gc.setFont(new Font("", 50));
                            gc.fillText("GAME OVER", ((double)tableSize/2 * 19), ((double)tableSize/2 * 23));
                            SinglePlayerDAOImpl dao = new SinglePlayerDAOImpl();
                            dao.save(new SinglePlayer(playerName, score));
                            stop();
                        }
                    }
                }

            }.start();

            App.singleScene = new Scene(root, tableSize * cornersize, tableSize * cornersize);
            // control
            App.singleScene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
                if (key.getCode() == KeyCode.W) {
                    direction = Dir.up;
                }
                if (key.getCode() == KeyCode.A) {
                    direction = Dir.left;
                }
                if (key.getCode() == KeyCode.S) {
                    direction = Dir.down;
                }
                if (key.getCode() == KeyCode.D) {
                    direction = Dir.right;
                }

            });

            // add start snake parts
            snake.add(new Corner(tableSize / 2, tableSize / 2));
            snake.add(new Corner(tableSize / 2, tableSize / 2));
            snake.add(new Corner(tableSize / 2, tableSize / 2));

            App.stage.setScene(App.singleScene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void tick(GraphicsContext gc) {

        for (int i = snake.size() - 1; i >= 1; i--) {
            snake.get(i).setX(snake.get(i - 1).getX());
            snake.get(i).setY(snake.get(i - 1).getY());
        }

        switch (direction) {
            case up:
                snake.get(0).setY(snake.get(0).getY() - 1);
                if (fal && snake.get(0).getY()<= 0) {
                    gameOver = true;
                } else if (!fal && snake.get(0).getY()<0) {
                    int x = snake.get(0).getX();
                    snake.get(0).setY(tableSize);
                    snake.get(0).setX(x);
                }
                break;
            case down:
                snake.get(0).setY(snake.get(0).getY() + 1);
                if (fal && snake.get(0).getY() >= tableSize - 1) {
                    gameOver = true;
                } else if (!fal && snake.get(0).getY()>=tableSize) {
                    int x = snake.get(0).getX();
                    snake.get(0).setY(0);
                    snake.get(0).setX(x);
                }
                break;
            case left:
                snake.get(0).setX(snake.get(0).getX() - 1);
                if (fal && snake.get(0).getX() <= 0) {
                    gameOver = true;
                } else if (!fal && snake.get(0).getX() < 0) {
                    int y = snake.get(0).getY();
                    snake.get(0).setX(tableSize);
                    snake.get(0).setY(y);
                }
                break;
            case right:
                snake.get(0).setX(snake.get(0).getX() + 1);
                if (fal && snake.get(0).getX() >= tableSize-1) {
                    gameOver = true;
                } else if (!fal && snake.get(0).getX() >= tableSize) {
                    int y = snake.get(0).getY();
                    snake.get(0).setX(0);
                    snake.get(0).setY(y);
                }
                break;

        }

        // eat food
        if (foodX == snake.get(0).getX() && foodY == snake.get(0).getY()) {
            snake.add(new Corner(-1, -1));
            score += (prevFood + 1);
            if(prevFood == 1) {
                ehseg = true;
            }
            prevFood = newFood();
        }

        // self destroy
        for (int i = 1; i < snake.size(); i++) {
            if (!ehseg && (snake.get(0).getX() == snake.get(i).getX() && snake.get(0).getY() == snake.get(i).getY())) {
                gameOver = true;
            } else if(ehseg && (snake.get(0).getX() == snake.get(i).getX() && snake.get(0).getY() == snake.get(i).getY())) {
                snake.subList(i, snake.size()).clear();
                ehseg = false;
            }
        }

        // fill
        // background
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, tableSize * cornersize, tableSize * cornersize);

        // score
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("", 30));
        gc.fillText("Score: " + score, 10, 30);

        // random foodcolor
        Color cc = Color.WHITE;

        switch (foodcolor) {
            case 0:
                cc = Color.PURPLE; //kannibál
                break;
            case 1:
                cc = Color.LIGHTBLUE; //éhség
                break;
            case 2:
                cc = foodColor1; //standard
                break;
            case 3:
                cc = foodColor2;
                break;
            case 4:
                cc = foodColor3;
                break;
        }
        gc.setFill(cc);
        gc.fillOval(foodX * cornersize, foodY * cornersize, cornersize, cornersize);

        // snake
        for (Corner c : snake) {
            gc.setFill(Color.BLACK);
            gc.fillRect(c.getX() * cornersize, c.getY() * cornersize, cornersize - 1, cornersize - 1);
            gc.setFill(snakeColor);
            gc.fillRect(c.getX() * cornersize, c.getY() * cornersize, cornersize - 2, cornersize - 2);

        }

    }
    public static int newFood() {
        start: while (true) {
            foodX = rand.nextInt(tableSize);
            foodY = rand.nextInt(tableSize);

            for (Corner c : snake) {
                if (c.getX() == foodX && c.getY() == foodY) {
                    continue start;
                }
            }
            foodcolor = rand.nextInt(5);
            speed++;
            break;

        }
        return foodcolor;
    }

}
