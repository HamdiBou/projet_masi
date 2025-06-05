import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class LineFactory implements ShapeFactory {
    @Override
    public Shape create(double startX, double startY) {
        return new Line(startX, startY, startX, startY);
    }
}
