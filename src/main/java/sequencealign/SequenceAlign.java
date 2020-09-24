package sequencealign;

import graphs.Edge;
import scoringmatrices.BLOSUM62ScoringMatrix;
import scoringmatrices.ScoringMatrix;

import java.util.List;

/**
 * The class used to get the longest subsequence of two strings. Computes an alignment matrix
 * and uses a sequence aligner to return the longest subsequence.
 */
public class SequenceAlign {

    private final LCSFinder lcsFinder;
    private final String baseSeq;
    private final String comparedSeq;
    private final ScoringMatrix scoringMatrix;

    /**
     * Constructor for the SequenceAlign
     * @param baseSeq - First sequence
     * @param comparedSeq - Sequence it is compared to
     * @param scoringMatrix - Type of matrix used to give values to mismatches and indels
     * @param lcsFinder - Type of algorithm used to find the longest common subsequence
     */
    public SequenceAlign(String baseSeq, String comparedSeq, ScoringMatrix scoringMatrix, LCSFinder lcsFinder) {
        this.baseSeq = baseSeq;
        this.comparedSeq = comparedSeq;
        this.lcsFinder = lcsFinder;
        this.scoringMatrix = scoringMatrix;
    }

    /**
     * Constructor for the SequenceAlign
     * @param baseSeq - First sequence
     * @param comparedSeq - Sequence it is compared to
     * @param lcsFinder - Type of algorithm used to find the longest common subsequence
     */
    public SequenceAlign(String baseSeq, String comparedSeq, LCSFinder lcsFinder) {
        this.baseSeq = baseSeq;
        this.comparedSeq = comparedSeq;
        this.lcsFinder = lcsFinder;
        this.scoringMatrix = new BLOSUM62ScoringMatrix();
    }

    /**
     * Constructor for the SequenceAlign
     * @param baseSeq - First sequence
     * @param comparedSeq - Sequence it is compared to
     * @param scoringMatrix - Type of matrix used to give values to mismatches and indels
     */
    public SequenceAlign(String baseSeq, String comparedSeq, ScoringMatrix scoringMatrix) {
        this.baseSeq = baseSeq;
        this.comparedSeq = comparedSeq;
        this.lcsFinder =  new DijkstraLCSFinder();
        this.scoringMatrix = scoringMatrix;
    }

    /**
     * Constructor for the SequenceAlign
     * @param baseSeq - First sequence
     * @param comparedSeq - Sequence it is compared to
     */
    public SequenceAlign(String baseSeq, String comparedSeq) {
        this.baseSeq = baseSeq;
        this.comparedSeq = comparedSeq;
        this.lcsFinder =  new DijkstraLCSFinder();
        this.scoringMatrix = new BLOSUM62ScoringMatrix();
    }

    public String baseSequence() { return baseSeq; }

    public String comparedSequence() { return comparedSeq; }

    public double[][] computeMatches() {
        return computeMatches(this.baseSeq, this.comparedSeq);
    }

    private double[][] computeMatches(String baseSeq, String comparedSeq) {
        double[][] output = new double[baseSeq.length()][comparedSeq.length()];
        for (int i = 0; i < baseSeq.length(); i++) {
            char baseChar = baseSeq.charAt(i);
            for (int j = 0; j < comparedSeq.length(); j++) {
                char comparedChar = comparedSeq.charAt(j);
                output[i][j] = scoringMatrix.getScore(baseChar, comparedChar);
            }
        }
        return output;
    }

    public String[] getLCS() {
        double[][] matches = computeMatches();
        List<Edge<DijkstraLCSFinder.Node>> path = lcsFinder.findLongestCommonSubsequence(matches);
        String[] subsequences = new String[] {"", ""};
        for (Edge<DijkstraLCSFinder.Node> edge : path) {
            char[] subseqChars = getSubseqChars(edge);
            subsequences[0] += subseqChars[0];
            subsequences[1] += subseqChars[1];
        }
        subsequences[0] += baseSeq.charAt(baseSeq.length() - 1);
        subsequences[1] += comparedSeq.charAt(comparedSeq.length() - 1);
        return subsequences;
    }

    private char[] getSubseqChars(Edge<DijkstraLCSFinder.Node> edge) {
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
