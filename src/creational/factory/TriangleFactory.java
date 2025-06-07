package creational.factory;

import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import structural.decorator.BorderShapeDecorator;
import javafx.scene.paint.Color;

public class TriangleFactory implements ShapeFactory {
    @Override
    public Shape create(double x, double y) {
        // Triangle équilatéral de base, centré sur (x, y), taille 60
        double size = 60;
        double h = Math.sqrt(3) / 2 * size;
        Polygon triangle = new Polygon(
            x, y - 2 * h / 3, // top
            x - size / 2, y + h / 3, // bottom left
            x + size / 2, y + h / 3  // bottom right
        );
        triangle.setFill(Color.LIGHTGOLDENRODYELLOW);
        triangle.setStroke(Color.ORANGE);
        // Decorate with border
        BorderShapeDecorator decorator = new BorderShapeDecorator(triangle, Color.RED, 3);
        return decorator.getDecoratedShape();
    }
}
