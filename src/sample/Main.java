package sample;

import com.sun.jndi.toolkit.url.Uri;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import sample.GameObjectWithImg;
import sample.GameObject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private Pane root;

    private List<GameObject> bullets = new ArrayList<>();
    private List<GameObject> enemies = new ArrayList<>();

    private GameObject player;

    private Parent createContent() {
        root = new Pane();
        root.setPrefSize(1000, 1000);

        player = new Drone();
        player.setVelocity(new Point2D(10, 0));
        addGameObject(player, 30, 30);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                onUpdate();
            }
        };
        timer.start();

        return root;
    }

    private void addBullet(GameObject bullet, double x, double y) {
        bullets.add(bullet);
        addGameObject(bullet, x, y);
    }

    private void addEnemy(GameObject enemy, double x, double y) {
        enemies.add(enemy);
        addGameObject(enemy, x, y);
    }

    private void addGameObject(GameObject object, double x, double y) {
        object.getView().setTranslateX(x);
        object.getView().setTranslateY(y);
        root.getChildren().add(object.getView());
    }

    private void onUpdate() {
        for (GameObject bullet : bullets) {
            for (GameObject enemy : enemies) {
                if (bullet.isColliding(enemy)) {
                    bullet.setAlive(false);
                    enemy.setAlive(false);

                    root.getChildren().removeAll(bullet.getView(), enemy.getView());
                }
                //Top Collision

                if(bullet.isCollidingWithArena(0,-10,600,10)){
                bullet.setVelocity((new Point2D(-bullet.getVelocity().getY(),bullet.getVelocity().getX())));

               //Bottom Collision
                if(bullet.isCollidingWithArena(0,600,600,10)){
                    bullet.setVelocity((new Point2D(-bullet.getVelocity().getY(),bullet.getVelocity().getX())));
                }

              //Left Collision
                if(bullet.isCollidingWithArena(-10,0,10,600)){
                    bullet.setVelocity(new Point2D(bullet.getVelocity().getY(),-bullet.getVelocity().getX()));
                }

                //Right Collision
                    if(bullet.isCollidingWithArena(600,0,10,600)){
                        bullet.setVelocity(new Point2D((bullet.getVelocity().getY()),-bullet.getVelocity().getX()));
                    }
                }
            }
        }

        bullets.removeIf(GameObject::isDead);
        enemies.removeIf(GameObject::isDead);

        bullets.forEach(GameObject::update);
        enemies.forEach(GameObject::update);

        player.update();

        if (Math.random() < 0.02) {
            addEnemy(new Drone(), Math.random() * root.getPrefWidth(), Math.random() * root.getPrefHeight());
        }

    }

    private static class Bullet extends GameObjectWithImg {
        Bullet() {
            super(new Image(new File("bullet1.jpg").toURI().toString()));
        }
    }

    // ADDED - Custom 'Richard' class to show his face as a GameObject
    private static class Drone extends GameObjectWithImg {
        Drone() {
            super(new Image(new File("rjm.jpg").toURI().toString()));
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(createContent()));
        stage.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) {
                player.rotateLeft();
            } else if (e.getCode() == KeyCode.RIGHT) {
                player.rotateRight();
            } else if (e.getCode() == KeyCode.SPACE) {
                Bullet bullet = new Bullet();
                bullet.setVelocity(player.getVelocity().normalize().multiply(5));
                addBullet(bullet, player.getView().getTranslateX(), player.getView().getTranslateY());
            }
        });
        stage.show();
    }

    public static void main(String[] args) {
        System.out.println(new File(".").getAbsoluteFile());
        launch(args);
    }
}