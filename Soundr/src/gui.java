import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;

public class gui extends Application {

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

        @Override
        public void handle(ActionEvent a) {
          Platform.exit();
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

    Slider slider = ThresholdSlider.getThresholdSlider();
    BorderPane sliderPane = new BorderPane();
    sliderPane.setTop(slider);

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

    root.setTop(titlePane);
    root.setBottom(sliderPane);
    this.stage.setScene(scene);
    this.stage.show();
  }

  public void setIconified(boolean bool) {
    this.stage.setIconified(bool);
  }

}
