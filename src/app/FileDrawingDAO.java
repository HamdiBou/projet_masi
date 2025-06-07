package app;

import javafx.scene.shape.Shape;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import java.io.*;
import java.util.*;
import creational.singleton.AppConfig;
import structural.decorator.BorderShapeDecorator;
import javafx.scene.paint.Color;

public class FileDrawingDAO implements DrawingDAO {
    private String filename = AppConfig.getInstance().getDefaultDrawingFile();

    @Override
    public void save(List<Shape> shapes) throws Exception {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Shape s : shapes) {
                // Decorate all shapes before saving (for demonstration, add a blue border)
                BorderShapeDecorator decorator = new BorderShapeDecorator(s, Color.BLUE, 2);
                Shape decorated = decorator.getDecoratedShape();
                if (decorated instanceof Rectangle) {
                    Rectangle r = (Rectangle) decorated;
                    pw.printf("RECT %.2f %.2f %.2f %.2f\n", r.getX(), r.getY(), r.getWidth(), r.getHeight());
                } else if (decorated instanceof Circle) {
                    Circle c = (Circle) decorated;
                    pw.printf("CIRC %.2f %.2f %.2f\n", c.getCenterX(), c.getCenterY(), c.getRadius());
                } else if (decorated instanceof Line) {
                    Line l = (Line) decorated;
                    pw.printf("LINE %.2f %.2f %.2f %.2f\n", l.getStartX(), l.getStartY(), l.getEndX(), l.getEndY());
                }
            }
        }
    }

    @Override
    public List<Shape> load() throws Exception {
        List<Shape> shapes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                switch (parts[0]) {
                    case "RECT":
                        shapes.add(new Rectangle(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]), Double.parseDouble(parts[4])));
                        break;
                    case "CIRC":
                        shapes.add(new Circle(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3])));
                        break;
                    case "LINE":
                        shapes.add(new Line(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]), Double.parseDouble(parts[3]), Double.parseDouble(parts[4])));
                        break;
                }
            }
        }
        return shapes;
    }
}
