import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

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
        triangle.setFill(javafx.scene.paint.Color.LIGHTGOLDENRODYELLOW);
        triangle.setStroke(javafx.scene.paint.Color.ORANGE);
        return triangle;
    }
}
