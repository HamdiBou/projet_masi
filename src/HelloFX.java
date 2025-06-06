import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class HelloFX extends Application {
    private enum ShapeType { RECTANGLE, CIRCLE, LINE, NODE, EDGE, TRIANGLE }
    private ShapeType selectedShape = ShapeType.RECTANGLE;
    private double startX, startY;
    private Shape previewShape;

    private Map<ShapeType, ShapeFactory> factories = new HashMap<>();
    private LoggerStrategy logger = new ConsoleLogger();
    private Pane drawingPane;
    private DrawingDAO drawingDAO = new FileDrawingDAO();
    private List<Shape> shapes = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setTitle("Dessinateur de Formes");

            // Palette de sélection
            Button rectBtn = new Button("Rectangle");
            Button circBtn = new Button("Cercle");
            Button lineBtn = new Button("Ligne");
            Button triangleBtn = new Button("Triangle");
            Button saveBtn = new Button("Save");
            Button clearBtn = new Button("Clear");
            HBox palette = new HBox(10, rectBtn, circBtn, lineBtn, triangleBtn, saveBtn, clearBtn);

            // Sélecteur de stratégie de log
            ComboBox<String> logSelector = new ComboBox<>();
            logSelector.getItems().addAll("Console", "Fichier", "Base de données");
            logSelector.setValue("Console");
            logSelector.setOnAction(e -> {
                switch (logSelector.getValue()) {
                    case "Console":
                        logger = new ConsoleLogger();
                        break;
                    case "Fichier":
                        logger = new FileLogger();
                        break;
                    case "Base de données":
                        logger = new DatabaseLogger();
                        break;
                }
                logger.log("Stratégie de log changée : " + logSelector.getValue());
            });
            palette.getChildren().add(logSelector);

            rectBtn.setOnAction(e -> {
                selectedShape = ShapeType.RECTANGLE;
                logger.log("Forme sélectionnée : Rectangle");
            });
            circBtn.setOnAction(e -> {
                selectedShape = ShapeType.CIRCLE;
                logger.log("Forme sélectionnée : Cercle");
            });
            lineBtn.setOnAction(e -> {
                selectedShape = ShapeType.LINE;
                logger.log("Forme sélectionnée : Ligne");
            });
            triangleBtn.setOnAction(e -> {
                selectedShape = ShapeType.TRIANGLE;
                logger.log("Forme sélectionnée : Triangle");
            });

            saveBtn.setOnAction(e -> {
                try {
                    drawingDAO.save(shapes);
                    logger.log("Dessin sauvegardé.");
                } catch (Exception ex) {
                    logger.log("Erreur lors de la sauvegarde: " + ex.getMessage());
                }
            });

            clearBtn.setOnAction(e -> {
                drawingPane.getChildren().clear();
                shapes.clear();
                logger.log("Dessin effacé.");
            });

            // Zone de dessin
            drawingPane = new Pane();
            drawingPane.setPrefSize(600, 400);
            drawingPane.setMinSize(600, 400);
            drawingPane.setMaxSize(600, 400);
            drawingPane.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2;");

            StackPane drawingContainer = new StackPane(drawingPane);
            drawingContainer.setPrefSize(600, 400);
            drawingContainer.setMinSize(600, 400);
            drawingContainer.setMaxSize(600, 400);

            drawingPane.setOnMousePressed(e -> onMousePressed(e, drawingPane));
            drawingPane.setOnMouseDragged(e -> onMouseDragged(e, drawingPane));
            drawingPane.setOnMouseReleased(e -> onMouseReleased(e, drawingPane));

            factories.put(ShapeType.RECTANGLE, new RectangleFactory());
            factories.put(ShapeType.CIRCLE, new CircleFactory());
            factories.put(ShapeType.LINE, new LineFactory());
            factories.put(ShapeType.TRIANGLE, new TriangleFactory());

            // Charger les formes sauvegardées au démarrage
            try {
                shapes = drawingDAO.load();
                if (shapes != null) {
                    drawingPane.getChildren().addAll(shapes);
                }
            } catch (Exception ex) {
                System.err.println("Erreur lors du chargement des formes: " + ex.getMessage());
                shapes = new ArrayList<>();
            }

            BorderPane root = new BorderPane();
            root.setTop(palette);
            root.setCenter(drawingContainer);

            primaryStage.setScene(new Scene(root, 700, 500));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isInDrawingArea(double x, double y) {
        return x >= 0 && y >= 0 && x <= 600 && y <= 400;
    }

    private void onMousePressed(MouseEvent e, Pane pane) {
        double x = e.getX();
        double y = e.getY();
        if (!isInDrawingArea(x, y)) return;
        startX = x;
        startY = y;
        if (selectedShape == ShapeType.NODE) {
            // TODO: Implémenter la création de nœuds
            return;
        }
        if (selectedShape == ShapeType.EDGE) {
            // TODO: Implémenter la création d'arêtes
            return;
        }
        ShapeFactory factory = factories.get(selectedShape);
        if (factory != null) {
            previewShape = factory.create(startX, startY);
            pane.getChildren().add(previewShape);
            shapes.add(previewShape); // Ajout à la liste pour DAO
            logger.log("Début du dessin d'une forme : " + selectedShape);
        }
    }

    private void onMouseDragged(MouseEvent e, Pane pane) {
        if (previewShape == null) return;
        double endX = e.getX();
        double endY = e.getY();
        switch (selectedShape) {
            case RECTANGLE:
                Rectangle rect = (Rectangle) previewShape;
                rect.setWidth(Math.abs(endX - startX));
                rect.setHeight(Math.abs(endY - startY));
                rect.setX(Math.min(startX, endX));
                rect.setY(Math.min(startY, endY));
                break;
            case CIRCLE:
                Circle circ = (Circle) previewShape;
                double radius = Math.hypot(endX - startX, endY - startY);
                circ.setRadius(radius);
                break;
            case LINE:
                Line line = (Line) previewShape;
                line.setEndX(endX);
                line.setEndY(endY);
                break;
            case TRIANGLE:
                Polygon triangle = (Polygon) previewShape;
                // Redimensionnement interactif du triangle
                double size = Math.max(Math.abs(endX - startX), Math.abs(endY - startY));
                double h = Math.sqrt(3) / 2 * size;
                triangle.getPoints().setAll(
                    startX, startY - 2 * h / 3,
                    startX - size / 2, startY + h / 3,
                    startX + size / 2, startY + h / 3
                );
                break;            case NODE:
            case EDGE:
                break;
        }
    }

    private void onMouseReleased(MouseEvent e, Pane pane) {
        previewShape = null;
    }

    @Override
    public void stop() {
        try {
            drawingDAO.save(shapes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}