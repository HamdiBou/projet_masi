package structural.decorator;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class BorderShapeDecorator extends ShapeDecorator {
    private Color borderColor;
    private double borderWidth;

    public BorderShapeDecorator(Shape decoratedShape, Color borderColor, double borderWidth) {
        super(decoratedShape);
        this.borderColor = borderColor;
        this.borderWidth = borderWidth;
        applyBorder();
    }

    private void applyBorder() {
        decoratedShape.setStroke(borderColor);
        decoratedShape.setStrokeWidth(borderWidth);
    }

    public Shape getDecoratedShape() {
        return decoratedShape;
    }

    // ...delegate other methods as needed...
}
