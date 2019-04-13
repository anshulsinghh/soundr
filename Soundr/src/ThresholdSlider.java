import javafx.scene.control.Slider;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;

public class ThresholdSlider {
  public static Slider getThresholdSlider() {

    Slider slider = new Slider(0, 1, 0.5);
    slider.setBlockIncrement(0.05);
  //  slider.setShowTickMarks(true);
    slider.setShowTickLabels(true);
    slider.setMajorTickUnit(.1);
    slider.valueProperty().addListener(new ChangeListener<Number>() {

      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue,
          Number newValue) {
        System.out.println(slider.getValue());
      }
    }); 

    return slider;
  }
}
