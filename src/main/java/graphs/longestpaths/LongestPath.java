package graphs.longestpaths;

import graphs.BaseEdge;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Interface for the LongestPath object, which is returned by the DijkstraLongestPathFinder.
 * This interface was originally written by the CSE 373 staff and slightly modified by Graeme Vissers
 *
 * @param <V> The vertex type
 * @param <E> The edge type
 */
public interface LongestPath<V, E extends BaseEdge<V, E>> {
    /** Returns true iff a path was found between the given start and end vertices. */
    boolean exists();

    /**
     * Returns the list of edges in the shortest path (in order from start to end).
     * @throws UnsupportedOperationException if no path exists from start to end
     */
    List<E> edges();

    /**
     * Returns the list of vertices in the shortest path (in order from start to end).
     * @throws UnsupportedOperationException if no path exists from start to end
     */
    List<V> vertices();

    /**
     * Returns the total weight of the edges in the shortest path.
     * @throws UnsupportedOperationException if no path exists from start to end
     */
    default double totalWeight() {
        return edges().stream().mapToDouble(E::weight).sum();
    }

    class Success<V, E extends BaseEdge<V, E>> implements LongestPath<V, E> {
        private final List<E> edges;

        /**
         * @param edges The list of edges in this shortest path.
         * @throws IllegalArgumentException if edges is empty or null
         */
        public Success(List<E> edges) {
            if (edges == null || edges.isEmpty()) {
                throw new IllegalArgumentException("Input edges must not be null or empty.");
            }
            this.edges = edges;
        }

        @Override
        public boolean exists() {
            return true;
        }

        @Override
        public List<E> edges() {
            return this.edges;
        }

        @Override
        public List<V> vertices() {
            return Stream.concat(
                    Stream.of(this.edges.get(0).from()),
                    this.edges.stream().map(E::to)
            ).collect(Collectors.toList());
        }
    }

    /** A path representing a single vertex (same start and end). */
    class SingleVertex<V, E extends BaseEdge<V, E>> implements LongestPath<V, E> {
        private final List<V> vertex;

        /**
         * @param vertex the only vertex in the path
         */
        public SingleVertex(V vertex) {
            this.vertex = List.of(vertex);
        }

        @Override
        public boolean exists() {
            return true;
        }

        @Override
        public List<E> edges() {
            return List.of();
        }

        @Override
        public List<V> vertices() {
            return this.vertex;
        }
    }

    class Failure<V, E extends BaseEdge<V, E>> implements LongestPath<V, E> {
        @Override
        public boolean exists() {
            return false;
        }

        @Override
        public List<E> edges() {
            throw new UnsupportedOperationException("No path found.");
        }

        @Override
        public List<V> vertices() {
            throw new UnsupportedOperationException("No path found.");
        }
    }
}
