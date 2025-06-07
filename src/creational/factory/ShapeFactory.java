package creational.factory;

import javafx.scene.shape.Shape;

public interface ShapeFactory {
    Shape create(double startX, double startY);
}
