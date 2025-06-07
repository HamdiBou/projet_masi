package creational.factory;

import structural.decorator.BorderShapeDecorator;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class CircleFactory implements ShapeFactory {
    @Override
    public Shape create(double startX, double startY) {
        Circle circle = new Circle(startX, startY, 0);
        circle.setFill(Color.PINK);
        circle.setStroke(Color.DARKRED);
        // Decorate with border
        BorderShapeDecorator decorator = new BorderShapeDecorator(circle, Color.RED, 3);
        return decorator.getDecoratedShape();
    }
}
