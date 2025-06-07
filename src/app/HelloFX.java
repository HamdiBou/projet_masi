package app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import creational.factory.ShapeFactory;
import creational.factory.RectangleFactory;
import creational.factory.CircleFactory;
import creational.factory.LineFactory;
import creational.factory.TriangleFactory;
import behavioral.strategy.LoggerStrategy;
import behavioral.strategy.ConsoleLogger;
import behavioral.strategy.FileLogger;
import behavioral.strategy.DatabaseLogger;
import structural.decorator.BorderShapeDecorator;
import structural.decorator.ShadowShapeDecorator;

public class HelloFX extends Application {
    private enum ShapeType { RECTANGLE, CIRCLE, LINE, NODE, EDGE, TRIANGLE }
    private ShapeType selectedShape = ShapeType.RECTANGLE;
    private double startX, startY;
    private Shape previewShape;

    private Map<ShapeType, ShapeFactory> factories = new HashMap<>();
    private LoggerStrategy logger = new ConsoleLogger();
    private Pane drawingPane;
    private DrawingDAO drawingDAO = new FileDrawingDAO();
    private List<Shape> shapes = new ArrayList<>();    // Fields for the UI components
    private ColorPicker fillColorPicker;
    private ColorPicker strokeColorPicker;
    private ComboBox<String> decoratorSelector;
    private Button rectBtn, circBtn, lineBtn, triangleBtn;

    @Override
    public void start(Stage primaryStage) {
        try {
            primaryStage.setTitle("Drawing Studio");

            // Create a root container
            BorderPane root = new BorderPane();
            
            // Create a sidebar for tools
            VBox toolSidebar = new VBox(10);
            toolSidebar.setPadding(new Insets(10));
            toolSidebar.setPrefWidth(200);
            toolSidebar.getStyleClass().add("tool-bar");
            
            // SHAPES SECTION
            Label shapesLabel = new Label("SHAPES");
            shapesLabel.getStyleClass().add("section-title");
            toolSidebar.getChildren().add(shapesLabel);
            
            VBox shapeButtons = new VBox(5);
            shapeButtons.getStyleClass().add("tool-section");
            
            rectBtn = new Button("Rectangle");
            rectBtn.getStyleClass().addAll("tool-button", "selected");
            rectBtn.setMaxWidth(Double.MAX_VALUE);
            rectBtn.setTooltip(new Tooltip("Draw a rectangle (R)"));
            
            circBtn = new Button("Circle");
            circBtn.getStyleClass().add("tool-button");
            circBtn.setMaxWidth(Double.MAX_VALUE);
            circBtn.setTooltip(new Tooltip("Draw a circle (C)"));
            
            lineBtn = new Button("Line");
            lineBtn.getStyleClass().add("tool-button");
            lineBtn.setMaxWidth(Double.MAX_VALUE);
            lineBtn.setTooltip(new Tooltip("Draw a line (L)"));
            
            triangleBtn = new Button("Triangle");
            triangleBtn.getStyleClass().add("tool-button");
            triangleBtn.setMaxWidth(Double.MAX_VALUE);
            triangleBtn.setTooltip(new Tooltip("Draw a triangle (T)"));
            
            shapeButtons.getChildren().addAll(rectBtn, circBtn, lineBtn, triangleBtn);
            toolSidebar.getChildren().add(shapeButtons);
            
            // COLOR SECTION
            toolSidebar.getChildren().add(new Separator());
            Label colorsLabel = new Label("COLORS");
            colorsLabel.getStyleClass().add("section-title");
            toolSidebar.getChildren().add(colorsLabel);
            
            VBox colorSection = new VBox(5);
            colorSection.getStyleClass().add("tool-section");
            
            Label fillLabel = new Label("Fill Color:");
            fillLabel.setTextFill(Color.WHITE);
            fillColorPicker = new ColorPicker(Color.BLACK);
            fillColorPicker.setMaxWidth(Double.MAX_VALUE);
            
            Label strokeLabel = new Label("Border Color:");
            strokeLabel.setTextFill(Color.WHITE);
            strokeColorPicker = new ColorPicker(Color.BLACK);
            strokeColorPicker.setMaxWidth(Double.MAX_VALUE);
            
            colorSection.getChildren().addAll(fillLabel, fillColorPicker, strokeLabel, strokeColorPicker);
            toolSidebar.getChildren().add(colorSection);
            
            // DECORATORS SECTION
            toolSidebar.getChildren().add(new Separator());
            Label decoratorsLabel = new Label("DECORATORS");
            decoratorsLabel.getStyleClass().add("section-title");
            toolSidebar.getChildren().add(decoratorsLabel);
            
            decoratorSelector = new ComboBox<>();
            decoratorSelector.getItems().addAll("None", "Border", "Shadow", "Border + Shadow");
            decoratorSelector.setValue("None");
            decoratorSelector.setMaxWidth(Double.MAX_VALUE);
            toolSidebar.getChildren().add(decoratorSelector);
            
            // LOGGING SECTION
            toolSidebar.getChildren().add(new Separator());
            Label logLabel = new Label("LOGGING");
            logLabel.getStyleClass().add("section-title");
            toolSidebar.getChildren().add(logLabel);
            
            ComboBox<String> logSelector = new ComboBox<>();
            logSelector.getItems().addAll("Console", "File", "Database");
            logSelector.setValue("Console");
            logSelector.setMaxWidth(Double.MAX_VALUE);
            toolSidebar.getChildren().add(logSelector);
            
            // ACTION BUTTONS
            toolSidebar.getChildren().add(new Separator());
            Label actionsLabel = new Label("ACTIONS");
            actionsLabel.getStyleClass().add("section-title");
            toolSidebar.getChildren().add(actionsLabel);
            
            Button saveBtn = new Button("Save Drawing");
            saveBtn.getStyleClass().add("action-button");
            saveBtn.setMaxWidth(Double.MAX_VALUE);
            
            Button clearBtn = new Button("Clear All");
            clearBtn.getStyleClass().addAll("action-button", "clear-button");
            clearBtn.setMaxWidth(Double.MAX_VALUE);
            
            VBox actionButtons = new VBox(5);
            actionButtons.getStyleClass().add("tool-section");
            actionButtons.getChildren().addAll(saveBtn, clearBtn);
            toolSidebar.getChildren().add(actionButtons);
            
            // Create a spacer to push everything up
            VBox spacer = new VBox();
            VBox.setVgrow(spacer, Priority.ALWAYS);
            toolSidebar.getChildren().add(spacer);
            
            // Status bar at the bottom of the sidebar
            Label statusLabel = new Label("Ready");
            statusLabel.setTextFill(Color.WHITE);
            statusLabel.setPadding(new Insets(5));
            HBox statusBar = new HBox(statusLabel);
            statusBar.getStyleClass().add("status-bar");
            statusBar.setAlignment(Pos.CENTER_LEFT);
            toolSidebar.getChildren().add(statusBar);
            
            // Set up button event handlers
            rectBtn.setOnAction(e -> {
                selectedShape = ShapeType.RECTANGLE;
                logger.log("Shape selected: Rectangle");
                updateToolButtons(rectBtn);
                statusLabel.setText("Drawing Rectangle");
            });
            
            circBtn.setOnAction(e -> {
                selectedShape = ShapeType.CIRCLE;
                logger.log("Shape selected: Circle");
                updateToolButtons(circBtn);
                statusLabel.setText("Drawing Circle");
            });
            
            lineBtn.setOnAction(e -> {
                selectedShape = ShapeType.LINE;
                logger.log("Shape selected: Line");
                updateToolButtons(lineBtn);
                statusLabel.setText("Drawing Line");
            });
            
            triangleBtn.setOnAction(e -> {
                selectedShape = ShapeType.TRIANGLE;
                logger.log("Shape selected: Triangle");
                updateToolButtons(triangleBtn);
                statusLabel.setText("Drawing Triangle");
            });
            
            saveBtn.setOnAction(e -> {
                try {
                    drawingDAO.save(shapes);
                    logger.log("Drawing saved.");
                    statusLabel.setText("Drawing saved");
                } catch (Exception ex) {
                    logger.log("Error during save: " + ex.getMessage());
                    statusLabel.setText("Error during save");
                }
            });
            
            clearBtn.setOnAction(e -> {
                drawingPane.getChildren().clear();
                shapes.clear();
                logger.log("Drawing cleared.");
                statusLabel.setText("Drawing cleared");
            });
            
            logSelector.setOnAction(e -> {
                switch (logSelector.getValue()) {
                    case "Console":
                        logger = new ConsoleLogger();
                        break;
                    case "File":
                        logger = new FileLogger();
                        break;
                    case "Database":
                        logger = new DatabaseLogger();
                        break;
                }
                logger.log("Log strategy changed: " + logSelector.getValue());
                statusLabel.setText("Log strategy changed: " + logSelector.getValue());
            });
            
            // Drawing area
            drawingPane = new Pane();
            drawingPane.setPrefSize(800, 600);
            drawingPane.getStyleClass().add("drawing-pane");
            
            StackPane drawingContainer = new StackPane(drawingPane);
            drawingContainer.setPadding(new Insets(20));
            
            drawingPane.setOnMousePressed(e -> onMousePressed(e, drawingPane));
            drawingPane.setOnMouseDragged(e -> onMouseDragged(e, drawingPane));
            drawingPane.setOnMouseReleased(e -> onMouseReleased(e, drawingPane));
            
            // Set up factories
            factories.put(ShapeType.RECTANGLE, new RectangleFactory());
            factories.put(ShapeType.CIRCLE, new CircleFactory());
            factories.put(ShapeType.LINE, new LineFactory());
            factories.put(ShapeType.TRIANGLE, new TriangleFactory());
            
            // Load saved shapes
            try {
                shapes = drawingDAO.load();
                if (shapes != null) {
                    drawingPane.getChildren().addAll(shapes);
                }
            } catch (Exception ex) {
                System.err.println("Error loading shapes: " + ex.getMessage());
                shapes = new ArrayList<>();
            }
            
            root.setLeft(toolSidebar);
            root.setCenter(drawingContainer);
            
            Scene scene = new Scene(root, 1024, 768);
            scene.getStylesheets().add(getClass().getResource("/app/resources/styles.css").toExternalForm());
            
            // Add keyboard shortcuts
            scene.setOnKeyPressed(e -> {
                switch (e.getCode()) {
                    case R:
                        rectBtn.fire();
                        break;
                    case C:
                        circBtn.fire();
                        break;
                    case L:
                        lineBtn.fire();
                        break;
                    case T:
                        triangleBtn.fire();
                        break;
                    case S:
                        if (e.isControlDown()) {
                            saveBtn.fire();
                        }
                        break;
                    default:
                        break;
                }
            });
            
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    private boolean isInDrawingArea(double x, double y) {
        // Adjust the drawing area bounds to match our new canvas size
        return x >= 0 && y >= 0 && x <= drawingPane.getWidth() && y <= drawingPane.getHeight();
    }private void onMousePressed(MouseEvent e, Pane pane) {
        double x = e.getX();
        double y = e.getY();
        if (!isInDrawingArea(x, y)) return;
        startX = x;
        startY = y;
        if (selectedShape == ShapeType.NODE) {
            // TODO: Implement node creation
            return;
        }
        if (selectedShape == ShapeType.EDGE) {
            // TODO: Implement edge creation
            return;
        }
        ShapeFactory factory = factories.get(selectedShape);
        if (factory != null) {
            previewShape = factory.create(startX, startY);
            
            // Apply the selected fill and stroke colors
            previewShape.setFill(fillColorPicker.getValue());
            previewShape.setStroke(strokeColorPicker.getValue());
            
            // Apply decorator based on user selection
            String decoratorChoice = decoratorSelector.getValue();
            if ("Border".equals(decoratorChoice)) {
                BorderShapeDecorator decorator = new BorderShapeDecorator(previewShape, strokeColorPicker.getValue(), 3);
                previewShape = decorator.getDecoratedShape();
            } else if ("Shadow".equals(decoratorChoice)) {
                ShadowShapeDecorator decorator = new ShadowShapeDecorator(previewShape, Color.GRAY, 10);
                previewShape = decorator.getDecoratedShape();
            } else if ("Border + Shadow".equals(decoratorChoice)) {
                BorderShapeDecorator borderDecorator = new BorderShapeDecorator(previewShape, strokeColorPicker.getValue(), 3);
                ShadowShapeDecorator shadowDecorator = new ShadowShapeDecorator(borderDecorator.getDecoratedShape(), Color.GRAY, 10);
                previewShape = shadowDecorator.getDecoratedShape();
            }
            pane.getChildren().add(previewShape);
            shapes.add(previewShape); // Add to the list for DAO
            logger.log("Started drawing shape: " + selectedShape);
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

    /**
     * Updates the visual state of tool buttons to reflect the currently selected tool
     * @param selectedButton The button that was just selected
     */
    private void updateToolButtons(Button selectedButton) {
        rectBtn.getStyleClass().remove("selected");
        circBtn.getStyleClass().remove("selected");
        lineBtn.getStyleClass().remove("selected");
        triangleBtn.getStyleClass().remove("selected");
        selectedButton.getStyleClass().add("selected");
    }

    public static void main(String[] args) {
        launch(args);
    }
}