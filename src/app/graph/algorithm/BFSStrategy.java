package app.graph.algorithm;

import app.graph.Graph;
import app.graph.Node;
import app.graph.Edge;
import java.util.*;

/**
 * Breadth-First Search (BFS) for unweighted shortest path
 */
public class BFSStrategy implements ShortestPathStrategy {
    @Override
    public List<Node> findShortestPath(Graph graph, Node source, Node target) {
        Map<Node, Node> prev = new HashMap<>();
        Queue<Node> queue = new LinkedList<>();
        Set<Node> visited = new HashSet<>();
        queue.add(source);
        visited.add(source);
        while (!queue.isEmpty()) {
            Node u = queue.poll();
            if (u.equals(target)) break;
            for (Edge edge : graph.getEdgesFrom(u)) {
                Node v = edge.getTarget();
                if (!visited.contains(v)) {
                    visited.add(v);
                    prev.put(v, u);
                    queue.add(v);
                }
            }
        }
        // Reconstruct path
        List<Node> path = new ArrayList<>();
        for (Node at = target; at != null; at = prev.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        if (path.size() == 1 && !path.get(0).equals(source)) return Collections.emptyList();
        return path;
    }
}
