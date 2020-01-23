package uk.reading.hb000671.gui;
//required imports for this class
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
/**
 * @author hb000671
 *  Drone interface class extends from the SimulationScene.
 */


public class DroneInterface extends SimulationScene {
    private MyCanvas mc;
    private AnimationTimer timer;								// timer used for starting the simulation
    private VBox rtPane;										// vertical box for collating the required information
    private SimulationScene arena; //calls the class simulation scene

    private void showAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);				// defines what type the box is
        alert.setTitle("About");									//displays the word bout in the border
        alert.setHeaderText(null);
        alert.setContentText("Neyma's Drone Gui");			// displays the text
        alert.showAndWait();										//shows the box on exit
    }


    void setMouseEvents (Canvas canvas) {
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, 		// for MOUSE PRESSED events in the border
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {

                    }
                });
    }

    MenuBar setMenu() {

        MenuBar menuBar = new MenuBar();						// creates the main menu

        Menu mFile = new Menu("File");							// adds a File to the main menu
        MenuItem mExit = new MenuItem("Exit");					// Additionally  has Exit
        mExit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {					// action on exit event is
                timer.stop();									// stops the timer
                System.exit(0);									// exits the program
            }
        });
        mFile.getItems().addAll(mExit);							// adds the exit to File menu

        Menu mHelp = new Menu("Help");							// creates the Help menu
        MenuItem mAbout = new MenuItem("About");				// adds the About to the menu
        mAbout.setOnAction(actionEvent -> {
            showAbout();									// prints about
        });
        mHelp.getItems().addAll(mAbout);						// add About to Help main item

        menuBar.getMenus().addAll(mFile, mHelp);				// set main menu with the File, Help
        return menuBar;											// returns back to to the main menu
    }

    /**
     * sets up the horizontal box for the bottom with relevant buttons
     * @return menuBar
     */
    private HBox setButtons() {
        Button btnStart = new Button("Start");                    // create button for starting
        // now define event when it is pressed
        btnStart.setOnAction(event -> {
            System.out.println("Test"); //test to see if button worked, print statement Test

            timer.start();// its action is to start the timer
            System.out.println("Test"); //test to see if the button worked , print statement Test


        });

        Button btnStop = new Button("Pause");                    //button for pausing the program
        btnStop.setOnAction(event -> {
            timer.stop();                                    //stops the timer
        });
        Button btnAdd = new Button("Load"); //button for loading the file
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

        bp.setTop(setMenu());											// places the menu at the top

        arena = new SimulationScene();
        timer = new AnimationTimer(){
            public void handle(long now){
                arena.onUpdate();
            }
        };

        Group root = new Group();										// creates group with canvas
        Canvas canvas = new Canvas( 1280, 720 );

        setMouseEvents(canvas);											// set up mouse events
        rtPane = new VBox();											// set vBox on right to list items
        rtPane.setAlignment(Pos.TOP_LEFT);								// set alignment
        rtPane.setPadding(new Insets(15, 80, 80, 15));					// padding
        bp.setRight(rtPane);											// add rtPane to borderpane right

        bp.setBottom(setButtons());										// set bottom pane with buttons

        Scene scene = new Scene(bp, 1280, 720);							// set overall scene
        bp.prefHeightProperty().bind(scene.heightProperty());
        bp.prefWidthProperty().bind(scene.widthProperty());

        primaryStage.setScene(scene);
        primaryStage.show();



    }


}