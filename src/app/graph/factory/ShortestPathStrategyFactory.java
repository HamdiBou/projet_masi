package app.graph.factory;

import app.graph.algorithm.ShortestPathStrategy;
import app.graph.algorithm.DijkstraStrategy;
import app.graph.algorithm.BFSStrategy;

public class ShortestPathStrategyFactory {
    public enum AlgorithmType { DIJKSTRA, BFS }
    public static ShortestPathStrategy getStrategy(AlgorithmType type) {
        switch (type) {
            case DIJKSTRA:
                return new DijkstraStrategy();
            case BFS:
                return new BFSStrategy();
            default:
                throw new IllegalArgumentException("Unknown algorithm type");
        }
    }
}
