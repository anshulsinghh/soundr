import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;



public class gui extends Application {

  private static final int WINDOW_WIDTH = 1190;
  private static final int WINDOW_HEIGHT = 830;
  private static final int TITLEBAR_HEIGHT = 65;
  
//  private double offsetX = 0;
  //private double offsetY = 0;
  
  class TitleBarButtons extends HBox {
    
    public TitleBarButtons() {
      Button minButton = new Button("-");
      Button maxButton = new Button("F");
      Button closeButton = new Button("X");
      
      this.getChildren().add(minButton);
      this.getChildren().add(maxButton);
      this.getChildren().add(closeButton);
      minButton.setOnAction(new EventHandler<ActionEvent>() {
        
        @Override
        public void handle(ActionEvent a) {
          Platform.exit();
        }
        
      });
      
      maxButton.setOnAction(new EventHandler<ActionEvent>() {
        
        @Override
        public void handle(ActionEvent a) {
          Platform.exit();
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
  
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    Image background = new Image("/image/background.jpg");
    ImageView imView = new ImageView(background);
    
    StackPane root = new StackPane();
    root.getChildren().addAll(imView);
    
    Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    stage.initStyle(StageStyle.TRANSPARENT);
    
    BorderPane pane = new BorderPane();
    
    ToolBar titleBar = new ToolBar();
    
    titleBar.setPrefHeight(TITLEBAR_HEIGHT);
    titleBar.setMinHeight(TITLEBAR_HEIGHT);
    titleBar.setMaxHeight(TITLEBAR_HEIGHT);
    titleBar.setStyle("-fx-background-color: transparent;");
    titleBar.getItems().add(new TitleBarButtons());
    
    pane.setTop(titleBar);
    root.getChildren().add(pane);
    stage.setScene(scene);    
    stage.show();
  }


}
