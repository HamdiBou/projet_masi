package app;

import java.util.List;
import javafx.scene.shape.Shape;

public interface DrawingDAO {
    void save(List<Shape> shapes) throws Exception;
    List<Shape> load() throws Exception;
}
