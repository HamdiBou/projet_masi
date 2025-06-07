package creational.factory;

import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;

public class LineFactory implements ShapeFactory {
    @Override
    public Shape create(double startX, double startY) {
        Line line = new Line(startX, startY, startX, startY);
        line.setStroke(javafx.scene.paint.Color.FORESTGREEN);
        line.setStrokeWidth(3);
        return line;
    }
}
