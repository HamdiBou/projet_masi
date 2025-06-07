package structural.decorator;

import javafx.scene.shape.Shape;

public abstract class ShapeDecorator {
    protected Shape decoratedShape;

    public ShapeDecorator(Shape decoratedShape) {
        this.decoratedShape = decoratedShape;
    }

    public Shape getDecoratedShape() {
        return decoratedShape;
    }
}
