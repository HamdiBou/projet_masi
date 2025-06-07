package app;

import javafx.scene.shape.Shape;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import java.io.*;
import java.util.*;
import creational.singleton.AppConfig;

public class FileDrawingDAO implements DrawingDAO {
    private String filename = AppConfig.getInstance().getDefaultDrawingFile();

    @Override
    public void save(List<Shape> shapes) throws Exception {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Shape s : shapes) {
                if (s instanceof Rectangle) {
                    Rectangle r = (Rectangle) s;
                    pw.printf("RECT %.2f %.2f %.2f %.2f\n", r.getX(), r.getY(), r.getWidth(), r.getHeight());
                } else if (s instanceof Circle) {
                    Circle c = (Circle) s;
                    pw.printf("CIRC %.2f %.2f %.2f\n", c.getCenterX(), c.getCenterY(), c.getRadius());
                } else if (s instanceof Line) {
                    Line l = (Line) s;
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
