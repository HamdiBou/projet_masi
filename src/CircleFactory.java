import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class CircleFactory implements ShapeFactory {
    @Override
    public Shape create(double startX, double startY) {
        return new Circle(startX, startY, 0);
    }
}
