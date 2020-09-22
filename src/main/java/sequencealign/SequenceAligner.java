package sequencealign;

import graphs.Edge;

import java.util.List;

public interface SequenceAligner {
    /**
     * Calculates and returns a list of edges representing the longest subsequence of
     * the two strings.
     * @param matches must be non-null and non-empty
     */
    List<Edge<DijkstraSequenceAligner.Node>> findLongestCommonSubsequence(double[][] matches);
}
