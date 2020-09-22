package graphs.longestpaths;

import graphs.BaseEdge;
import graphs.Graph;
import priorityqueues.ArrayHeapMaxPQ;
import priorityqueues.ExtrinsicMaxPQ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * DijkstraLongestPathFinder employs Dijkstra's algorithm to find the longest path between two
 * points of a weighted graph.
 *
 * @param <G> The graph type
 * @param <V> The vertex type
 * @param <E> The edge type
 */
public class DijkstraLongestPathFinder<G extends Graph<V, E>, V, E extends BaseEdge<V, E>>
    implements LongestPathFinder<G, V, E> {

    protected <T> ExtrinsicMaxPQ<T> createMinPQ() {
        return new ArrayHeapMaxPQ<>();
    }

    public LongestPath<V, E> findLongestPath(G graph, V start, V end) {
        if (Objects.equals(start, end)) {
            return new LongestPath.SingleVertex<>(start);
        }
        Map<V, E> edgeToV = new HashMap<>();
        Map<V, Double> distToStart = new HashMap<>();
        ExtrinsicMaxPQ<V> perimeter = createMinPQ();
        perimeter.add(start, 0);
        distToStart.put(start, 0.0);
        while (!perimeter.isEmpty()) {
            V from = perimeter.removeMax();
            if (Objects.equals(from, end)) {
                List<E> edges = new ArrayList<>();
                E prevEdge = edgeToV.get(end);
                edges.add(prevEdge);
                while (!Objects.equals(prevEdge.from(), start)) {
                    prevEdge = edgeToV.get(prevEdge.from());
                    edges.add(0, prevEdge);
                }
                return new LongestPath.Success<>(edges);
            }
            for (E e : graph.outgoingEdgesFrom(from)) {
                V to = e.to();
                if (!distToStart.containsKey(to)) {
                    distToStart.put(to, Double.NEGATIVE_INFINITY);
                }
                double oldDist = distToStart.get(to);
                double newDist = distToStart.get(from) + e.weight();
                if (newDist > oldDist) {
                    edgeToV.put(to, e);
                    distToStart.put(to, newDist);
                    if (perimeter.contains(to)) {
                        perimeter.changePriority(to, newDist);
                    } else {
                        perimeter.add(to, newDist);
                    }
                }
            }
        }
        return new LongestPath.Failure<>();
    }
}
