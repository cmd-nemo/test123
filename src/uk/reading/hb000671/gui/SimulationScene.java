package uk.reading.hb000671.gui; //generated package name for the respected project
//required imports

import javafx.animation.AnimationTimer; //javafx AnimationTimer
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

/**
 * @author hb000671
 * Class that handles the Game UI within canvas, with callbacks to the main interface and canvas.
 */
public class SimulationScene extends Application implements Serializable { //functiom serializable used for load and save
    private static int ARENA_WIDTH = 1280; //arena width
    private static int ARENA_HEIGHT = 720; //arena height
    /**
     * Class extends serializable
     * Declares the access modifiers required for setting the arena H and W
     *
     * @ARENA_WIDTH is the arena width
     * @ARENA_HEIGHT is the arena height
     */
    //ACCESS MODIFIERS FOR BASE FUNCTIONS
    private Pane root;
    private BorderPane bP1; //defines new border pane
    private boolean space_held = false; //key down event
    private boolean left_held = false; //key down event
    private boolean right_held = false; //key down event

    private List<Bullets> bullets = new ArrayList<>(); //array list of game objecta
    private List<Enemy> enemies = new ArrayList<>();
    private List<Sensor> sensors = new ArrayList<>();
    public AnimationTimer timer; //game loop
    /**
     * the Pane is declared from the too, border pane instantiates the outer canvas
     *
     * @bP1 is the border pane within the root Of the Pane
     * @root is instantiating the top of the menuBar
     * @space_held on key press increases miniDrone
     * @left_held on key press increases miniDrone
     * @right_held on key press increases miniDrone
     */
    private GameObject player;

    MenuBar setMenu() {
        MenuBar menuBar = new MenuBar();                        // creates a main menu

        Menu mFile = new Menu("File");                            // add File main menu
        MenuItem mExit = new MenuItem("Exit");                    //includes the ability to exit the window
        mExit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {                    // action on exit is
                //timer.stop();									// stop timer
                System.exit(0);                                    // exit program
            }
        });
        MenuItem mPause = new MenuItem("Pause");                    // menu has option to pause the simulation
        mPause.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {                    // action is pause
                timer.stop();                                // timer stops
            }
        });
        MenuItem mPlay = new MenuItem("Play");                //Plays the simulation
        mPlay.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                System.out.println("hello");//test output to see if button worked
                timer.start();                                    // game loop starts
            }
        });
        ///
        MenuItem mSave = new MenuItem("Save");                    // saves the simulation menu tab
        mSave.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {                    //saves the simulation on click.
                save();
            }
        });

        MenuItem mLoad = new MenuItem("Load");                    // loads the simlation on play.
        mLoad.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {                    // action on play is to reload saved arena.
                load();
            }
        });
        mFile.getItems().addAll(mExit, mSave, mLoad);
        mFile.getItems().addAll(mExit, mPause, mPlay);                            // add exit to File menu

        Menu mHelp = new Menu("Help");                            // creates the Help menu
        MenuItem mAbout = new MenuItem("About");                // adds about to the menu list
        mAbout.setOnAction(actionEvent -> {
            //showAbout();									// and its action to print about
        });
        mHelp.getItems().addAll(mAbout);                        // add About to Help main item

        menuBar.getMenus().addAll(mFile, mHelp);                // set main menu with File, about
        return menuBar;                                            // return the menu
    }

    /**
     * This method is used to set the menu layout with the relevant tabs
     * simplest form of a class method with various call backs.
     * Implements serializable to load and save files
     *
     * @return menuBar;
     */

    public void load() {
        String url = "DroneSimulation.txt"; //output file on load
        try {
            ObjectInputStream loader = new ObjectInputStream(new FileInputStream(url));
            root = (Pane) loader.readObject();
            loader.close();
            onUpdate();                                        //runs the game loop onupdate
        } catch (Exception e) { //includes exceptions on load
            System.out.println(e); //prints out the exception statement
        }
    }

    /**
     * Loads the file
     * implements a Try Catch exception handler
     * implements serializable
     */

    public void save() {
        String url = "DroneSimulation.txt"; //url is output file on save
        try {
            ObjectOutputStream saver = new ObjectOutputStream(new FileOutputStream(url));
            saver.writeObject(root);
            saver.close();
        } catch (Exception e) {
            System.out.println(e); //outputs the exception statement
        }
    }

    /**
     * Saves the file
     * implements serializable
     * implements a Try Catch exception handler
     */


    /***
     * method createContent is used to create the game objects
     * sets the borderpane
     * @return bP1
     */
    private Parent createContent() {
        bP1 = new BorderPane();


        root = new Pane();
        root.setPrefSize(ARENA_WIDTH, ARENA_HEIGHT);
        bP1.setCenter(root);
        player = new NewPlayer();
        player.setVelocity(new Point2D(1, 0));
        addGameObject(player, ARENA_WIDTH / 2, ARENA_HEIGHT / 2);

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

    /***
     * method to add bullet
     * @param bullets
     * @param x
     * @param y
     * adds eaach game object
     */
    private void addBullet(Bullets bullets, double x, double y) {
        this.bullets.add(bullets);
        addGameObject(bullets, x, y);
    }

    /***
     * method for adding enemy
     * same as method for adding bullets
     * @param enemy
     * @param x
     * @param y
     */
    private void addEnemy(Enemy enemy, double x, double y) {
        enemies.add(enemy);
        addGameObject(enemy, x, y);
    }

    /***
     * method that adds all Game objects through abstract class
     * @param object
     * @param x
     * @param y
     */
    private void addGameObject(GameObject object, double x, double y) {
        object.getView().setTranslateX(x);
        object.getView().setTranslateY(y);
        root.getChildren().add(object.getView());
    }

    /***
     * method add obstacle adds the sensor drones that change colour on hit of bullet
     * @param sensor
     * @param x
     * @param y
     */
    private void addObstacle(Sensor sensor, double x, double y) {
        sensor.setX(x);
        sensor.setY(y);
        sensors.add(sensor);
        root.getChildren().add(sensor.getView());
    }

    /***
     * deflection method to allow deflection of bullets against arena walls
     * @param obj
     */
    private void bounceOffArena(GameObject obj) {
        // collision from the top wall with the bullets.
        if (obj.isCollidingWithArena(0, -10, ARENA_WIDTH, 10)) {
            obj.setVelocity((new Point2D(-obj.getVelocity().getY(), obj.getVelocity().getX())));
        }
        //Bottom Collision of walls with bullets
        if (obj.isCollidingWithArena(0, ARENA_HEIGHT, ARENA_WIDTH, 10)) {
            obj.setVelocity((new Point2D(-obj.getVelocity().getY(), obj.getVelocity().getX())));
        }

        //Left  of walls with bullets
        if (obj.isCollidingWithArena(-10, 0, 10, ARENA_HEIGHT)) {
            obj.setVelocity(new Point2D(obj.getVelocity().getY(), -obj.getVelocity().getX()));
        }

        //Right Collision of walls with bullets
        if (obj.isCollidingWithArena(ARENA_WIDTH, 0, 10, ARENA_HEIGHT)) {
            obj.setVelocity(new Point2D((obj.getVelocity().getY()), -obj.getVelocity().getX()));
        }
    }

    /**
     * game loop on update calls the key board entries
     * calls the objects
     * and direction
     */
    public void onUpdate() { //method on update
        if (left_held) { //player rotates left if left key
            player.rotateLeft();
        }

        if (right_held) {
            player.rotateRight(); //player rotates right if right key
        }

        if (space_held) {
            Bullets bullets = new Bullets(); //player shoots bullets from normalized vector if space bar
            bullets.setVelocity(player.getVelocity().normalize().multiply(5));
            addBullet(bullets, player.getView().getTranslateX(), player.getView().getTranslateY());
        }

        for (GameObject bullet : bullets) { //for loop of bullets
            for (GameObject enemy : enemies) { //for loop of enemies

                if (bullet.isColliding(enemy)) { //if bullet collides with enemy, then enemy is dead
                    bullet.setAlive(false);
                    enemy.setAlive(false);


                }


                if (bullet.isColliding(enemy)) {
                    bullet.setAlive(false); //if bullet collides with enemy, then enemy is dead
                    root.getChildren().removeAll(bullet.getView(), enemy.getView()); //removes the bullet if the enemy is dead
                    bullet.setVelocity(
                            new Point2D(
                                    -bullet.getVelocity().getX(), -bullet.getVelocity().getY() //vector of bullets
                            )
                    );
                    for (Sensor o : sensors) { //for loop for Class Sensor
                        o.onCollide();  //if object collides with sensor , then calls method onCollide
                    }
                }

            }
            bounceOffArena(bullet); //calls method bounceOFfArena, so that bullets deflect off the arena

        }

        bullets.removeIf(GameObject::isDead);
        enemies.removeIf(GameObject::isDead); //remove the bullets if the enemy is dead

        bullets.forEach(GameObject::update);
        enemies.forEach(GameObject::update);

        player.update(); //player movement naed on eveents in update method

        if (Math.random() < 0.01) { //adds enemies based on 0.01% certainty, completely randomized
            addEnemy(new Enemy(), Math.random() * root.getPrefWidth(), Math.random() * root.getPrefHeight());
        }

        if (Math.random() < 0.001) { //adds the sensor drones based on 0.01% certainty, random locstions
            addObstacle(new Sensor(), Math.random() * root.getPrefWidth(), Math.random() * root.getPrefHeight());
        }

        if (player.getX() <= 0) {
            player.setX(ARENA_WIDTH); //allows player to spawn back in opposite side from the side left
        } else if (player.getX() > ARENA_WIDTH) {
            player.setX(0);
        }

        if (player.getY() <= 0) {
            player.setY(ARENA_HEIGHT);
        } else if (player.getY() > ARENA_HEIGHT) {
            player.setY(0);
        }


    }

    /**
     * class Enemy extends to GameObjectWithImage
     */
    private static class Enemy extends GameObjectWithImg {
        Enemy() {
            super(new Image(new File("enem4.png").toURI().toString())); //creates an image of the respective type for enemy
        }// definition for calling a method defined in the superclass.
    }

    /***
     * class bullets extends to GameObjectWithImg
     */

    private static class Bullets extends GameObjectWithImg {
        Bullets() {  //creates an image of the respective type for bullets
            super(new Image(new File("mini2.png").toURI().toString())); // definition for calling a method defined in the superclass.
        }
    }

    /***
     * class NewPlayer extends GameObjectWithImg
     */
    private static class NewPlayer extends GameObjectWithImg {
        NewPlayer() { //creates an image of the respective type for player
            super(new Image(new File("dronePly.png").toURI().toString())); // definition for calling a method defined in the superclass.
        }
    }

    /**
     * creates an image of the sensor drone extending from GameObjectWithImg
     */
    private static class Sensor extends GameObjectWithImg {
        Sensor() {
            super(new Image(new File("sens1.png").toURI().toString()));
        }// definition for calling a method defined in the superclass.

        public void onCollide() { //on collision of bullet and obstacle sensor drone
            Lighting c = new Lighting(); //changes color to hot pink
            c.setLight(new Light.Distant(45, 45, Color.HOTPINK));
            ((ImageView) this.view).setEffect(c); //implements java lighting effects
        }

    }

    /***
     * start method that implements the scene and canvas
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {


        stage.setScene(new Scene(createContent()));
        stage.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) { //player movement left
                left_held = true;
                //player.rotateLeft();
            } else if (e.getCode() == KeyCode.RIGHT) { //player movement right
                right_held = true;
            } else if (e.getCode() == KeyCode.SPACE) { //spacebar shoots miniDrones
                space_held = true;

            }
        });

        stage.getScene().setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.LEFT) {
                left_held = false;
                //player.rotateLeft();
            } else if (e.getCode() == KeyCode.RIGHT) { //on key down increases the number of bullets available
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
