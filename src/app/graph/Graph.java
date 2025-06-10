package app.graph;

import java.util.*;

public class Graph {
    private List<Node> nodes = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void addEdge(Node source, Node target, double weight) {
        Edge edge = new Edge(source, target, weight);
        edges.add(edge);
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public List<Edge> getEdgesFrom(Node node) {
        List<Edge> out = new ArrayList<>();
        for (Edge e : edges) {
            if (e.getSource().equals(node)) {
                out.add(e);
            }
        }
        return out;
    }
}
