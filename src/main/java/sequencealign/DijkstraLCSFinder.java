package sequencealign;

import graphs.Edge;
import graphs.Graph;
import graphs.longestpaths.DijkstraLongestPathFinder;
import graphs.longestpaths.LongestPath;
import graphs.longestpaths.LongestPathFinder;

import java.util.*;

public class DijkstraLCSFinder implements LCSFinder {
    private final LongestPathFinder<Graph<Node, Edge<Node>>, Node, Edge<Node>> pathFinder;

    public DijkstraLCSFinder() { this.pathFinder = createPathFinder(); }

    protected <G extends Graph<V, Edge<V>>, V> LongestPathFinder<G, V, Edge<V>> createPathFinder() {

        return new DijkstraLongestPathFinder<>();
    }

    public List<Edge<Node>> findLongestCommonSubsequence(double[][] alignmentScores) {
        LongestPath<Node, Edge<Node>> alignment = pathFinder.findLongestPath(
                new AlignmentGraph(alignmentScores),
                new Node(0, 0),
                new Node(alignmentScores.length - 1, alignmentScores[0].length - 1));
        return alignment.edges();
    }

    protected static class Node {
        private final int xPosition;
        private final int yPosition;

        public Node(int xPosition, int yPosition) {
            this.xPosition = xPosition;
            this.yPosition = yPosition;
        }

        public int xPos() { return this.xPosition; }

        public int yPos() { return this.yPosition; }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Node)) {
                return false;
            }
            Node node = (Node) o;
            return xPosition == node.xPosition &&
                    yPosition == node.yPosition;
        }

        @Override
        public int hashCode() {
            return Objects.hash(xPosition, yPosition);
        }
    }

    /**
     * Nested class that creates graph for vertical seams
     */
    private class AlignmentGraph implements Graph<Node, Edge<Node>> {
        private final double[][] alignmentScores;

        public AlignmentGraph(double[][] alignmentScores) {
            this.alignmentScores = alignmentScores;
        }

        /**
         * Set of edges leaving a node. Indels are given a weight of negative one; matches and
         * mismatches are determined by their relative alignment scores.
         * @param v Node type with xPosition and yPosition
         * @return Edges that stem from each node
         */
        @Override
        public Collection<Edge<Node>> outgoingEdgesFrom(Node v) {
            Set<Edge<Node>> neighbors = new HashSet<>();
            if (v.xPos() != alignmentScores.length - 1) {
                neighbors.add(new Edge<>(v, new Node(v.xPos() + 1, v.yPos()), -1));
            }
            if (v.yPos() != alignmentScores[0].length - 1) {
                neighbors.add(new Edge<>(v, new Node(v.xPos(), v.yPos() + 1), -1));
            }
            if (v.xPos() != alignmentScores.length - 1 && v.yPos() != alignmentScores[0].length - 1) {
                neighbors.add(new Edge<>(v, new Node(v.xPos() + 1, v.yPos() + 1),
                        alignmentScores[v.xPos()][v.yPos()]));
            }
            return neighbors;
        }
    }
}
