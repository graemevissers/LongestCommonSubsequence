package graphs;

/**
 * A weighted directed edge.
 * This Edge class was written by the CSE 373 staff at the University of Washington
 *
 * @param <V> The vertex type.
 */
public final class Edge<V> extends BaseEdge<V, Edge<V>> {
    public Edge(V from, V to, double weight) {
        super(from, to, weight);
    }

    @Override
    public Edge<V> reversed() {
        return new Edge<>(this.to, this.from, this.weight);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "from=" + from +
                ", to=" + to +
                ", weight=" + weight +
                "} " + super.toString();
    }
}
