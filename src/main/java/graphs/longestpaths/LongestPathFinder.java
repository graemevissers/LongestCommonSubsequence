package graphs.longestpaths;

import graphs.BaseEdge;
import graphs.Edge;
import graphs.Graph;

/**
 * Interface for finding a shortest path between two vertices of a graph.
 * This LongestPathFinder Interface was originally written by the CSE 373 staff at the University of Washington
 * and later modified by myself, Graeme Vissers
 *
 * @param <G> The graph type. Must be a subtype of {@link Graph}.
 * @param <V> The vertex type.
 * @param <E> The edge type. Must be a subtype of {@link Edge}.
 */
public interface LongestPathFinder<G extends Graph<V, E>, V, E extends BaseEdge<V, E>> {
    /**
     * Computes a shortest path from start to end in given graph, and returns a ShortestPath object
     * that can be queried for the list of edges or vertices along the path, or its total weight
     */
    LongestPath<V, E> findLongestPath(G graph, V start, V end);
}
