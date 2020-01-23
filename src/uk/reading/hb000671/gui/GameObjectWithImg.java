package uk.reading.hb000671.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * @author hb000671
 * // Custom class from Game Object that shows an image rather than a shape node.
 */

public class GameObjectWithImg extends GameObject {
    public GameObjectWithImg(Image img) { //extends from abstract class GameObject
        super(null); //allows for loading images rather than shapes
        view = new ImageView((img));
    }
}