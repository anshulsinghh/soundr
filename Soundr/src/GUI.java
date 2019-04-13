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

public class GUI extends Application {

  public Recorder r;

  class TitleBarButtons extends HBox {

    public TitleBarButtons() {

      Button minButton = new Button("\uE949");
      Button closeButton = new Button("\uE106");

      minButton.setId("min");
      closeButton.setId("close");

      this.getChildren().add(minButton);
      this.getChildren().add(closeButton);
      this.setPadding(new Insets(0, 0, 13, 0));
      minButton.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent a) {
          setIconified(true);
        }

      });

      closeButton.setOnAction(new EventHandler<ActionEvent>() {

        @SuppressWarnings("deprecation")
        @Override
        public void handle(ActionEvent a) {
          Platform.exit();
          r.stop();
          System.exit(1);
        }

      });

    }
  }

  double offsetX = 0;
  double offsetY = 0;

  private Stage stage;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {

    this.stage = stage;
    // StackPane root = new StackPane();
    BorderPane root = new BorderPane();
    Scene scene = new Scene(root);
    scene.getStylesheets().add("/css/style.css");
    this.stage.initStyle(StageStyle.TRANSPARENT);

    this.r = new Recorder();

    Slider slider = new Slider(0, 1, 0.5);
    slider.setBlockIncrement(0.05);
    slider.setShowTickLabels(true);
    slider.setMajorTickUnit(.1);
    ProgressBar pb = new ProgressBar(0.5);
    pb.getTransforms().add(new Translate(0,-25));
    slider.valueProperty().addListener(new ChangeListener<Number>() {

      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue,
          Number newValue) {

        r.setThreshold((float) slider.getValue());
        pb.setProgress((double)newValue);
      }

    });

    StackPane sliderPane = new StackPane();
    sliderPane.getChildren().addAll(pb, slider);
  /* BorderPane sliderPane = new BorderPane();
    sliderPane.setTop(slider); */

    BorderPane titlePane = new BorderPane();
    ToolBar titleBar = new ToolBar();
    titleBar.getItems().add(new TitleBarButtons());
    titlePane.setTop(titleBar);
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

    root.setLeft(getText());
    root.setTop(titlePane);
    root.setBottom(sliderPane);
    this.stage.setScene(scene);
    this.stage.show();
    this.r.start();
  }

  public BorderPane getText() {
    Text soundr = new Text("  Soundr");
    soundr.setId("title-text");
    BorderPane textPane = new BorderPane();
    textPane.setTop(soundr);
    return textPane;
  }
  
  public void setIconified(boolean bool) {
    this.stage.setIconified(bool);
  }

}
