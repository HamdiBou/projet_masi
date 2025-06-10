package app.graph.algorithm;

import app.graph.Graph;
import app.graph.Node;
import java.util.List;

/**
 * Strategy interface for shortest path algorithms
 */
public interface ShortestPathStrategy {
    List<Node> findShortestPath(Graph graph, Node source, Node target);
}
