package structural.decorator;

import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class ShadowShapeDecorator extends ShapeDecorator {
    private Color shadowColor;
    private double radius;

    public ShadowShapeDecorator(Shape decoratedShape, Color shadowColor, double radius) {
        super(decoratedShape);
        this.shadowColor = shadowColor;
        this.radius = radius;
        applyShadow();
    }

    private void applyShadow() {
        DropShadow shadow = new DropShadow();
        shadow.setColor(shadowColor);
        shadow.setRadius(radius);
        decoratedShape.setEffect(shadow);
    }

    public Shape getDecoratedShape() {
        return decoratedShape;
    }
}
