package app.graph;

/**
 * Represents an edge in a graph between two nodes
 */
public class Edge {
    private Node source;
    private Node target;
    private double weight;
    
    public Edge(Node source, Node target, double weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }
    
    public Node getSource() {
        return source;
    }
    
    public Node getTarget() {
        return target;
    }
    
    public double getWeight() {
        return weight;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Edge edge = (Edge) obj;
        return source.equals(edge.source) && target.equals(edge.target);
    }
    
    @Override
    public int hashCode() {
        return 31 * source.hashCode() + target.hashCode();
    }
    
    @Override
    public String toString() {
        return source + " -> " + target + " (" + weight + ")";
    }
}
