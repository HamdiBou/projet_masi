package creational.factory;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import structural.decorator.BorderShapeDecorator;

public class LineFactory implements ShapeFactory {
    @Override
    public Shape create(double startX, double startY) {
        Line line = new Line(startX, startY, startX, startY);
        line.setStroke(Color.FORESTGREEN);
        line.setStrokeWidth(3);
        // Decorate with border
        BorderShapeDecorator decorator = new BorderShapeDecorator(line, Color.RED, 3);
        return decorator.getDecoratedShape();
    }
}
