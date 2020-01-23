package uk.reading.hb000671.gui;
import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

/**
 * @author shsmchlr
 *  Class to handle a canvas, used by different GUIs
 */
public class MyCanvas {
    int xCanvasSize = 1200;				// constants for relevant sizes
    int yCanvasSize = 1200;
    GraphicsContext gc;


    /**
     * onstructor sets up relevant Graphics context and size of canvas
     * @param g
     * @param cs
     */
    public MyCanvas(GraphicsContext g, int xcs, int ycs) {
        gc = g;
        xCanvasSize = xcs;
        yCanvasSize = ycs;
    }

    public MyCanvas(Object o) {
    }


    public void showText (double x, double y, String s) {
        gc.setTextAlign(TextAlignment.CENTER);							// set horizontal alignment
        gc.setTextBaseline(VPos.CENTER);								// vertical
        gc.setFill(Color.BLACK);										// colour in white
        gc.fillText(s, x, y);						// print score as text
    }

    /**
     * Show Int .. by writing int i at position x,y
     * @param x
     * @param y
     * @param i
     */
    public void showInt (double x, double y, int i) {
        showText (x, y, Integer.toString(i));
    }
}

