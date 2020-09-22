package sequencealign;

import graphs.Edge;
import graphs.longestpaths.DijkstraLongestPathFinder;

import java.util.List;

/**
 * The class used to get the longest subsequence of two strings. Computes an alignment matrix
 * and uses a sequence aligner to return the longest subsequence.
 */
public class LCSFinder {

    private final SequenceAligner sequenceAligner;
    private final String baseSeq;
    private final String comparedSeq;

    public LCSFinder(String baseSeq, String comparedSeq, SequenceAligner sequenceAligner) {
        this.baseSeq = baseSeq;
        this.comparedSeq = comparedSeq;
        this.sequenceAligner =  sequenceAligner;
    }

    public String baseSequence() { return baseSeq; }

    public String comparedSequence() { return comparedSeq; }

    public double[][] computeMatches() { return computeMatches(this.baseSeq, this.comparedSeq); }

    private static double[][] computeMatches(String baseSeq, String comparedSeq) {
        double[][] output = new double[baseSeq.length()][comparedSeq.length()];
        for (int i = 0; i < baseSeq.length(); i++) {
            char baseChar = baseSeq.charAt(i);
            for (int j = 0; j < comparedSeq.length(); j++) {
                char compared = comparedSeq.charAt(j);
                if (compared == baseChar) {
                    output[i][j] = 1;
                } else {
                    output[i][j] = 0;
                }
            }
        }
        return output;
    }

    public String[] getLCS() {
        double[][] matches = computeMatches();
        List<Edge<DijkstraSequenceAligner.Node>> path = sequenceAligner.findLongestCommonSubsequence(matches);
        String[] subsequences = new String[] {"", ""};
        for (Edge<DijkstraSequenceAligner.Node> edge : path) {
            char[] subseqChars = getSubseqChars(edge);
            subsequences[0] += subseqChars[0];
            subsequences[1] += subseqChars[1];
        }
        subsequences[0] += baseSeq.charAt(baseSeq.length() - 1);
        subsequences[1] += comparedSeq.charAt(comparedSeq.length() - 1);
        return subsequences;
    }

    private char[] getSubseqChars(Edge<DijkstraSequenceAligner.Node> edge) {
        int x1 = edge.from().xPos();
        int y1 = edge.from().yPos();
        int x2 = edge.to().xPos();
        int y2 = edge.to().yPos();
        char[] subseqChars = new char[2];
        subseqChars[0] = '-';
        subseqChars[1] = '-';
        if (x2 - x1 == 1) {
            subseqChars[0] = baseSeq.charAt(x1);
        }
        if (y2 - y1 == 1) {
            subseqChars[1] = comparedSeq.charAt(y1);
        }
        return subseqChars;
    }
}
