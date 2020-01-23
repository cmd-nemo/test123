package uk.reading.hb000671.gui;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.Serializable;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;



public class SimulationScene extends Application implements Serializable {
    static int ARENA_WIDTH = 1280;
    static int ARENA_HEIGHT = 720;

    private Pane root;
    private BorderPane bP1;
    private boolean space_held = false;
    private boolean left_held = false;
    private boolean right_held = false;
    private List<Bullet> bullets = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private List<Sensor> sensors = new ArrayList<>();
    public AnimationTimer timer ;

    private GameObject player;
    MenuBar setMenu() {
        MenuBar menuBar = new MenuBar();						// create main menu

        Menu mFile = new Menu("File");							// add File main menu
        MenuItem mExit = new MenuItem("Exit");					// whose sub menu has Exit
        mExit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {					// action on exit is
                //timer.stop();									// stop timer
                System.exit(0);									// exit program
            }
        });
        MenuItem mPause = new MenuItem("Pause");					// whose sub menu has Exit
        mPause.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {					// action on exit is
                timer.stop();								// exit program
            }
        });
        MenuItem mPlay = new MenuItem("Play");					// whose sub menu has Exit
        mPlay.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                System.out.println("hello");// action on exit is
                timer.start();									// exit program
            }
        });
        ///
        MenuItem mSave = new MenuItem("Save");					// whose sub menu has Save
        mSave.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {					// action on exit is
                save();
            }
        });

        MenuItem mLoad = new MenuItem("Load");					// whose sub menu has Save
        mLoad.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {					// action on exit is
                load();
            }
        });
        mFile.getItems().addAll(mExit, mSave, mLoad);
        mFile.getItems().addAll(mExit, mPause, mPlay);							// add exit to File menu

        Menu mHelp = new Menu("Help");							// create Help menu
        MenuItem mAbout = new MenuItem("About");				// add About sub men item
        mAbout.setOnAction(actionEvent -> {
            //showAbout();									// and its action to print about
        });
        mHelp.getItems().addAll(mAbout);						// add About to Help main item

        menuBar.getMenus().addAll(mFile, mHelp);				// set main menu with File, Help
        return menuBar;											// return the menu
    }

    public void load() {
        String url = "simulation.txt";
        try {
            ObjectInputStream loader = new ObjectInputStream(new FileInputStream(url));
           root  = (Pane) loader.readObject();
            loader.close();
            onUpdate();										// redraw the world
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public void save() {
        String url = "simulation.txt";
        try {
            ObjectOutputStream saver = new ObjectOutputStream(new FileOutputStream(url));
            saver.writeObject(root);
            saver.close();
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    private Parent createContent() {
bP1 = new BorderPane();


        root = new Pane();
        root.setPrefSize(ARENA_WIDTH, ARENA_HEIGHT);
bP1.setCenter(root);
        player = new NewPlayer();
        player.setVelocity(new Point2D(1, 0));
        addGameObject(player, ARENA_WIDTH/2, ARENA_HEIGHT/2);

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        bP1.setTop(setMenu());
        timer.start();
        return bP1;
    }

    private void addBullet(Bullet bullet, double x, double y) {
        bullets.add(bullet);
        addGameObject(bullet, x, y);
    }

    private void addEnemy(Enemy enemy, double x, double y) {
        enemies.add(enemy);
        addGameObject(enemy, x, y);
    }

    private void addGameObject(GameObject object, double x, double y) {
        object.getView().setTranslateX(x);
        object.getView().setTranslateY(y);
        root.getChildren().add(object.getView());
    }

    private void addObstacle(Sensor sensor, double x, double y){
        sensor.setX(x);
        sensor.setY(y);
        sensors.add(sensor);
        root.getChildren().add(sensor.getView());
    }

    private void bounceOffArena(GameObject obj) {
        if(obj.isCollidingWithArena(0,-10,ARENA_WIDTH,10)) {
            obj.setVelocity((new Point2D(-obj.getVelocity().getY(), obj.getVelocity().getX())));
        }
        //Bottom Collision
        if(obj.isCollidingWithArena(0,ARENA_HEIGHT,ARENA_WIDTH,10)){
            obj.setVelocity((new Point2D(-obj.getVelocity().getY(),obj.getVelocity().getX())));
        }

        //Left Collision
        if(obj.isCollidingWithArena(-10,0,10,ARENA_HEIGHT)){
            obj.setVelocity(new Point2D(obj.getVelocity().getY(),-obj.getVelocity().getX()));
        }

        //Right Collision
        if(obj.isCollidingWithArena(ARENA_WIDTH,0,10,ARENA_HEIGHT)){
            obj.setVelocity(new Point2D((obj.getVelocity().getY()),-obj.getVelocity().getX()));
        }
    }


    public void onUpdate() {
        if (left_held) {
            player.rotateLeft();
        }

        if (right_held) {
            player.rotateRight();
        }

        if (space_held) {
            Bullet bullet = new Bullet();
            bullet.setVelocity(player.getVelocity().normalize().multiply(5));
            addBullet(bullet, player.getView().getTranslateX(), player.getView().getTranslateY());
        }

        for (GameObject bullet : bullets) {
            for (GameObject enemy : enemies) {

                if (bullet.isColliding(enemy)) {
                    bullet.setAlive(false);
                    enemy.setAlive(false);


                }


                if (bullet.isColliding(enemy)) {
                    bullet.setAlive(false);
                    root.getChildren().removeAll(bullet.getView(), enemy.getView());
                    bullet.setVelocity(
                            new Point2D(
                                    -bullet.getVelocity().getX(), -bullet.getVelocity().getY()
                            )
                    );
                    for (Sensor o : sensors) {
                        o.onCollide();
                    }
                }
                //Top Collision
            }
            bounceOffArena(bullet);

        }

        bullets.removeIf(GameObject::isDead);
        enemies.removeIf(GameObject::isDead);

        bullets.forEach(GameObject::update);
        enemies.forEach(GameObject::update);


       player.update();

        if (Math.random() < 0.1) {
            addEnemy(new Enemy(), Math.random() * root.getPrefWidth(), Math.random() * root.getPrefHeight());
        }

        if (Math.random() < 0.01){
            addObstacle(new Sensor(), Math.random() * root.getPrefWidth(), Math.random() * root.getPrefHeight());
        }

        if (player.getX() <= 0) {
            player.setX(ARENA_WIDTH);
        } else if (player.getX() > ARENA_WIDTH) {
            player.setX(0);
        }

        if (player.getY() <= 0) {
            player.setY(ARENA_HEIGHT);
        } else if (player.getY() > ARENA_HEIGHT) {
            player.setY(0);
        }



    }

    private static class Enemy extends GameObjectWithImg {
        Enemy() {
            super(new Image(new File("rjm3.png").toURI().toString()));
        }
    }

    private static class Bullet extends GameObjectWithImg {
        Bullet() {
            super(new Image(new File("rjm2.png").toURI().toString()));
        }
    }

    // ADDED - Custom 'Richard' class to show his face as a GameObject
    private static class NewPlayer extends GameObjectWithImg {
        NewPlayer() {
            super(new Image(new File("rjm1.png").toURI().toString()));
        }
    }
    private static class Sensor extends GameObjectWithImg{
        Sensor(){ super(new Image(new File("obs2.png").toURI().toString()));
        }

        public void onCollide() {
            Lighting c = new Lighting();
            c.setLight(new Light.Distant(45, 45, Color.HOTPINK));
            ((ImageView) this.view).setEffect(c);
        }

    }

    @Override
    public void start(Stage stage) throws Exception {


        stage.setScene(new Scene(createContent()));
        stage.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) {
                left_held = true;
                //player.rotateLeft();
            } else if (e.getCode() == KeyCode.RIGHT) {
                right_held = true;
            } else if (e.getCode() == KeyCode.SPACE) {
                space_held = true;

            }
        });

        stage.getScene().setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.LEFT) {
                left_held = false;
                //player.rotateLeft();
            } else if (e.getCode() == KeyCode.RIGHT) {
                right_held = false;
                //player.rotateRight();
            } else if (e.getCode() == KeyCode.SPACE) {
                space_held = false;
            }
        });
        stage.show();
    }

    public static void main(String[] args) {
        System.out.println(new File(".").getAbsoluteFile());
        launch(args);
    }
}