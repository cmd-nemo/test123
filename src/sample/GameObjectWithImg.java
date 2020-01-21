package sample;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

// Custom class from Game Object that shows an image rather than a shape node
public class GameObjectWithImg extends GameObject {
    public GameObjectWithImg(Image img) {
        super(null);
        view = new ImageView((img));
    }
}