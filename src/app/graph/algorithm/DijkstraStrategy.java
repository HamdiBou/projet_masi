package app.graph.algorithm;

import app.graph.Graph;
import app.graph.Node;
import app.graph.Edge;
import java.util.*;

/**
 * Dijkstra's algorithm implementation for shortest path
 */
public class DijkstraStrategy implements ShortestPathStrategy {
    @Override
    public List<Node> findShortestPath(Graph graph, Node source, Node target) {
        Map<Node, Double> dist = new HashMap<>();
        Map<Node, Node> prev = new HashMap<>();
        Set<Node> visited = new HashSet<>();
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingDouble(dist::get));

        for (Node node : graph.getNodes()) {
            dist.put(node, Double.POSITIVE_INFINITY);
            prev.put(node, null);
        }
        dist.put(source, 0.0);
        queue.add(source);

        while (!queue.isEmpty()) {
            Node u = queue.poll();
            if (u.equals(target)) break;
            if (!visited.add(u)) continue;
            for (Edge edge : graph.getEdgesFrom(u)) {
                Node v = edge.getTarget();
                double alt = dist.get(u) + edge.getWeight();
                if (alt < dist.get(v)) {
                    dist.put(v, alt);
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
