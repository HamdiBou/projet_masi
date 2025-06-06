import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class RectangleFactory implements ShapeFactory {
    @Override
    public Shape create(double startX, double startY) {
        Rectangle rect = new Rectangle(startX, startY, 0, 0);
        rect.setFill(javafx.scene.paint.Color.LIGHTBLUE);
        rect.setStroke(javafx.scene.paint.Color.DARKBLUE);
        return rect;
    }
}
