package creational.factory;

import structural.decorator.BorderShapeDecorator;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class RectangleFactory implements ShapeFactory {
    @Override
    public Shape create(double startX, double startY) {
        Rectangle rect = new Rectangle(startX, startY, 0, 0);
        rect.setFill(Color.LIGHTBLUE);
        rect.setStroke(Color.DARKBLUE);
        // Decorate with border
        BorderShapeDecorator decorator = new BorderShapeDecorator(rect, Color.RED, 3);
        return decorator.getDecoratedShape();
    }
}
