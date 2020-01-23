package uk.reading.hb000671.gui;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/**
 * @author hb000671
 *  Class to handle a canvas, used by different GUIs
 */
public class MyCanvas {
    int xCanvasSize = 1200;				// constants for the size of the canvas.
    int yCanvasSize = 1200;
    GraphicsContext gc;


    /**
     * constructor sets up relevant Graphics context and size of canvas
     * @param xcs
     * @param ycs
     */
    public MyCanvas(GraphicsContext g, int xcs, int ycs) {
        gc = g;
        xCanvasSize = xcs;
        yCanvasSize = ycs;
    }

    /***
     * declares new object type O
     * @param o
     */
    public MyCanvas(Object o) {
    }

    /***
     * method showText shows the text in the menubar
     * @param x
     * @param y
     * @param s
     */

    public void showText (double x, double y, String s) {
        gc.setTextAlign(TextAlignment.CENTER);							// set horizontal alignment
        gc.setTextBaseline(VPos.CENTER);								// vertical alignment
        gc.setFill(Color.BLACK);										// colour in black
        gc.fillText(s, x, y);						// print sposition in text
    }

    /**
     * Show text of positions of drones added
     * @param x
     * @param y
     * @param i
     */
    public void showInt (double x, double y, int i) {
        showText (x, y, Integer.toString(i));
    }
}

