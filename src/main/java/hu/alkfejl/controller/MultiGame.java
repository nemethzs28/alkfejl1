package hu.alkfejl.controller;

import hu.alkfejl.dao.MultiPlayerDAOImpl;
import hu.alkfejl.dao.SinglePlayerDAOImpl;
import hu.alkfejl.model.Corner;
import hu.alkfejl.model.MultiPlayer;
import hu.alkfejl.model.SinglePlayer;
import hu.alkfejl.view.App;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MultiGame {
    static int score = 0;
    static String playerName;
    static String playerName2;
    static Color snakeColor = Color.YELLOW;
    static Color snakeColor2 = Color.DARKRED;
    static Color foodColor1 = Color.YELLOW;
    static Color foodColor2 = Color.PINK;
    static Color foodColor3 = Color.ORANGE;
    static boolean fal = false;
    static int score2 = 0;
    static int speed = 5;
    static int speed2 = 6;
    static int foodcolor = 0;
    static int tableSize = 20;
    static int foodX = 0;
    static int foodY = 0;
    static int cornersize = 25;
    static boolean gameOver = false;
    static Random rand = new Random();
    private static List<Corner> snake = new ArrayList<>();
    private static List<Corner> snake2 = new ArrayList<>();
    static SingleGame.Dir direction = SingleGame.Dir.left;
    static SingleGame.Dir direction2 = SingleGame.Dir.left;
    static int prevFood = -1;
    static boolean kannibal1 = false;
    static boolean kannibal2 = false;
    static int kannibalCount1 = 0;
    static int kannibalCount2 = 0;
    static boolean ehseg1 = false;
    static boolean ehseg2 = false;
    static AnimationTimer anim1;
    static AnimationTimer anim2;

    public enum Dir {
        left, right, up, down
    }

    public static void gameStart() {
        try {
            prevFood = newFood();

            VBox root = new VBox();
            Canvas c = new Canvas(tableSize * cornersize, tableSize * cornersize);
            GraphicsContext gc = c.getGraphicsContext2D();
            GraphicsContext gc2 = c.getGraphicsContext2D();
            root.getChildren().addAll(c);
            anim1 = new AnimationTimer() {
                long lastTick = 0;

                public void handle(long now) {
                    if (lastTick == 0) {
                        lastTick = now;
                        tick(gc);
                        return;
                    }

                    if (now - lastTick > 1000000000 / speed) {
                        lastTick = now;
                        if (!gameOver) {
                            tick(gc);
                        } else {
                            gc.setFill(Color.RED);
                            gc.setFont(new Font("", 50));
                            gc.fillText("GAME OVER", ((double) tableSize / 2 * 19), ((double) tableSize / 2 * 23));
                            MultiPlayerDAOImpl dao = new MultiPlayerDAOImpl();
                            dao.save(new MultiPlayer(playerName, score, score2, playerName2));
                            stop();
                            anim2.stop();

                        }
                    }

                }

            };
            anim1.start();
            anim2 = new AnimationTimer() {
                long lastTick2 = 0;

                public void handle(long now) {
                    if (lastTick2 == 0) {
                        lastTick2 = now;
                        tick2(gc2);
                    }

                    if (now - lastTick2 > 1000000000 / speed2) {
                        lastTick2 = now;
                        if (!gameOver) {
                            tick2(gc2);
                        } else {
                            gc.setFill(Color.RED);
                            gc.setFont(new Font("", 50));
                            gc.fillText("GAME OVER", ((double) tableSize / 2 * 19), ((double) tableSize / 2 * 23));
                            MultiPlayerDAOImpl dao = new MultiPlayerDAOImpl();
                            dao.save(new MultiPlayer(playerName, score, score2, playerName2));
                            stop();
                            anim1.stop();

                        }
                    }
                }

            };
            anim2.start();

            App.multiScene = new Scene(root, tableSize * cornersize, tableSize * cornersize);
            // control
            App.multiScene.addEventFilter(KeyEvent.KEY_PRESSED, key -> {
                if (key.getCode() == KeyCode.W) {
                    if (direction == SingleGame.Dir.up) {
                        speed++;
                    } else if ((direction == SingleGame.Dir.down) && speed > 6) {
                        speed--;
                    } else if (direction != SingleGame.Dir.down) {
                        direction = SingleGame.Dir.up;
                    }
                }
                if (key.getCode() == KeyCode.A) {
                    if (direction == SingleGame.Dir.left) {
                        speed++;
                    } else if ((direction == SingleGame.Dir.right) && speed > 6) {
                        speed--;
                    } else if (direction != SingleGame.Dir.right) {
                        direction = SingleGame.Dir.left;
                    }
                }
                if (key.getCode() == KeyCode.S) {
                    if (direction == SingleGame.Dir.down) {
                        speed++;
                    } else if ((direction == SingleGame.Dir.up) && speed > 6) {
                        speed--;
                    } else if (direction != SingleGame.Dir.up) {
                        direction = SingleGame.Dir.down;
                    }
                }
                if (key.getCode() == KeyCode.D) {
                    if (direction == SingleGame.Dir.right) {
                        speed++;
                    } else if ((direction == SingleGame.Dir.left) && speed > 6) {
                        speed--;
                    } else if (direction != SingleGame.Dir.left) {
                        direction = SingleGame.Dir.right;
                    }
                }
                if (key.getCode() == KeyCode.UP) {
                    if (direction2 == SingleGame.Dir.up) {
                        speed2++;
                    } else if ((direction2 == SingleGame.Dir.down) && speed2 > 6) {
                        speed2--;
                    } else if (direction2 != SingleGame.Dir.down) {
                        direction2 = SingleGame.Dir.up;
                    }
                }
                if (key.getCode() == KeyCode.DOWN) {
                    if (direction2 == SingleGame.Dir.down) {
                        speed2++;
                    } else if ((direction2 == SingleGame.Dir.up) && speed2 > 6) {
                        speed2--;
                    } else if (direction2 != SingleGame.Dir.up) {
                        direction2 = SingleGame.Dir.down;
                    }
                }
                if (key.getCode() == KeyCode.LEFT) {
                    if (direction2 == SingleGame.Dir.left) {
                        speed2++;
                    } else if ((direction2 == SingleGame.Dir.right) && speed2 > 6) {
                        speed2--;
                    } else if (direction2 != SingleGame.Dir.right) {
                        direction2 = SingleGame.Dir.left;
                    }
                }
                if (key.getCode() == KeyCode.RIGHT) {
                    if (direction2 == SingleGame.Dir.right) {
                        speed2++;
                    } else if ((direction2 == SingleGame.Dir.left) && speed2 > 6) {
                        speed2--;
                    } else if (direction2 != SingleGame.Dir.left) {
                        direction2 = SingleGame.Dir.right;
                    }
                }

            });

            // add start snake parts
            snake.add(new Corner(tableSize / 2, tableSize / 2));
            snake.add(new Corner(tableSize / 2, tableSize / 2));
            snake.add(new Corner(tableSize / 2, tableSize / 2));

            snake2.add(new Corner(tableSize / 3, tableSize / 3));
            snake2.add(new Corner(tableSize / 3, tableSize / 3));
            snake2.add(new Corner(tableSize / 3, tableSize / 3));

            App.stage.setScene(App.multiScene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void tick(GraphicsContext gc) {
        if (score + 10 < score2) {
            kannibal1 = false;
        }
        for (int i = snake.size() - 1; i >= 1; i--) {
            snake.get(i).setX(snake.get(i - 1).getX());
            snake.get(i).setY(snake.get(i - 1).getY());
        }

        switch (direction) {
            case up:
                snake.get(0).setY(snake.get(0).getY() - 1);
                if (fal && snake.get(0).getY() <= 0) {
                    gameOver = true;
                } else if (!fal && snake.get(0).getY() < 0) {
                    int x = snake.get(0).getX();
                    snake.get(0).setY(tableSize);
                    snake.get(0).setX(x);
                }
                break;
            case down:
                snake.get(0).setY(snake.get(0).getY() + 1);
                if (fal && snake.get(0).getY() >= tableSize - 1) {
                    gameOver = true;
                } else if (!fal && snake.get(0).getY() >= tableSize) {
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
                if (fal && snake.get(0).getX() >= tableSize - 1) {
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
            if (kannibal1) {
                kannibalCount1++;
                if (kannibalCount1 >= 2) {
                    kannibalCount1 = 0;
                    kannibal1 = false;
                }
            }
            snake.add(new Corner(-1, -1));
            score += (prevFood + 1);
            if (prevFood == 0 && !kannibal2 && score + 10 >= score2) {
                kannibalCount1 = 0;
                kannibal1 = true;
            } else if (prevFood == 1) {
                ehseg1 = true;
            }
            prevFood = newFood();
        }


        for (int i = 1; i < snake.size(); i++) {
            if (!ehseg1 && ((snake.get(0).getX() == snake.get(i).getX() && snake.get(0).getY() == snake.get(i).getY()))) {
                System.out.println("Snake2 nyert");
                gameOver = true;
                break;

            } else if (ehseg1 && (snake.get(0).getX() == snake.get(i).getX() && snake.get(0).getY() == snake.get(i).getY())) {
                snake.subList(i, snake.size()).clear();
                ehseg1 = false;
            }
            if ((snake2.get(0).getX() == snake.get(i).getX() && snake2.get(0).getY() == snake.get(i).getY()) ||
                    (snake2.get(0).getX() == snake.get(0).getX() && snake2.get(0).getY() == snake.get(0).getY())) {
                if (kannibal1) {
                    System.out.println("Snake1 nyert");
                } else if (kannibal2) {
                    System.out.println("Snake2 nyert");
                } else {
                    if (score > score2) {
                        System.out.println("Snake1 nyert");
                    } else if (score < score2) {
                        System.out.println("Snake2 nyert");
                    } else {
                        System.out.println("Döntetlen");
                    }
                }
                gameOver = true;
                break;
            }

        }
        // fill
        // background
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, tableSize * cornersize, tableSize * cornersize);
        // score
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("", 30));
        gc.fillText(playerName + " Score: " + score, 10, 30);
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("", 30));
        gc.fillText(playerName2 + " Score: " + score2, 10, 80);

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
        for (Corner c : snake2) {
            gc.setFill(Color.BLACK);
            gc.fillRect(c.getX() * cornersize, c.getY() * cornersize, cornersize - 1, cornersize - 1);
            gc.setFill(snakeColor2);
            gc.fillRect(c.getX() * cornersize, c.getY() * cornersize, cornersize - 2, cornersize - 2);
        }
    }

    public static void tick2(GraphicsContext gc2) {
        if (score2 + 10 < score) {
            kannibal2 = false;
        }
        for (int i = snake2.size() - 1; i >= 1; i--) {
            snake2.get(i).setX(snake2.get(i - 1).getX());
            snake2.get(i).setY(snake2.get(i - 1).getY());
        }

        switch (direction2) {
            case up:
                snake2.get(0).setY(snake2.get(0).getY() - 1);
                if (fal && snake2.get(0).getY() <= 0) {
                    gameOver = true;
                } else if (!fal && snake2.get(0).getY() < 0) {
                    int x = snake2.get(0).getX();
                    snake2.get(0).setY(tableSize);
                    snake2.get(0).setX(x);
                }
                break;
            case down:
                snake2.get(0).setY(snake2.get(0).getY() + 1);
                if (fal && snake2.get(0).getY() >= tableSize - 1) {
                    gameOver = true;
                } else if (!fal && snake2.get(0).getY() >= tableSize) {
                    int x = snake2.get(0).getX();
                    snake2.get(0).setY(0);
                    snake2.get(0).setX(x);
                }
                break;
            case left:
                snake2.get(0).setX(snake2.get(0).getX() - 1);
                if (fal && snake2.get(0).getX() <= 0) {
                    gameOver = true;
                } else if (!fal && snake2.get(0).getX() < 0) {
                    int y = snake2.get(0).getY();
                    snake2.get(0).setX(tableSize);
                    snake2.get(0).setY(y);
                }
                break;
            case right:
                snake2.get(0).setX(snake2.get(0).getX() + 1);
                if (fal && snake2.get(0).getX() >= tableSize - 1) {
                    gameOver = true;
                } else if (!fal && snake2.get(0).getX() >= tableSize) {
                    int y = snake2.get(0).getY();
                    snake2.get(0).setX(0);
                    snake2.get(0).setY(y);
                }
                break;

        }

        // eat food
        if (foodX == snake2.get(0).getX() && foodY == snake2.get(0).getY()) {
            if (kannibal2) {
                kannibalCount2++;
                if (kannibalCount2 >= 2) {
                    kannibalCount2 = 0;
                    kannibal2 = false;
                }
            }
            snake2.add(new Corner(-1, -1));
            score2 += (prevFood + 1);
            if (prevFood == 0 && !kannibal1 && score2 + 10 >= score) {
                kannibalCount2 = 0;
                kannibal2 = true;
            } else if (prevFood == 1) {
                ehseg2 = true;
            }
            prevFood = newFood2();
        }
        //mozgas
        for (int i = 1; i < snake2.size(); i++) {
            if (!ehseg2 && ((snake2.get(0).getX() == snake2.get(i).getX() && snake2.get(0).getY() == snake2.get(i).getY()))) {
                gameOver = true;
                System.out.println("Snake1 nyert");
                break;
            } else if (ehseg2 && (snake2.get(0).getX() == snake2.get(i).getX() && snake2.get(0).getY() == snake2.get(i).getY())) {
                snake2.subList(i, snake2.size()).clear();
                ehseg2 = false;
            }
            if((snake.get(0).getX() == snake2.get(i).getX() && snake.get(0).getY() == snake2.get(i).getY()) ||
                    (snake.get(0).getX() == snake2.get(0).getX() && snake.get(0).getY() == snake2.get(0).getY())) {
                if (kannibal1) {
                    System.out.println("Snake1 nyert");
                } else if (kannibal2) {
                    System.out.println("Snake2 nyert");
                } else {
                    if (score > score2) {
                        System.out.println("Snake1 nyert");
                    } else if (score < score2) {
                        System.out.println("Snake2 nyert");
                    } else {
                        System.out.println("Döntetlen");
                    }
                    gameOver = true;
                    break;
                }
            }
        }
        // fill
        // background
        gc2.setFill(Color.BLACK);
        gc2.fillRect(0, 0, tableSize * cornersize, tableSize * cornersize);
        // score
        gc2.setFill(Color.WHITE);
        gc2.setFont(new Font("", 30));
        gc2.fillText(playerName + " Score: " + score, 10, 30);
        gc2.setFill(Color.WHITE);
        gc2.setFont(new Font("", 30));
        gc2.fillText(playerName2 + " Score: " + score2, 10, 80);

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
        gc2.setFill(cc);
        gc2.fillOval(foodX * cornersize, foodY * cornersize, cornersize, cornersize);

        for (Corner c : snake) {
            gc2.setFill(Color.BLACK);
            gc2.fillRect(c.getX() * cornersize, c.getY() * cornersize, cornersize - 1, cornersize - 1);
            gc2.setFill(snakeColor);
            gc2.fillRect(c.getX() * cornersize, c.getY() * cornersize, cornersize - 2, cornersize - 2);
        }

        for (Corner c : snake2) {
            gc2.setFill(Color.BLACK);
            gc2.fillRect(c.getX() * cornersize, c.getY() * cornersize, cornersize - 1, cornersize - 1);
            gc2.setFill(snakeColor2);
            gc2.fillRect(c.getX() * cornersize, c.getY() * cornersize, cornersize - 2, cornersize - 2);
        }
    }

    private static int newFood2() {
        start:
        while (true) {
            foodX = rand.nextInt(tableSize);
            foodY = rand.nextInt(tableSize);

            for (Corner c : snake2) {
                if (c.getX() == foodX && c.getY() == foodY) {
                    continue start;
                }
            }
            foodcolor = rand.nextInt(5);
            speed2++;
            break;

        }
        return foodcolor;
    }

    public static int newFood() {
        start:
        while (true) {
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

