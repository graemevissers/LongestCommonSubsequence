package graphs;

import java.util.Objects;

public abstract class BaseEdge<V, THIS extends BaseEdge<V, THIS>> {
    protected final V from;
    protected final V to;
    protected final double weight;

    public BaseEdge(V from, V to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public V from() {
        return this.from;
    }

    public V to() {
        return this.to;
    }

    public double weight() {
        return weight;
    }

    /**
     * Returns a new edge with the same weight, but in the opposite direction.
     * This BaseEdge class was written by the CSE 373 staff at the University of Washington
     */
    public abstract THIS reversed();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseEdge<?, ?> baseEdge = (BaseEdge<?, ?>) o;
        return Double.compare(baseEdge.weight, weight) == 0 &&
                Objects.equals(from, baseEdge.from) &&
                Objects.equals(to, baseEdge.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, weight);
    }
}
