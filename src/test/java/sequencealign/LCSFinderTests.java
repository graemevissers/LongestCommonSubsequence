package sequencealign;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class LCSFinderTests {

    /**
     * Tests using different short sequences
     * baseSeq = AAAAA, comparedSeq = AAAAB
     */
    private String baseSeq_short = "AAAAA";
    private String comparedSeq_short = "AAAAB";
    private LCSFinder lcsFinder_diffSeq_long = new LCSFinder(baseSeq_short, comparedSeq_short, new DijkstraSequenceAligner());

    @Test
    public void baseSequence_returnsCorrectSequence() {
        String baseSeq = lcsFinder_diffSeq_long.baseSequence();
        assertEquals(baseSeq_short, baseSeq);
    }

    @Test
    public void comparedSequence_returnsCorrectSequence() {
        String comparedSeq = lcsFinder_diffSeq_long.comparedSequence();
        assertEquals(comparedSeq_short, comparedSeq);
    }

    @Test
    public void getMatches_withDifferentSeq_returnsCorrectMatrix() {
        double[] col = new double[] {1, 1, 1, 1, 0};
        double[][] matchesExpected = new double[][] {col, col, col, col, col};
        double[][] matches = lcsFinder_diffSeq_long.computeMatches();
        assertArrayEquals(matchesExpected, matches);
    }

    @Test
    public void getLCS_withDifferentSeq_returnsCorrectArray() {
        String[] output = new String[] {"AAAAA", "AAAAB"};
        String[] lcs = lcsFinder_diffSeq_long.getLCS();
        assertArrayEquals(output, lcs);
    }

    /**
     * Tests using the same short sequence
     * baseSeq = AAAAAA, comparedSeq = AAAAA
     */
    private LCSFinder lcsFinder_sameSeq_short = new LCSFinder(baseSeq_short, baseSeq_short, new DijkstraSequenceAligner());

    @Test
    public void getMatches_withSameSeq_returnsCorrectMatrix() {
        double [] col_allOnes = new double[] {1, 1, 1, 1, 1};
        double [][] matches_allOnes = new double[][] {col_allOnes, col_allOnes, col_allOnes, col_allOnes, col_allOnes};
        double[][] matches = lcsFinder_sameSeq_short.computeMatches();
        assertArrayEquals(matches_allOnes, matches);
    }

    @Test
    public void getLCS_withSameSeq_returnsCorrectArray() {
        String[] lcsExpected = new String[] {"AAAAA", "AAAAA"};
        String[] lcs = lcsFinder_sameSeq_short.getLCS();
        assertArrayEquals(lcsExpected, lcs);
    }
}