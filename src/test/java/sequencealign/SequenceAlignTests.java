package sequencealign;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class SequenceAlignTests {

    /**
     * Tests using different short sequences
     * baseSeq = "AAAAA", comparedSeq = "AAAAR"
     */
    private String baseSeq_short = "AAAAA";
    private String comparedSeq_short = "AAAAR";
    private SequenceAlign sequenceAlign_diffSeq_short = new SequenceAlign(baseSeq_short, comparedSeq_short, new DijkstraLCSFinder());

    @Test
    public void baseSequence_returnsCorrectSequence() {
        String baseSeq = sequenceAlign_diffSeq_short.baseSequence();
        assertEquals(baseSeq_short, baseSeq);
    }

    @Test
    public void comparedSequence_returnsCorrectSequence() {
        String comparedSeq = sequenceAlign_diffSeq_short.comparedSequence();
        assertEquals(comparedSeq_short, comparedSeq);
    }

    @Test
    public void getScores_withDifferentSeq_returnsCorrectMatrix() {
        double[] col = new double[]{4, 4, 4, 4, -1};
        double[][] matchesExpected = new double[][]{col, col, col, col, col};
        double[][] matches = sequenceAlign_diffSeq_short.computeMatches();
        assertArrayEquals(matchesExpected, matches);
    }

    @Test
    public void getLCS_withDifferentSeq_returnsCorrectArray() {
        String[] output = new String[]{"AAAAA", "AAAAR"};
        String[] lcs = sequenceAlign_diffSeq_short.getLCS();
        assertArrayEquals(output, lcs);
    }

    /**
     * Tests using the same short sequence
     * baseSeq = "AAAAA", comparedSeq = "AAAAA"
     */
    private SequenceAlign sequenceAlign_sameSeq_short = new SequenceAlign(baseSeq_short, baseSeq_short, new DijkstraLCSFinder());

    @Test
    public void getScores_withSameSeq_returnsCorrectMatrix() {
        double[] col_allOnes = new double[]{4, 4, 4, 4, 4};
        double[][] matches_allOnes = new double[][]{col_allOnes, col_allOnes, col_allOnes, col_allOnes, col_allOnes};
        double[][] matches = sequenceAlign_sameSeq_short.computeMatches();
        assertArrayEquals(matches_allOnes, matches);
    }

    @Test
    public void getLCS_withSameSeq_returnsCorrectArray() {
        String[] lcsExpected = new String[]{"AAAAA", "AAAAA"};
        String[] lcs = sequenceAlign_sameSeq_short.getLCS();
        assertArrayEquals(lcsExpected, lcs);
    }

    /**
     * Tests using same sequence of every amino acid type to test the
     * BLOSUM62 scoring matrix.
     */
    private String baseSeq_allAAs = "ARNDCQEGHILKMFPSTWYVBZX*";

    private SequenceAlign getSequenceAlign_sameSeq_allAAs = new SequenceAlign(
            baseSeq_allAAs,
            baseSeq_allAAs,
            new DijkstraLCSFinder());

    @Test
    public void getAlignmentScores_withAllAAs_returnsCorrectMatrix() {
        double[][] scoresExpected = new double[][] {
            new double[] { 4, -1, -2, -2,  0, -1, -1,  0, -2, -1, -1, -1, -1, -2, -1,  1,  0, -3, -2,  0, -2, -1,  0, -4},
            new double[] {-1,  5,  0, -2, -3,  1,  0, -2,  0 ,-3, -2,  2, -1, -3, -2, -1, -1, -3, -2, -3, -1,  0, -1, -4},
            new double[] {-2,  0,  6,  1, -3,  0,  0,  0,  1, -3, -3,  0, -2, -3, -2,  1,  0, -4, -2, -3,  3,  0, -1, -4},
            new double[] {-2, -2,  1,  6, -3,  0,  2, -1, -1, -3, -4, -1, -3, -3, -1,  0, -1, -4, -3, -3,  4,  1, -1, -4},
            new double[] { 0, -3, -3, -3,  9, -3, -4, -3, -3, -1, -1, -3, -1, -2, -3, -1, -1, -2, -2, -1, -3, -3, -2, -4},
            new double[] {-1,  1,  0,  0, -3,  5,  2, -2,  0, -3, -2,  1,  0, -3, -1,  0, -1, -2, -1, -2,  0,  3, -1, -4},
            new double[] {-1,  0,  0,  2, -4,  2,  5, -2,  0, -3, -3,  1, -2, -3, -1,  0, -1, -3, -2, -2,  1,  4, -1, -4},
            new double[] { 0, -2,  0, -1, -3, -2, -2,  6, -2, -4, -4, -2, -3, -3, -2,  0, -2, -2, -3, -3, -1, -2, -1, -4},
            new double[] {-2,  0,  1, -1, -3,  0,  0, -2,  8, -3, -3, -1, -2, -1, -2, -1, -2, -2,  2, -3,  0,  0, -1, -4},
            new double[] {-1, -3, -3, -3, -1, -3, -3, -4, -3,  4,  2, -3,  1,  0, -3, -2, -1, -3, -1,  3, -3, -3, -1, -4},
            new double[] {-1, -2, -3, -4, -1, -2, -3, -4, -3,  2,  4, -2,  2,  0, -3, -2, -1, -2, -1,  1, -4, -3, -1, -4},
            new double[] {-1,  2,  0, -1, -3,  1,  1, -2, -1, -3, -2,  5, -1, -3, -1,  0, -1, -3, -2, -2,  0,  1, -1, -4},
            new double[] {-1, -1, -2, -3, -1,  0, -2, -3, -2,  1,  2, -1,  5,  0, -2, -1, -1, -1, -1,  1, -3, -1, -1, -4},
            new double[] {-2, -3, -3, -3, -2, -3, -3, -3, -1,  0,  0, -3,  0,  6, -4, -2, -2,  1,  3, -1, -3, -3, -1, -4},
            new double[] {-1, -2, -2, -1, -3, -1, -1, -2, -2, -3, -3, -1, -2, -4,  7, -1, -1, -4, -3, -2, -2, -1, -2, -4},
            new double[] { 1, -1,  1,  0, -1,  0,  0,  0, -1, -2, -2,  0, -1, -2, -1,  4,  1, -3, -2, -2,  0,  0,  0, -4},
            new double[] { 0, -1,  0, -1, -1, -1, -1, -2, -2, -1, -1, -1, -1, -2, -1,  1,  5, -2, -2,  0, -1, -1,  0, -4},
            new double[] {-3, -3, -4, -4, -2, -2, -3, -2, -2, -3, -2, -3, -1,  1, -4, -3, -2, 11,  2, -3, -4, -3, -2, -4},
            new double[] {-2, -2, -2, -3, -2, -1, -2, -3,  2, -1, -1, -2, -1,  3, -3, -2, -2,  2,  7, -1, -3, -2, -1, -4},
            new double[] { 0, -3, -3, -3, -1, -2, -2, -3, -3,  3,  1, -2,  1, -1, -2, -2,  0, -3, -1,  4, -3, -2, -1, -4},
            new double[] {-2, -1,  3,  4, -3,  0,  1, -1,  0, -3, -4,  0, -3, -3, -2,  0, -1, -4, -3, -3,  4,  1, -1, -4},
            new double[] {-1,  0,  0,  1, -3,  3,  4, -2,  0, -3, -3,  1, -1, -3, -1,  0, -1, -3, -2, -2,  1,  4, -1, -4},
            new double[] { 0, -1, -1, -1, -2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -2,  0,  0, -2, -1, -1, -1, -1, -1, -4},
            new double[] {-4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4, -4,  1}
        };
        assertArrayEquals(scoresExpected, getSequenceAlign_sameSeq_allAAs.computeMatches());
    }
}