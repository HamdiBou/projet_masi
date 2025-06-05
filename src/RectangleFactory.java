import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class RectangleFactory implements ShapeFactory {
    @Override
    public Shape create(double startX, double startY) {
        return new Rectangle(startX, startY, 0, 0);
    }
}
