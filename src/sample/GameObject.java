package sample;

import javafx.geometry.Point2D;
import javafx.scene.Node;

public abstract class GameObject {

    protected Node view;
    private Point2D velocity = new Point2D(0, 0);

    private boolean alive = true;

    public GameObject(Node view) {
        this.view = view;
    }

    public void update() {
        view.setTranslateX(view.getTranslateX() + velocity.getX());
        view.setTranslateY(view.getTranslateY() + velocity.getY());
    }
    public double getX() { return view.getTranslateX(); }
    public double getY() { return view.getTranslateY(); }

    public void setX(double x) {
        this.view.setTranslateX(x);
    }

    public void setY(double y) {
        this.view.setTranslateY(y);
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public Point2D getVelocity() {
        return velocity;
    }

    public Node getView() {
        return view;
    }

    public boolean isDead() {
        return !alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public double getRotate() {
        return view.getRotate();
    }

    public void rotateRight() {
        view.setRotate(view.getRotate() + 5);
        setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
    }

    public void rotateLeft() {
        view.setRotate(view.getRotate() - 5);
        setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
    }

    public boolean isColliding(GameObject other) {
        return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());
    }
    public boolean isCollidingWithArena(double x, double y, double width, double height){
        return getView().getBoundsInParent().intersects(x,y,width,height);
    }

    public boolean isCollidingWithObject(GameObject x) {
        return this.getX() < x.getX() + 5 && x.getX() > this.getX() + 5 &&
                this.getY() < x.getY() + 5 && x.getY() > this.getY() + 5;
    }

}