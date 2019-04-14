//Import all the necessary files for the GUI
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.event.EventHandler; 
import javafx.geometry.Insets;
import javafx.event.ActionEvent;

/**
 * The main class for Soundr. It contains the main method where the project is entered, and controls
 *   the entire app in general.
 */
public class GUI extends Application {

  public Recorder r; //Stores the recorder, which collects sound volumes readings

  //A private inner class which contains all the title bar buttons for the GUI
  class TitleBarButtons extends HBox {

    public TitleBarButtons() {

      Button minButton = new Button("\uE949"); //Creates a minimizing button for the window
      Button closeButton = new Button("\uE106"); //Creates a closing button for the window

      minButton.setId("min"); //Set the id of the minimizing button to min
      closeButton.setId("close"); //Set the id of the closing button to close

      //Configure the buttons
      this.getChildren().add(minButton);
      this.getChildren().add(closeButton);
      this.setPadding(new Insets(0, 0, 13, 0));

      //This assigns the minimizing button to minimizing the window
      minButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent a) {
          setIconified(true);
        }
      });

      //This makes the closing button close the entire program
      closeButton.setOnAction(new EventHandler<ActionEvent>() {
        @SuppressWarnings("deprecation")
        @Override
        public void handle(ActionEvent a) {
          Platform.exit(); //Exits the platform
          r.stop(); //Stops the recorder from continuing
          System.exit(1); //Exit the program
        }
      });

    }
  }

  double offsetX = 0; //The x offset for the borderless window
  double offsetY = 0; //The y offset for the borderless window
  private Stage stage; //The stage that the window will use

  /**
   * The main method for the entire Soundr project. This is the entry point when running the
   *   entire project.
   * 
   * @param args the arguments for the Soundr project
   */
  public static void main(String[] args) {
    launch(args); //Launches the program window and starts recording
  }

  /**
   * This method initializes the GUI window and puts the proper sliders and descriptions on the
   *   screen.
   * 
   * @param stage the stage for the GUI
   * @throws Exception the start method has a chance of throwing an exception
   */
  @Override
  public void start(Stage stage) throws Exception {

    this.stage = stage; //Stores the stage in the instance variable
    BorderPane root = new BorderPane(); //Creates the root border pane
    Scene scene = new Scene(root); //Create the scene using the root border pane
    scene.getStylesheets().add("/css/style.css"); //Retrieves the style sheets
    this.stage.initStyle(StageStyle.TRANSPARENT); //Initializes the stage to be transparent

    this.r = new Recorder(); //Creates the recorder

    //Creates the slider and configures it visually
    Slider slider = new Slider(0, 1, 0.5);
    slider.setBlockIncrement(0.05);
    slider.setShowTickLabels(true);
    slider.setMajorTickUnit(.1);
    ProgressBar pb = new ProgressBar(0.5);
    pb.setId("slider-bar");
    pb.getTransforms().add(new Translate(0, -25));

    //Adds a change listener to the slider, so that changes to the threshold are monitored
    slider.valueProperty().addListener(new ChangeListener<Number>() {

      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue,
          Number newValue) {

        //Set the threshold in the recorder, since the user modified the threshold value
        r.setThreshold((float) slider.getValue());
        pb.setProgress((double) newValue);
      }
    });

    //Creates and arranges the various panes that are involved in the GUI
    StackPane sliderPane = new StackPane();
    sliderPane.getChildren().addAll(pb, slider);
    BorderPane titlePane = new BorderPane();
    ToolBar titleBar = new ToolBar();
    titleBar.getItems().add(new TitleBarButtons());
    titlePane.setTop(titleBar);

    //Sets the titlebar so that the mouse can move the window with the titlebar
    titleBar.setOnMousePressed(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        offsetX = stage.getX() - e.getScreenX();
        offsetY = stage.getY() - e.getScreenY();
      }
    });

    titleBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        stage.setX(e.getScreenX() + offsetX);
        stage.setY(e.getScreenY() + offsetY);
      }
    });

    //Adds the various panels to the root window
    root.setLeft(getText());
    root.setTop(titlePane); 
    root.setBottom(sliderPane);

    //Display the window
    this.stage.setScene(scene);
    this.stage.show();

    r.start(); //Start the recorder, since it is in its own thread
  }

  /**
   * This method returns the text that will be displayed in the window.
   * 
   * @return the borderpane contianing the text
   */
  public BorderPane getText() {
    Text soundr = new Text("  Soundr"); //The title text
    soundr.setId("title-text");

    //Description text about soundr
    Text about = new Text("Soundr is a utility that sends notifications when sounds reach \n"
        + "a certain threshold of loudness. Soundr actively monitors the user's \n"
        + "environment and will send alerts whenever the ambiant sound reaches a \n"
        + "certain threshold. The user can also adjust the level of volume that \n"
        + "noises are measured, so that the app can be customized.");
    about.setId("about");
    about.getTransforms().addAll(new Translate(14, -90));
    BorderPane textPane = new BorderPane();

    //Adds the text and description to the textPane and returns the pane
    textPane.setTop(soundr);
    textPane.setBottom(about);
    return textPane;
  }

  //Sets the stage's iconified boolean
  public void setIconified(boolean bool) {
    this.stage.setIconified(bool);
  }
}