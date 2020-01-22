package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DroneInterface extends Main {
    private MyCanvas mc;
    private AnimationTimer timer;								// timer used for animation
    private VBox rtPane;										// vertical box for putting info
    private Main arena;

    private void showAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);				// define what box is
        alert.setTitle("About");									// say is About
        alert.setHeaderText(null);
        alert.setContentText("RJM's JavaFX Demonstrator");			// give text
        alert.showAndWait();										// show box and wait for user to close
    }


    void setMouseEvents (Canvas canvas) {
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, 		// for MOUSE PRESSED event
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {

                    }
                });
    }

    MenuBar setMenu() {

        MenuBar menuBar = new MenuBar();						// create main menu

        Menu mFile = new Menu("File");							// add File main menu
        MenuItem mExit = new MenuItem("Exit");					// whose sub menu has Exit
        mExit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {					// action on exit is
                timer.stop();									// stop timer
                System.exit(0);									// exit program
            }
        });
        mFile.getItems().addAll(mExit);							// add exit to File menu

        Menu mHelp = new Menu("Help");							// create Help menu
        MenuItem mAbout = new MenuItem("About");				// add About sub men item
        mAbout.setOnAction(actionEvent -> {
            showAbout();									// and its action to print about
        });
        mHelp.getItems().addAll(mAbout);						// add About to Help main item

        menuBar.getMenus().addAll(mFile, mHelp);				// set main menu with File, Help
        return menuBar;											// return the menu
    }

    /**
     * set up the horizontal box for the bottom with relevant buttons
     * @return
     */
    private HBox setButtons() {
        Button btnStart = new Button("Start");                    // create button for starting
        // now define event when it is pressed
        btnStart.setOnAction(event -> {
            timer.start();                                    // its action is to start the timer
        });

        Button btnStop = new Button("Pause");                    // now button for stop
        btnStop.setOnAction(event -> {
            timer.stop();                                    // and its action to stop the timer
        });
        Button btnAdd = new Button("Load");
        btnAdd.setOnAction(event -> {

        });
        // now add these buttons + labels to a HBox
        return new HBox(new Label("Run: "), btnStart, btnStop, new Label("Add: "), btnAdd);
        }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Stage primaryStage = new Stage();
        primaryStage.setTitle("hb000671_Drone_Simulation");
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(15, 20, 15, 25));

        bp.setTop(setMenu());											// put menu at the top
        arena = new Main();
        timer = new AnimationTimer(){
            public void handle(long now){
                arena.onUpdate();
            }
        };
        Group root = new Group();										// create group with canvas
        Canvas canvas = new Canvas( 400, 500 );
        root.getChildren().add( canvas );
        bp.setLeft(root);												// load canvas to left area

        mc = new MyCanvas(canvas.getGraphicsContext2D(), 400, 500);

        setMouseEvents(canvas);											// set up mouse events


        rtPane = new VBox();											// set vBox on right to list items
        rtPane.setAlignment(Pos.TOP_LEFT);								// set alignment
        rtPane.setPadding(new Insets(15, 80, 80, 15));					// padding
        bp.setRight(rtPane);											// add rtPane to borderpane right

        bp.setBottom(setButtons());										// set bottom pane with buttons

        Scene scene = new Scene(bp, 900, 900);							// set overall scene
        bp.prefHeightProperty().bind(scene.heightProperty());
        bp.prefWidthProperty().bind(scene.widthProperty());

        primaryStage.setScene(scene);
        primaryStage.show();


        //return null;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Application.launch(args);			// launch the GUI

    }

}