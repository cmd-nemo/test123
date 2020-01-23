package uk.reading.hb000671.gui;
//required imports

import javafx.geometry.Point2D;
import javafx.scene.Node;

import java.io.Serializable;

/**
 * @author hb000671
 * Class that handles the abstract class GameObject and implements Serializable.
 */

public abstract class GameObject implements Serializable {

    protected Node view; //Accessor method to get a reference to the next node in the view
    private Point2D velocity = new Point2D(0, 0);
    //A 2D geometric point that represents the x, y coordinates.
//It also represents a relative magnitude vector
    private boolean alive = true; //for if the enemy is not hit by bullet

    public GameObject(Node view) { //new abstract node within game object
        this.view = view;
    }

    public void update() {
        view.setTranslateX(view.getTranslateX() + velocity.getX()); //direction of player movement called on update
        view.setTranslateY(view.getTranslateY() + velocity.getY());
    }

    /**
     * method update
     *
     * @return view of the vector translating x and y
     */
    public double getX() {
        return view.getTranslateX();
    }

    public double getY() {
        return view.getTranslateY();
    }

    /**
     * method to set the x value
     *
     * @param x
     */
    public void setX(double x) { //sets the X value
        this.view.setTranslateX(x);
    }

    /**
     * method to set the y value
     *
     * @param y  double: y takes an GPS locationvjhlj
     */
    public void setY(double y) {
        this.view.setTranslateY(y); //Sets the y value
    }

    /**
     * method for setting the velocity
     *
     * @param velocity Poit2D:
     */
    public void setVelocity(Point2D velocity) { //sets the velocity based on the vector
        this.velocity = velocity;
    }

    /**
     * method setVelocity based on the vector
     *
     * @return velocity
     */

    public Point2D getVelocity() {
        return velocity;
    }

    /**
     * method to get View from node
     *
     * @return view
     */
    public Node getView() {
        return view;
    }

    /**
     * if bullet does not hit the enemy then not dead
     *
     * @return alive;
     */
    public boolean isDead() {
        return !alive;
    }

    /**
     * method set alive
     * if player is alive
     *
     * @param alive
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * method for getting the direction based of rotate
     *
     * @return
     */

    public double getRotate() {
        return view.getRotate();
    }


    public void rotateRight() {
        view.setRotate(view.getRotate() + 5);
        setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
    }//rotates the player right on key down

    public void rotateLeft() {
        view.setRotate(view.getRotate() - 5);
        setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate()))));
    } //rotates the player left on key down

    public boolean isColliding(GameObject other) {
        return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());
    }//if bullet is colliding with object, then deflect

    /**
     * checks if bullets are colliding within bounds of arena and deflect
     *
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    public boolean isCollidingWithArena(double x, double y, double width, double height) {
        return getView().getBoundsInParent().intersects(x, y, width, height);
    }

    /**
     * method for if anything is colliding with the arena
     *
     * @param x
     * @return
     */

    public boolean isCollidingWithObject(GameObject x) {
        return this.getX() < x.getX() + 5 && x.getX() > this.getX() + 5 && //checks if snything collides with sensors
                // and allows deflection
                this.getY() < x.getY() + 5 && x.getY() > this.getY() + 5;
    } //uses the x and y location declared above methods


}
