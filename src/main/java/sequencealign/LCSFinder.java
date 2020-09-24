package sequencealign;

import graphs.Edge;

import java.util.List;

public interface LCSFinder {
    /**
     * Calculates and returns a list of edges representing the longest subsequence of
     * the two strings.
     * @param matches must be non-null and non-empty
     */
    List<Edge<DijkstraLCSFinder.Node>> findLongestCommonSubsequence(double[][] matches);
}
